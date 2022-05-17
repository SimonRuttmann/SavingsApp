package service.userservice.service.imp;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import service.userservice.businessmodel.account.*;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Invitation;
import service.userservice.persistence.entity.userdata.InvitationCompoundId;
import service.userservice.persistence.entity.userdata.Person;

import service.userservice.persistence.repository.userdata.InvitationRepository;
import service.userservice.persistence.repository.userdata.PersonRepository;
import service.userservice.service.IUserManagementService;
import service.userservice.util.Pair;
import service.userservice.validation.InviteValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserManagementService implements IUserManagementService {

    private final DatabaseService databaseService;

    @Autowired
    public UserManagementService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Person
    @Override
    public PersonDTO register(RegisterPersonDTO registerDto) {
        Person newPerson = new Person(registerDto.id, registerDto.username, registerDto.email);
        return databaseService.savePerson(newPerson).toPersonDTO();
    }

    @Override
    public PersonDTO getUser(UUID userId) {
        Person person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+userId+" don't exists");
        return person.toPersonDTO();
    }

    @Override
    public Collection<GroupDTO> getAllGroupsOfPerson(UUID personId) {
        Collection<GroupDTO> groupsDto = new HashSet<>();
        Collection<Group> groups = databaseService.getGroupsOfPersonId(personId);
        if (groups == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user with id "+personId+" is not in the db");

        groups.forEach( group -> {
            groupsDto.add(group.toGroupDTO());
        });
        return groupsDto;
    }

    @Override
    public PersonDTO deleteUser(UUID userId) {
        Person person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+ userId +" don't exists");

        databaseService.removePerson(userId);
        return person.toPersonDTO();
    }

    // Group
    @Override
    public GroupDTO registerGroup(HttpServletRequest request, RegisterGroupDTO registerDto) {
        UUID userId = getUserId(request);
        Group newGroup = new Group(registerDto.groupName);
        Group savedGroup = databaseService.saveGroup(newGroup);
        // add the creator-user to group
        databaseService.addPersonToGroup(userId, savedGroup.getId());
        return savedGroup.toGroupDTO();
    }

    @Override
    public GroupDTO getGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");
        return group.toGroupDTO();
    }

    @Override
    public Collection<PersonDTO> getAllUserfromGroup(Long groupId) {
        Collection<PersonDTO> personsDTO = new HashSet<>();
        Collection<Person> members = databaseService.getPersonsOfGroupId(groupId);
        if (members == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the group with id "+groupId+" is not in the db");

        members.forEach( member -> {
            personsDTO.add(member.toPersonDTO());
        });

        return personsDTO;
    }

    @Override
    public GroupDTO leaveGroup(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        Pair<Person, Group> pair = databaseService.removePersonFromGroup(userId, groupId);
        return pair.getSecond().toGroupDTO();
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");

        databaseService.removeGroup(groupId);
        return group.toGroupDTO();
    }

    //Invitaion
    @Override
    public InvitationStatusDTO invite(InviteDTO newInvitation) {


        //Validate
        var identifier = new InviteValidator().validate(newInvitation, false);

        //Check also if userId and groupID exists
        Invitation invitation = databaseService.addInvitation(newInvitation.userId, newInvitation.groupId);

        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user or group can not be find");

        return invitation.toInvitationStatusDto();
    }

    @Override
    public List<InvitationDTO> getInvitations(HttpServletRequest request) {
        List<InvitationDTO> listDto = new ArrayList<>();
        UUID userId = getUserId(request);
        List<Invitation> invitations = databaseService.getAllInvitations(userId);
        if(invitations != null) {
            invitations.forEach(invitation -> {
                listDto.add(invitation.toInvitationDto());
            });
        }
        listDto.sort(Comparator.comparing(InvitationDTO::getCreatedOn));
        return listDto;
    }

    @Override
    public InvitationStatusDTO acceptInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);


        Invitation invitation = databaseService.acceptInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        databaseService.addPersonToGroup(userId, groupId);

        return invitation.toInvitationStatusDto();
    }

    @Override
    public InvitationStatusDTO declineInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        //InvitationCompoundId invitationCompoundId = new InvitationCompoundId(userId, groupId);
        Invitation invitation = databaseService.declineInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        return invitation.toInvitationStatusDto();
    }

    @Override
    public Boolean checkIfPersonIsMember(UUID personId, Long groupId) {
        return databaseService.checkIfPersonIsMember(personId, groupId);
    }


    private UUID getUserId(HttpServletRequest request){
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getPrincipal().toString();
        return UUID.fromString(userId);
    }
}
