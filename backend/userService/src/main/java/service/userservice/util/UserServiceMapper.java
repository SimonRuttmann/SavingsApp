package service.userservice.util;

import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.InvitationDTO;
import dtoAndValidation.dto.user.PersonDTO;
import relationalDatabaseModule.model.Group;
import relationalDatabaseModule.model.Invitation;
import relationalDatabaseModule.model.KPerson;

import java.util.UUID;

public class UserServiceMapper {


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
        groupDTO.setPersonGroup(group.getPersonGroupBool());
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
