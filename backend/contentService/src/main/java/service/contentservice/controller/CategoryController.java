package service.contentservice.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.SavingEntry;
import service.contentservice.validation.ValidatorFactory;

import java.util.List;


@RestController
@RequestMapping("/group")
public class CategoryController {

    //Get put post delete category

    //get category
    @GetMapping("/{groupId}/category/{categoryId}")
    public String getCategory(
            @PathVariable(value="groupId") Integer groupId,
            @PathVariable(value="categoryId") Integer categoryId){

        return "";
    }

    //get categories
    @GetMapping("/{groupId}/category")
    public String getCategories(
            @PathVariable(value="groupId") Integer groupId){

        return "";
    }

    //update category
    @PutMapping(
            value="/{groupId}/category",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Category> updateCategory(
            @PathVariable(value="groupId") Integer groupId,
            @RequestBody Category category){

        var validator = ValidatorFactory.getInstance().getValidator(Category.class);

        if(!validator.validate(category, true))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);



        return ResponseEntity.status(HttpStatus.OK).body( new Category(""));
    }


    //creates category
    @PostMapping(
            value="/{groupId}/category",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String addCategory(
            @PathVariable(value="groupId") Integer groupId,
            @RequestBody SavingEntry savingEntry){
        return "";
    }

    //deletes category
    @DeleteMapping("/{groupId}/category/{categoryId}")
    public String deleteCategory(
            @PathVariable(value="groupId") Integer groupId,
            @PathVariable(value="categoryId") Integer categoryId){
        return "";
    }



}
