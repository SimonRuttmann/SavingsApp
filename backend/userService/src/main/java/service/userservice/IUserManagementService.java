package service.userservice;

import dtoAndValidation.dto.user.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import java.util.List;
import java.util.UUID;


public interface IUserManagementService {

    //Person
    PersonDTO register(PersonDTO registerDto);
    PersonDTO getUser(UUID userId);
    Collection<GroupDTO> getAllGroupsOfPerson(UUID personId);
    PersonDTO deleteUser(UUID userId);

    //Group
    GroupDTO registerGroup(HttpServletRequest request, GroupDTO registerDto);
    GroupDTO getGroup(Long groupId);
    Collection<PersonDTO> getAllUserfromGroup(Long groupId);
    GroupDTO leaveGroup(HttpServletRequest request, Long groupId);
    GroupDTO deleteGroup(Long groupId);

    //Invitation
    InvitationDTO invite(InviteDTO newInvitation);
    List<InvitationDTO> getInvitations(HttpServletRequest request);
    InvitationDTO acceptInvitation(HttpServletRequest request, Long groupId);
    InvitationDTO declineInvitation(HttpServletRequest request, Long groupId);


    Boolean checkIfPersonIsMember(UUID personId, Long groupId);
}

