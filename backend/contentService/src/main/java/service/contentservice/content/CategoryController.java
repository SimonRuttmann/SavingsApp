package service.contentservice.content;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.DocObjectIdUtil;
import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.service.IGroupDocumentService;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.util.MapperUtil;
import dtoAndValidation.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    private final IGroupDocumentService groupDocumentService;

    @Autowired
    public CategoryController(IGroupDocumentService groupDocumentService) {
        this.groupDocumentService = groupDocumentService;
    }


    /**
     * Returns a category based on the groupId and categoryId
     * @param groupId The id of the group
     * @param categoryId The id of the requested category
     * @return The category resolved by id
     */
    @GetMapping("/{groupId}/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(
            @PathVariable(value="groupId") Long groupId,
            @PathVariable(value="categoryId") String categoryId){

        //Validate input
        if(groupId == null || categoryId == null || categoryId.isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Get category
        Category category = groupDocumentService.getCategory(
                groupId,
                DocObjectIdUtil.toObjectId(categoryId));

        if(category == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        CategoryDTO categoryDTO = MapperUtil.CategoryToDTO(category);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(categoryDTO);

    }


    /**
     * Returns all categories based on the groupId
     * @param groupId The id of the group
     * @return The categories of the group
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<List<CategoryDTO>> getCategories(
            @PathVariable(value="groupId") Long groupId){

        //Validate input
        if(groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        //Get categories
        GroupDocument groupDocument = groupDocumentService.getGroupDocument(groupId);

        if(groupDocument == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        List<CategoryDTO> responseList = new ArrayList<>();
        groupDocument.categories.forEach(category ->
                responseList.add(MapperUtil.CategoryToDTO(category)));

        return ResponseEntity.
                status(HttpStatus.OK).
                body(responseList);
    }


    /**
     * Updates a category for a given group
     * @param groupId The id of the group
     * @param category The updated category
     * @return The updated category
     */
    @PutMapping(
            value="/{groupId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable(value="groupId") Long groupId,
            @RequestBody CategoryDTO category){

        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(CategoryDTO.class);

        if(!validator.validate(category, true) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Update category
        Category updatedCategory = groupDocumentService.updateCategory(
                groupId, MapperUtil.DTOToExistingCategory(category));

        if(updatedCategory == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        CategoryDTO categoryDTO = MapperUtil.CategoryToDTO(updatedCategory);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(categoryDTO);
    }


    /**
     * Updates a category for a given group
     * @param groupId The id of the group
     * @param category The updated category
     * @return The updated category
     */
    @PostMapping(
            value="/{groupId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CategoryDTO> addCategory(
            @PathVariable(value="groupId") Long groupId,
            @RequestBody CategoryDTO category){

        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(CategoryDTO.class);

        if(!validator.validate(category, false) || groupId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Insert category
        Category insertCategory = groupDocumentService.insertCategory(
                groupId, MapperUtil.DTOToCategory(category));

        if(insertCategory == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Return dto
        CategoryDTO categoryDTO = MapperUtil.CategoryToDTO(insertCategory);

        return ResponseEntity.
                status(HttpStatus.OK).
                body(categoryDTO);
    }


    /**
     * Deletes a category for a given group and given category
     * @param groupId The id of the group
     * @param categoryId The id of the category
     * @return The status code
     */
    @DeleteMapping("/{groupId}/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable(value="groupId") Long groupId,
            @PathVariable(value="categoryId") String categoryId){

        //Validate input
        if(groupId == null || categoryId == null || categoryId.isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Delete category
        groupDocumentService.deleteCategory(
                groupId, DocObjectIdUtil.toObjectId(categoryId));

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
