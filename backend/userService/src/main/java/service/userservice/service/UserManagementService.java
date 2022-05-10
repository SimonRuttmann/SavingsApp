package service.userservice.service;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import service.contentservice.businessmodel.content.CategoryDTO;


import service.contentservice.services.ValidateAndResolveDocumentService;
import service.userservice.businessmodel.account.*;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Invitation;
import service.userservice.persistence.entity.userdata.InvitationCompoundId;
import service.userservice.persistence.entity.userdata.Person;
import service.userservice.persistence.repository.userdata.PersonRepository;
import service.userservice.util.Pair;
import service.userservice.validation.InviteValidator;
import service.userservice.validation.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserManagementService implements IUserManagementService {

    private final PersonRepository personRepository;
    private final DatabaseService databaseService;

    @Autowired
    public UserManagementService(PersonRepository personRepository, DatabaseService databaseService) {
        this.personRepository = personRepository;
        this.databaseService = databaseService;
    }

    @Override
    public Invitation invite(InviteDTO newInvitation) {


        //Validate
        var identifier = new InviteValidator().validate(newInvitation, false);

        //Check also if userId and groupID exists
        Invitation invitation = databaseService.addInvitation(newInvitation.userId, newInvitation.groupId);

        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user or group can not be find");

        return invitation;
    }



    @Override
    public Person register(RegisterPersonDTO registerDto) {
        Person newPerson = new Person(registerDto.id, registerDto.username, registerDto.email);
        return databaseService.savePerson(newPerson);
    }

    @Override
    public Group registerGroup(HttpServletRequest request, RegisterGroupDTO registerDto) {
        UUID userId = getUserId(request);
        Group newGroup = new Group(registerDto.groupName);
        Group savedGroup = databaseService.saveGroup(newGroup);
        // add the creator-user to group
        databaseService.addPersonToGroup(userId, savedGroup.getId());
        return savedGroup;
    }

    @Override
    public List<Invitation> getInvitations(HttpServletRequest request) {
        UUID userId = getUserId(request);
        // alle Invitation getten
        return databaseService.getAllInvitations(userId);
    }

    @Override
    public Collection<Person> getAllUserfromGroup(Long groupId) {
        return databaseService.getPersonsOfGroupId(groupId);
    }

    @Override
    public Collection<Group> getAllGroupsOfPerson(UUID personId) {
        Collection<Group> groups = databaseService.getGroupsOfPersonId(personId);
        if (groups == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user with id "+personId+" is not in the db");
        return databaseService.getGroupsOfPersonId(personId);
    }

    @Override
    public Invitation acceptInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        InvitationCompoundId invitationCompoundId = new InvitationCompoundId(userId, groupId);
        Invitation invitation = databaseService.acceptInvitation(invitationCompoundId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This group or user is not in db");
        return invitation;
    }

    @Override
    public Invitation declineInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        InvitationCompoundId invitationCompoundId = new InvitationCompoundId(userId, groupId);
        Invitation invitation = databaseService.declineInvitation(invitationCompoundId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This group or user is not in db");
        return invitation;
    }

    @Override
    public GroupDTO leaveGroup(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        Pair<Person, Group> pair = databaseService.removePersonFromGroup(userId, groupId);
        return pair.getSecond().toDTO();
    }

    private UUID getUserId(HttpServletRequest request){
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getPrincipal().toString();
        return UUID.fromString(userId);
    }
}
