package service.contentservice.content;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.DocObjectIdUtil;
import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.model.SavingEntry;
import documentDatabaseModule.service.IGroupDocumentService;
import dtoAndValidation.dto.content.GeneralGroupInformationDTO;
import dtoAndValidation.dto.inflation.InflationDto;
import dtoAndValidation.dto.processing.*;
import dtoAndValidation.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import relationalDatabaseModule.model.Group;
import relationalDatabaseModule.model.KPerson;
import relationalDatabaseModule.service.IDatabaseService;
import service.contentservice.util.ContentServiceMapper;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/processing")
public class ProcessingController {
    @Value("${inflationservice-uri}")
    public String URI;

    private final IGroupDocumentService groupDocumentService;
    private final IDatabaseService databaseService;

    @Autowired
    public ProcessingController(IGroupDocumentService groupDocumentService, IDatabaseService databaseService) {
        this.groupDocumentService = groupDocumentService;
        this.databaseService = databaseService;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GeneralGroupInformationDTO> getGeneralGroupInformation(
            @PathVariable Long groupId){

        //Validate input
        if(groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var groupInfo = new GeneralGroupInformationDTO();

        //Add all users of the group and the group name
        Collection<KPerson> persons = databaseService.getPersonsOfGroupId(groupId);
        persons.forEach(person -> groupInfo.addPersonToGroupInfo(ContentServiceMapper.mapPersonToDto(person)));

        Group group = databaseService.getGroupById(groupId);
        if (group == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        groupInfo.setGroupName(group.getGroupName());

        return ResponseEntity.status(HttpStatus.OK).body(groupInfo);
    }

    /**
     *
     * When i wrote this, only God and i understood what i was doing.. Now, God only knows
     *
     * Updates a SavingEntry for a given group
     * @param groupId The id of the group
     * @param filterInformation The updated SavingEntry
     * @return The inserted savingEntry
     */
    @PostMapping(
            value="/{groupId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ProcessResultContainerDTO> getProcessedResults(
            @PathVariable(value="groupId") Long groupId,
            @RequestBody FilterInformationDTO filterInformation){

        var inflationDto = receiveInflationRate();

        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(FilterInformationDTO.class);

        if(!validator.validate(filterInformation, false) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        //Resolve categories
        Set<Category> categories;
        List<Category> categoryList = new ArrayList();
        GroupDocument groupDocument = groupDocumentService.getGroupDocument(groupId);

        if(groupDocument == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(filterInformation.getCategoryIds().isEmpty()){
            List<String> categoryIds = new ArrayList();
            groupDocument.categories.forEach(category -> {
                categoryList.add(category);
                categoryIds.add(DocObjectIdUtil.toHexString(category.getId()));
            });

            categories = Set.copyOf(categoryList);
            filterInformation.setCategoryIds(categoryIds);
        } else {
            categories = groupDocument.
                    categories.
                    stream().
                    filter(c ->
                            filterInformation.getCategoryIds().
                                    contains(DocObjectIdUtil.toHexString(c.getId()))).
                    collect(Collectors.toSet());
        }






        //Resolve persons
        Set<KPerson> allowedPersons;



        Collection<KPerson> persons = databaseService.getPersonsOfGroupId(groupId);

        if(filterInformation.getPersonIds().isEmpty()){
            allowedPersons = Set.copyOf(persons);
            List<UUID> personIds = new ArrayList<>();
            for(KPerson person : persons) {
                personIds.add(UUID.fromString(person.getId()));
            }
            filterInformation.setPersonIds(personIds);
        } else {
            allowedPersons =
                    persons.
                    stream().
                    filter(p ->
                            filterInformation.getPersonIds().contains(UUID.fromString(p.getId()))).
                    collect(Collectors.toSet());
        }




        Set<String> personNames = allowedPersons.stream().map(KPerson::getUsername).collect(Collectors.toSet());


        //Apply filter and sorting
        List<SavingEntry> filteredAndSortedEntries = groupDocument.savingEntries.
                stream().
                filter(savingEntry ->
                        savingEntry.
                        getCreationDate().
                                compareTo(
                                        filterInformation.getStartDate() == null ?
                                                new Date(Long.MIN_VALUE): filterInformation.getStartDate()) > 0).

                filter(savingEntry ->
                        savingEntry.
                        getCreationDate().
                                compareTo(
                                        filterInformation.getEndDate() == null ?
                                                new Date() : filterInformation.getEndDate()) < 0).

                filter(savingEntry -> categories.contains(savingEntry.getCategory())).

                filter(savingEntry -> personNames.contains(savingEntry.getCreator())).

                sorted((e1, e2) ->

                         switch (filterInformation.getSortParameter()) {
                            case CreationDate -> e1.getCreationDate().compareTo(e2.getCreationDate());
                            case Creator -> e1.getCreator().compareTo(e2.getCreator());
                            case CostBalance -> e1.getCostBalance().compareTo(e2.getCostBalance());
                            case Name -> e1.getName().compareTo(e2.getName());
                            case Categories -> e1.getCategory().getName().compareTo(e2.getCategory().getName());

                            //Description is optional, therefore a null check is necessary
                            case Description ->  e1.getDescription() == null ?
                                    0 : e1.getDescription().compareTo(e2.getDescription());

                        }).

               collect(Collectors.toList());

        //Add entries to result

        var result = new ProcessResultContainerDTO();
        filteredAndSortedEntries.forEach(entry -> result.addSavingEntry(ContentServiceMapper.mapSavingEntryToDto(entry)));




        //Diagram 1
        var diagram1 = new BalanceProcessResultDTO();

        Double income = 0d;
        Double outcome = 0d;
        for (SavingEntry filteredAndSortedEntry : filteredAndSortedEntries) {

            if (filteredAndSortedEntry.getCostBalance() >= 0)
                income += filteredAndSortedEntry.getCostBalance();

            else outcome += filteredAndSortedEntry.getCostBalance();
        }
        diagram1.setIncome(income);
        diagram1.setOutcome(outcome);
        diagram1.setBalance(income+outcome);
        diagram1.setFutureBalance(diagram1.getBalance() - (diagram1.getBalance() * (inflationDto.getInflationValueInPercent()*0.01)));

        result.setBalanceProcessResultDTO(diagram1);




        //Diagram 2 and 3 Time intervals

        //The string is the correct representation of the date
        //Due to the different formatting the grouping is done correctly
        Map<String, List<SavingEntry>> entriesByTimeInterval = filteredAndSortedEntries.stream().
                collect(Collectors.groupingBy(entry -> {

                    SimpleDateFormat dateFormat = switch (filterInformation.getTimeInterval()){
                        case Day   -> new SimpleDateFormat("dd.MM.y");  //e.g. 17.03.2022
                        case Week  -> new SimpleDateFormat("ww.y");       //e.g. Calendar week 02, 04, 05 , 2022
                        case Month -> new SimpleDateFormat("MMMM.y");     //e.g. September,  2022
                        case Year  -> new SimpleDateFormat("y");        //e.g. 2022
                    };

                    return dateFormat.format(entry.getCreationDate());
                }));



        //Diagram 2 <Interval> <Category,Sum>
        List<IntervalGroupDTO> valuesForEntriesByIntervalAndCategory = new ArrayList<>();

        //For each interval, add the interval representation and group the remaining entries by category
        for (Map.Entry<String, List<SavingEntry>> entryByTimeInterval : entriesByTimeInterval.entrySet()) {

            IntervalGroupDTO intervalGroupDTO = new IntervalGroupDTO();
            intervalGroupDTO.setDateRepresentation(entryByTimeInterval.getKey());


            Map<Category, List<SavingEntry>> entriesByTimeIntervalAndCategory =
                    entryByTimeInterval.getValue().stream().
                    collect(Collectors.groupingBy(SavingEntry::getCategory));

            //For each group of saving entries (grouped by interval and category) resolve the sum and name of category
            for(Map.Entry<Category, List<SavingEntry>> entryByTimeIntervalAndCategory :
                entriesByTimeIntervalAndCategory.entrySet()){

                IntervalBasedEntryValueDTO intervalBasedEntryValueDTO = new IntervalBasedEntryValueDTO();
                intervalBasedEntryValueDTO.setNameDescription(entryByTimeIntervalAndCategory.getKey().getName());
                intervalBasedEntryValueDTO.setId(DocObjectIdUtil.toHexString(entryByTimeIntervalAndCategory.getKey().getId()));

                Double sum = 0d;

                for(SavingEntry savingEntry : entryByTimeIntervalAndCategory.getValue()){
                    sum += savingEntry.getCostBalance();
                }

                intervalBasedEntryValueDTO.setSum(sum);
                intervalGroupDTO.addValue(intervalBasedEntryValueDTO);
            }

            valuesForEntriesByIntervalAndCategory.add(intervalGroupDTO);

        }

        result.setDiagramByIntervalAndCategory(valuesForEntriesByIntervalAndCategory);



        //Diagram 3 <Interval> <User,Sum>
        List<IntervalGroupDTO> valuesForEntriesByIntervalAndCreator = new ArrayList<>();

        //For each interval, add the interval representation and group the remaining entries by creators
        for (Map.Entry<String, List<SavingEntry>> entryByTimeInterval : entriesByTimeInterval.entrySet()) {

            IntervalGroupDTO intervalGroupDTO = new IntervalGroupDTO();
            intervalGroupDTO.setDateRepresentation(entryByTimeInterval.getKey());

            Map<String, List<SavingEntry>> entriesByTimeIntervalAndUser =
                    entryByTimeInterval.getValue().stream().
                            collect(Collectors.groupingBy(SavingEntry::getCreator));

            //For each group of saving entries (grouped by interval and user) resolve the sum and name of creator
            for(Map.Entry<String, List<SavingEntry>> entryByTimeIntervalAndUser :
                    entriesByTimeIntervalAndUser.entrySet()){

                IntervalBasedEntryValueDTO intervalBasedEntryValueDTO = new IntervalBasedEntryValueDTO();
                intervalBasedEntryValueDTO.setNameDescription(entryByTimeIntervalAndUser.getKey());
                var name = entryByTimeIntervalAndUser.getKey();

                var person = allowedPersons.stream().filter(searchedPerson -> name.equals(searchedPerson.getUsername())).findFirst();
                person.ifPresent(kPerson -> intervalBasedEntryValueDTO.setId(kPerson.getId()));

                Double sum = 0d;

                for(SavingEntry savingEntry : entryByTimeIntervalAndUser.getValue()){
                    sum += savingEntry.getCostBalance();
                }

                intervalBasedEntryValueDTO.setSum(sum);
                intervalGroupDTO.addValue(intervalBasedEntryValueDTO);
            }

            valuesForEntriesByIntervalAndCreator.add(intervalGroupDTO);

        }


        result.setDiagramByIntervalAndCreator(valuesForEntriesByIntervalAndCreator);


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    private InflationDto receiveInflationRate(){

        RestTemplate restTemplate = new RestTemplate();

        InflationDto inflationResponse = restTemplate.getForObject(URI, InflationDto.class);

        if(inflationResponse != null){
            return inflationResponse;
        }

        InflationDto inflationDto = new InflationDto();
        inflationDto.setInflationValueInPercent(1.0);

        return inflationDto;
    }
}
