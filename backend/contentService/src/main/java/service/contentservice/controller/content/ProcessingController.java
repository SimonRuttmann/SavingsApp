package service.contentservice.controller.content;

import documentDatabaseService.documentbased.service.IGroupDocumentService;
import dtoAndValidation.dto.content.GeneralGroupInformationDTO;
import dtoAndValidation.dto.processing.*;
import dtoAndValidation.util.MapperUtil;
import dtoAndValidation.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relationalDatabaseService.model.Group;
import relationalDatabaseService.model.Person;
import relationalDatabaseService.service.IDatabaseService;
import documentDatabaseService.documentbased.model.Category;
import documentDatabaseService.documentbased.model.GroupDocument;
import documentDatabaseService.documentbased.model.SavingEntry;
import documentDatabaseService.documentbased.model.DocObjectIdUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/processing")
public class ProcessingController {

    public final Double INFLATION = 0.1d;
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
        Collection<Person> persons = databaseService.getPersonsOfGroupId(groupId);
        persons.forEach(person -> groupInfo.addPersonToGroupInfo(MapperUtil.PersonToDTO(person)));

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


        var validator = ValidatorFactory.getInstance().getValidator(FilterInformationDTO.class);

        if(!validator.validate(filterInformation, false) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Validate input


        //Resolve categories
        Set<Category> categories;
        GroupDocument groupDocument = groupDocumentService.getGroupDocument(groupId);

        if(groupDocument == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        categories = groupDocument.
                categories.
                stream().
                filter(c ->
                        filterInformation.getCategoryIds().
                                contains(DocObjectIdUtil.toHexString(c.getId()))).
                collect(Collectors.toSet());


        //Resolve persons
        Set<Person> allowedPersons;


        Collection<Person> persons = databaseService.getPersonsOfGroupId(groupId);

        allowedPersons =
                    persons.
                    stream().
                    filter(p ->
                            filterInformation.getPersonIds().contains(p.getId())).
                    collect(Collectors.toSet());


        Set<String> personNames = allowedPersons.stream().map(Person::getUsername).collect(Collectors.toSet());



        //Apply filter and sorting
        List<SavingEntry> filteredAndSortedEntries = groupDocument.savingEntries.
                stream().
                filter(savingEntry ->
                        savingEntry.
                        getCreationDate().
                                compareTo(filterInformation.getStartDate()) > 0).

                filter(savingEntry ->
                        savingEntry.
                        getCreationDate().
                                compareTo(
                                        filterInformation.getEndDate() == null ?
                                                new Date() : filterInformation.getEndDate()) < 0).

                filter(savingEntry -> categories.contains(savingEntry.getCategory())).

                filter(savingEntry -> personNames.contains(savingEntry.getCreator())).

                sorted((e1, e2) ->

                         switch (filterInformation.getSortingParameter()) {
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
        filteredAndSortedEntries.forEach(entry -> result.addSavingEntry(MapperUtil.SavingEntryToDTO(entry)));




        //Diagram 1
        var diagram1 = new BalanceProcessResultDTO();

        Double income = 0d;
        Double outcome = 0d;
        for (SavingEntry filteredAndSortedEntry : filteredAndSortedEntries) {
            if (filteredAndSortedEntry.getCostBalance() >= 0)
                income += filteredAndSortedEntry.getCostBalance();
            outcome += filteredAndSortedEntry.getCostBalance();
        }
        diagram1.setIncome(income);
        diagram1.setOutcome(outcome);
        diagram1.setBalance(income-outcome);
        diagram1.setFutureBalance(diagram1.getBalance() * INFLATION);

        result.setBalanceProcessResultDTO(diagram1);




        //Diagram 2 and 3 Time intervals

        //The string is the correct representation of the date
        //Due to the different formatting the grouping is done correctly
        Map<String, List<SavingEntry>> entriesByTimeInterval = filteredAndSortedEntries.stream().
                collect(Collectors.groupingBy(entry -> {

                    SimpleDateFormat dateFormat = switch (filterInformation.getTimeInterval()){
                        case Day   -> new SimpleDateFormat("dd.MM.y");  //e.g. 17.03.2022
                        case Week  -> new SimpleDateFormat("ww");       //e.g. Calendar week 02, 04, 05
                        case Month -> new SimpleDateFormat("MMMM");     //e.g. September
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
                    filteredAndSortedEntries.stream().
                            collect(Collectors.groupingBy(SavingEntry::getCreator));

            //For each group of saving entries (grouped by interval and user) resolve the sum and name of creator
            for(Map.Entry<String, List<SavingEntry>> entryByTimeIntervalAndUser :
                    entriesByTimeIntervalAndUser.entrySet()){

                IntervalBasedEntryValueDTO intervalBasedEntryValueDTO = new IntervalBasedEntryValueDTO();
                intervalBasedEntryValueDTO.setNameDescription(entryByTimeIntervalAndUser.getKey());
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
}







    //FILTER:           KEY           VALUE
    //                  Anfangsdatum    Date
    //                  Endatum         Date
    //                  User            Array an User Id`s
    //                  Kategorie       Array an Kategorien
    //
    // Filter:
    // Anfangsdatum
    // Zeitintervall
    //

    //Diagramm 1
    // Summe abhängig von Filtern
    // 1. Zeitraum --> Anfangsdatum ist gegeben
    // 2. User: Array an Usern, nur user beachten, welche darin liegen
    // 3. Kategory: Array an KategorieId`s, nur beachten welche darin liegen

    //Wert 1 Typ: Integer Summe der Einnahmen
    //Wert 2 Typ: Integer Summe der Ausgaben


    //Diagramm 2
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden
    // 3. User + Kategorie wie voher

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      Kategorie 1: Summe ausgaben (für alle user)
    //      Kategorie 2: Summe ausgaben
    //      Kategorie 3: Summe ausgaben


    //Diagramm 3
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden
    // 3. User + Kategorie wie voher

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      user 1: Summe ausgaben (für alle kategorien)
    //      user 2: Summe ausgaben
    //      user 3: Summe ausgaben


    //Diagramm 4
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      Summe ausgaben
    //      Summe ausgaben
    //      Summe ausgaben

    //Anzahl = Zeitintervall bisher / 2
    // Calculated ..
    // mittelwert * inflation



    //Default get, put, post, delete entry



