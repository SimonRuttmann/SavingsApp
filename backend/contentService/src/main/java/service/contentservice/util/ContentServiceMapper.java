package service.contentservice.util;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.DocObjectIdUtil;
import documentDatabaseModule.model.SavingEntry;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.dto.user.PersonDTO;
import relationalDatabaseModule.model.KPerson;

import java.util.UUID;

public class ContentServiceMapper {

    public static Category mapDtoToCategory(CategoryDTO dto){

        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }

    public static Category mapDtoToExistingCategory(CategoryDTO dto){

        Category category = new Category();
        category.setName(dto.getName());
        category.setId(DocObjectIdUtil.toObjectId(dto.getId()));

        return category;
    }

    public static CategoryDTO mapCategoryToDto(Category category){

        CategoryDTO categoryDto =  new CategoryDTO();
        categoryDto.setId(DocObjectIdUtil.toHexString(category.getId()));
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public static SavingEntry mapDtoToSavingEntry(SavingEntryDTO dto){

        SavingEntry savingEntry = mapDtoToSavingEntryNoId(dto);
        savingEntry.setId(DocObjectIdUtil.toObjectId(dto.getId()));

        return savingEntry;
    }

    public static SavingEntry mapDtoToSavingEntryNoId(SavingEntryDTO dto){

        SavingEntry savingEntry = new SavingEntry();
        savingEntry.setCategory(mapDtoToExistingCategory(dto.getCategory()));
        savingEntry.setDescription(dto.getDescription());
        savingEntry.setCreationDate(dto.getCreationDate());
        savingEntry.setCreator(dto.getCreator());
        savingEntry.setCostBalance(dto.getCostBalance());
        savingEntry.setName(dto.getName());

        return savingEntry;
    }

    public static SavingEntryDTO mapSavingEntryToDto(SavingEntry savingEntry){

        SavingEntryDTO savingEntryDto =  new SavingEntryDTO();
        savingEntryDto.setId(DocObjectIdUtil.toHexString(savingEntry.getId()));
        savingEntryDto.setCategory(mapCategoryToDto(savingEntry.getCategory()));
        savingEntryDto.setCostBalance(savingEntry.getCostBalance());
        savingEntryDto.setCreator(savingEntry.getCreator());
        savingEntryDto.setName(savingEntry.getName());
        savingEntryDto.setDescription(savingEntry.getDescription());
        savingEntryDto.setCreationDate(savingEntry.getCreationDate());

        return savingEntryDto;
    }

    public static PersonDTO mapPersonToDto(KPerson person){

        PersonDTO dto = new PersonDTO();
        dto.setId(UUID.fromString(person.getId()));
        dto.setEmail(person.getEmail());
        dto.setUsername(person.getUsername());

        return dto;
    }

}
