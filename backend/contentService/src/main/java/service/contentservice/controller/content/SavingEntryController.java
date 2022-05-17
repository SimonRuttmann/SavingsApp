package service.contentservice.controller.content;

import documentDatabaseService.documentbased.service.IGroupDocumentService;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.util.MapperUtil;
import dtoAndValidation.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import documentDatabaseService.documentbased.model.GroupDocument;
import documentDatabaseService.documentbased.model.SavingEntry;
import documentDatabaseService.documentbased.model.DocObjectIdUtil;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/content/savingEntry")
public class SavingEntryController {


    private final IGroupDocumentService groupDocumentService;


    @Autowired
    public SavingEntryController(IGroupDocumentService groupDocumentService) {
        this.groupDocumentService = groupDocumentService;
    }

    /**
     * Returns a saving entry based on the groupId and saving entry id
     * @param groupId The id of the group
     * @param entryId The id of the requested saving entry
     * @return The savingEntry resolved by id
     */
    @GetMapping("/{groupId}/{savingEntryId}")
    public ResponseEntity<SavingEntryDTO> getSavingEntry(
            @PathVariable(value="groupId") Long groupId,
            @PathVariable(value="savingEntryId") String entryId){

        //Validate input
        if(groupId == null || entryId == null || entryId.isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Get savingEntry
        SavingEntry savingEntry = groupDocumentService.getSavingEntry(
                groupId, DocObjectIdUtil.toObjectId(entryId));

        if(savingEntry == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        SavingEntryDTO savingEntryDTO = MapperUtil.SavingEntryToDTO(savingEntry);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(savingEntryDTO);

    }

    /**
     * Returns all saving entries based on the groupId
     * @param groupId The id of the group
     * @return The entries of the group
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<List<SavingEntryDTO>> getSavingEntries(
            @PathVariable(value="groupId") Long groupId){

        //Validate input
        if(groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Get saving entry
        GroupDocument groupDocument = groupDocumentService.getGroupDocument(groupId);

        if(groupDocument == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        List<SavingEntryDTO> responseList = new ArrayList<>();
        groupDocument.savingEntries.forEach(entry ->
                responseList.add(MapperUtil.SavingEntryToDTO(entry)));

        return ResponseEntity.
                status(HttpStatus.OK).
                body(responseList);
    }


    /**
     * Updates a savingEntry for a given group
     * @param groupId The id of the group
     * @param savingEntry The updated savingEntry
     * @return The updated savingEntry
     */
    @PutMapping(
            value="/{groupId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SavingEntryDTO> updateSavingEntry(
            @PathVariable(value="groupId") Long groupId,
            @RequestBody SavingEntryDTO savingEntry){


        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(SavingEntryDTO.class);

        if(!validator.validate(savingEntry, true) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Update SavingEntry
        SavingEntry updatedSavingEntry = groupDocumentService.updateSavingEntry(
                groupId, MapperUtil.DTOToSavingEntry(savingEntry));

        if(updatedSavingEntry == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        SavingEntryDTO savingEntryDTO = MapperUtil.SavingEntryToDTO(updatedSavingEntry);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(savingEntryDTO);
    }


    /**
     * Updates a SavingEntry for a given group
     * @param groupId The id of the group
     * @param savingEntry The updated SavingEntry
     * @return The inserted savingEntry
     */
    @PostMapping(
            value="/{groupId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SavingEntryDTO> addSavingEntry(
            @PathVariable(value="groupId") Long groupId,
            @RequestBody SavingEntryDTO savingEntry){

        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(SavingEntryDTO.class);
        if(!validator.validate(savingEntry, false) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        //Insert SavingEntry
        SavingEntry insertSavingEntry = groupDocumentService.addSavingEntry(
                groupId, MapperUtil.DTOToSavingEntry(savingEntry));

        //TODO
        if(insertSavingEntry == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        SavingEntryDTO SavingEntryDTO = MapperUtil.SavingEntryToDTO(insertSavingEntry);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(SavingEntryDTO);
    }


    /**
     * Deletes a SavingEntry for a given group and given SavingEntry id
     * @param groupId The id of the group
     * @param savingEntryId The id of the SavingEntry
     * @return The status code
     */
    @DeleteMapping("/{groupId}/{savingEntryId}")
    public ResponseEntity<?> deleteSavingEntry(
            @PathVariable(value="groupId") Long groupId,
            @PathVariable(value="savingEntryId") String savingEntryId){

        //Validate input
        if(groupId == null || savingEntryId == null || savingEntryId.isBlank())
            return new ResponseEntity<SavingEntryDTO>(HttpStatus.BAD_REQUEST);


        //Delete SavingEntry
        groupDocumentService.deleteSavingEntry(
                groupId, DocObjectIdUtil.toObjectId(savingEntryId));

        return new ResponseEntity<>(HttpStatus.OK);
    }




}

