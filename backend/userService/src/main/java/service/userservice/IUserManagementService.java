package service.userservice;

import dtoAndValidation.dto.user.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import java.util.List;
import java.util.UUID;


public interface IUserManagementService {

    //Person
    PersonDTO getUser(HttpServletRequest request);
    Collection<String> getAllUsernames();

    //Group
    GroupDTO registerGroup(HttpServletRequest request, GroupDTO registerDto);
    Collection<GroupDTO> getAllGroupsOfPerson(HttpServletRequest request);
    GroupDTO leaveGroup(HttpServletRequest request, Long groupId);
    GroupDTO deleteGroup(Long groupId);

    //Invitation
    InvitationDTO invite(InviteDTO newInvitation, HttpServletRequest request);
    List<InvitationDTO> getInvitations(HttpServletRequest request);
    InvitationDTO acceptInvitation(HttpServletRequest request, Long groupId);
    InvitationDTO declineInvitation(HttpServletRequest request, Long groupId);


    Boolean checkIfPersonIsMember(UUID personId, Long groupId);
    Collection<PersonDTO> getAllUser();

}

