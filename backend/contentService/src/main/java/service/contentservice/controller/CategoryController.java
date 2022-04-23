package service.contentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.contentservice.persistence.documentbased.SavingEntry;
import service.contentservice.services.IDatabaseService;

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
    public String updateCategory(
            @PathVariable(value="groupId") Integer groupId,
            @RequestBody SavingEntry savingEntry){
        return "";
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
