package dtoAndValidation.util;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.DocObjectIdUtil;
import documentDatabaseModule.model.SavingEntry;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.InvitationDTO;
import dtoAndValidation.dto.user.PersonDTO;
import relationalDatabaseModule.model.Group;
import relationalDatabaseModule.model.Invitation;
import relationalDatabaseModule.model.KPerson;
import relationalDatabaseModule.model.Person;

import java.util.UUID;


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


    public static PersonDTO PersonToDTO(KPerson person){
        PersonDTO dto = new PersonDTO();
        dto.setId(UUID.fromString(person.getId()));
        dto.setEmail(person.getEmail());
        dto.setUsername(person.getUsername());
        return dto;
    }

    public static GroupDTO GroupToDTO(Group group){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        groupDTO.setPersonGroupBoolean(group.getPersonGroupBool());
        return groupDTO;
    }

    public static InvitationDTO InvitationToDTO(Invitation invitation){
        return new InvitationDTO(
                invitation.getInvitationStatus(),
                invitation.getRequestedGroup().getId(),
                invitation.getRequestedGroup().getGroupName(),
                invitation.getId().getDate());
    }

}
