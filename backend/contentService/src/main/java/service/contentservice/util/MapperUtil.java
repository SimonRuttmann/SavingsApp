package service.contentservice.util;

import service.contentservice.businessmodel.account.PersonDTO;
import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.businessmodel.content.SavingEntryDTO;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.SavingEntry;
import service.contentservice.persistence.relational.entity.Person;

public class MapperUtil {

    public static Category DTOToCategory(CategoryDTO dto){
        Category category = new Category();
        category.setId(DocObjectIdUtil.toObjectId(dto.getId()));
        category.setName(dto.getName());
        return category;
    }

    public static CategoryDTO CategoryToDTO(Category category){
        CategoryDTO categoryDto =  new CategoryDTO();
        categoryDto.setId(DocObjectIdUtil.toHexString(category.getId()));
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static SavingEntry DTOToSavingEntry(SavingEntryDTO dto){
        SavingEntry savingEntry = new SavingEntry();
        savingEntry.setCategory(DTOToCategory(dto.getCategory()));
        savingEntry.setDescription(dto.getDescription());
        savingEntry.setCreationDate(dto.getCreationDate());
        savingEntry.setCreator(dto.getCreator());
        savingEntry.setId(DocObjectIdUtil.toObjectId(dto.getId()));
        savingEntry.setCostBalance(dto.getCostBalance());
        savingEntry.setName(dto.getName());
        return savingEntry;
    }

    public static SavingEntryDTO SavingEntryToDTO(SavingEntry savingEntry){
        SavingEntryDTO savingEntryDto =  new SavingEntryDTO();
        savingEntryDto.setId(DocObjectIdUtil.toHexString(savingEntry.getId()));
        savingEntryDto.setCategory(CategoryToDTO(savingEntry.getCategory()));
        savingEntryDto.setCostBalance(savingEntry.getCostBalance());
        savingEntryDto.setCreator(savingEntry.getCreator());
        savingEntryDto.setName(savingEntry.getName());
        savingEntryDto.setDescription(savingEntry.getDescription());
        savingEntryDto.setCreationDate(savingEntry.getCreationDate());
        return savingEntryDto;
    }


    public static PersonDTO PersonToDTO(Person person){
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setEmail(person.getEmail());
        dto.setUsername(person.getUsername());
        return dto;
    }
}