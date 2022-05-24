package service.userservice;

import dtoAndValidation.dto.user.*;
import dtoAndValidation.util.MapperUtil;
import model.AtomicIntegerModel;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import relationalDatabaseModule.model.Group;
import relationalDatabaseModule.model.Invitation;
import relationalDatabaseModule.model.Pair;
import relationalDatabaseModule.model.Person;
import relationalDatabaseModule.service.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import service.RedisDatabaseService;

@Service
public class UserManagementService implements IUserManagementService {
    @Autowired
    private RedisDatabaseService redisDataBaseService;

    private final DatabaseService databaseService;

    @Autowired
    public UserManagementService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Person
    @Override
    public PersonDTO register(PersonDTO registerDto) {
        Person newPerson = new Person(registerDto.getId(), registerDto.getUsername(), registerDto.getEmail());
        var p =  databaseService.savePerson(newPerson);
        redisDataBaseService.incrementValue(AtomicIntegerModel.COUNTUSERS);
        return MapperUtil.PersonToDTO(p);
    }

    @Override
    public PersonDTO getUser(UUID userId) {
        Person person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+userId+" don't exists");
        return MapperUtil.PersonToDTO(person);
    }

    @Override
    public Collection<GroupDTO> getAllGroupsOfPerson(UUID personId) {
        Collection<GroupDTO> groupsDto = new HashSet<>();
        Collection<Group> groups = databaseService.getGroupsOfPersonId(personId);
        if (groups == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user with id "+personId+" is not in the db");

        groups.forEach( group -> {
            groupsDto.add(MapperUtil.GroupToDTO(group));
        });
        return groupsDto;
    }

    @Override
    public PersonDTO deleteUser(UUID userId) {
        Person person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+ userId +" don't exists");

        databaseService.removePerson(userId);
        return MapperUtil.PersonToDTO(person);
    }

    // Group
    @Override
    public GroupDTO registerGroup(HttpServletRequest request, GroupDTO registerDto) {
        UUID userId = getUserId(request);
        Group newGroup = new Group(registerDto.getGroupName());
        Group savedGroup = databaseService.saveGroup(newGroup);
        // add the creator-user to group
        databaseService.addPersonToGroup(userId, savedGroup.getId());
        return MapperUtil.GroupToDTO(savedGroup);
    }

    @Override
    public GroupDTO getGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");
        return MapperUtil.GroupToDTO(group);
    }

    @Override
    public Collection<PersonDTO> getAllUserfromGroup(Long groupId) {
        Collection<PersonDTO> personsDTO = new HashSet<>();
        Collection<Person> members = databaseService.getPersonsOfGroupId(groupId);
        if (members == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the group with id "+groupId+" is not in the db");

        members.forEach( member -> {
            personsDTO.add(MapperUtil.PersonToDTO(member));
        });

        return personsDTO;
    }

    @Override
    public GroupDTO leaveGroup(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        Pair<Person, Group> pair = databaseService.removePersonFromGroup(userId, groupId);
        return MapperUtil.GroupToDTO(pair.getSecond());
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");

        databaseService.removeGroup(groupId);
        return MapperUtil.GroupToDTO(group);
    }

    //Invitaion
    @Override
    public InvitationDTO invite(InviteDTO newInvitation) {


        //Check also if userId and groupID exists
        Invitation invitation = databaseService.addInvitation(newInvitation.userId, newInvitation.groupId);

        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user or group can not be find");

        return MapperUtil.InvitationToDTO(invitation);
    }

    @Override
    public List<InvitationDTO> getInvitations(HttpServletRequest request) {
        List<InvitationDTO> listDto = new ArrayList<>();
        UUID userId = getUserId(request);
        List<Invitation> invitations = databaseService.getAllInvitations(userId);
        if(invitations != null) {
            invitations.forEach(invitation -> {
                listDto.add(MapperUtil.InvitationToDTO(invitation));
            });
        }
        listDto.sort(Comparator.comparing(InvitationDTO::getCreatedOn));
        return listDto;
    }

    @Override
    public InvitationDTO acceptInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);


        Invitation invitation = databaseService.acceptInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        databaseService.addPersonToGroup(userId, groupId);

        return MapperUtil.InvitationToDTO(invitation);
    }

    @Override
    public InvitationDTO declineInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        //InvitationCompoundId invitationCompoundId = new InvitationCompoundId(userId, groupId);
        Invitation invitation = databaseService.declineInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        return MapperUtil.InvitationToDTO(invitation);
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
