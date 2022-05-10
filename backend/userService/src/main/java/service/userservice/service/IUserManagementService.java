package service.userservice.service;

import service.userservice.businessmodel.account.*;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Invitation;
import service.userservice.persistence.entity.userdata.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public interface IUserManagementService {


    Person register(RegisterPersonDTO registerDto);
    
    Invitation invite(InviteDTO newInvitation);
    List<Invitation> getInvitations(HttpServletRequest request);
    Invitation acceptInvitation(HttpServletRequest request, Long groupId);
    Invitation declineInvitation(HttpServletRequest request, Long groupId);




    Group registerGroup(HttpServletRequest request, RegisterGroupDTO registerDto);
    Collection<Person> getAllUserfromGroup(Long groupId);
    Collection<Group> getAllGroupsOfPerson(UUID personId);
    GroupDTO leaveGroup(HttpServletRequest request, Long groupId);
}

