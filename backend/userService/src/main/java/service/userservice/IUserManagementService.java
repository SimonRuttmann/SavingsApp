package service.userservice;

import main.java.dtoAndValidation.dto.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public interface IUserManagementService {

    //Person
    PersonDTO register(RegisterPersonDTO registerDto);
    PersonDTO getUser(UUID userId);
    Collection<GroupDTO> getAllGroupsOfPerson(UUID personId);
    PersonDTO deleteUser(UUID userId);

    //Group
    GroupDTO registerGroup(HttpServletRequest request, RegisterGroupDTO registerDto);
    GroupDTO getGroup(Long groupId);
    Collection<PersonDTO> getAllUserfromGroup(Long groupId);
    GroupDTO leaveGroup(HttpServletRequest request, Long groupId);
    GroupDTO deleteGroup(Long groupId);

    //Invitation
    InvitationStatusDTO invite(InviteDTO newInvitation);
    List<InvitationDTO> getInvitations(HttpServletRequest request);
    InvitationStatusDTO acceptInvitation(HttpServletRequest request, Long groupId);
    InvitationStatusDTO declineInvitation(HttpServletRequest request, Long groupId);


    Boolean checkIfPersonIsMember(UUID personId, Long groupId);
}

