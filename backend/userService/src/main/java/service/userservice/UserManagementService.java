package service.userservice;

import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.service.IGroupDocumentService;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.user.*;
import dtoAndValidation.util.MapperUtil;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import relationalDatabaseModule.model.*;
import relationalDatabaseModule.service.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import relationalDatabaseModule.service.KeycloakRepository;
import service.RedisDatabaseService;

@Service
public class UserManagementService implements IUserManagementService {
    @Autowired
    private RedisDatabaseService redisDataBaseService;
    private final DatabaseService databaseService;
    private final IGroupDocumentService groupDocumentService;
    private final KeycloakRepository keycloakRepository;

    @Autowired
    public UserManagementService(DatabaseService databaseService, IGroupDocumentService groupDocumentService, KeycloakRepository keycloakRepository) {
        this.databaseService = databaseService;
        this.groupDocumentService = groupDocumentService;
        this.keycloakRepository = keycloakRepository;
    }

    // Person
//    @Override
//    public PersonDTO register(PersonDTO registerDto) {
//        Person newPerson = new Person(registerDto.getId(), registerDto.getUsername(), registerDto.getEmail());
//        var p =  databaseService.savePerson(newPerson);
//        redisDataBaseService.incrementValue(AtomicIntegerModel.COUNTUSERS);
//        // open Group in postgres - temp solution becaues copy of registerGroup
//        Group newGroup = new Group("Ich", true );
//        Group savedGroup = databaseService.saveGroup(newGroup);
//        databaseService.addPersonToGroup(registerDto.getId(), savedGroup.getId());
//        // open groupDoc in Mongo
//        var d =new GroupDocument();
//        d.groupId = savedGroup.getId();
//        groupDocumentService.createDocument(d);
//        return new PersonDTO(newPerson.getId(), newPerson.getUsername(), newPerson.getEmail());
//    }

    @Override
    public PersonDTO getUser(UUID userId) {
        KPerson person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+userId+" don't exists");
        return MapperUtil.PersonToDTO(person);
    }

    @Override
    public Collection<GroupDTO> getAllGroupsOfPerson(HttpServletRequest request) {
        UUID personId = getUserId(request);
        Collection<GroupDTO> groupsDto = new HashSet<>();
        Collection<Group> groups = databaseService.getGroupsOfPersonId(personId);
        if (groups == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user with id "+personId+" is not in the db");

        // new registered User
        if (groups.isEmpty()){
            // open Group in postgres
            Group newGroup = new Group("Ich", true );
            Group savedGroup = databaseService.saveGroup(newGroup);
            databaseService.addPersonToGroup(personId, savedGroup.getId());
            newMongoGroup(savedGroup.getId());

            groups = databaseService.getGroupsOfPersonId(personId);
        }
        groups.forEach( group -> {
            groupsDto.add(MapperUtil.GroupToDTO(group));
        });
        return groupsDto;
    }

    @Override
    public Collection<String> getAllUsernames() {
        Collection<String> usernames = new HashSet<>();

        List<KPerson> persons = keycloakRepository.findAll();
        persons.forEach(person -> {
            if(!Objects.equals(person.getUsername(), "admin")) {usernames.add(person.getUsername());}

        });
        return usernames;
    }

//    @Override
//    public PersonDTO deleteUser(UUID userId) {
//        KPerson person = databaseService.getPersonById(userId);
//        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+ userId +" don't exists");
//
//        databaseService.removePerson(userId);
//        return MapperUtil.PersonToDTO(person);
//    }

    // Group
    @Override
    public GroupDTO registerGroup(HttpServletRequest request, GroupDTO registerDto) {
        UUID userId = getUserId(request);
        Group newGroup = new Group(registerDto.getGroupName(), false);
        Group savedGroup = databaseService.saveGroup(newGroup);
        // add the creator-user to group
        databaseService.addPersonToGroup(userId, savedGroup.getId());
        newMongoGroup(savedGroup.getId());
        return MapperUtil.GroupToDTO(savedGroup);
    }

    @Override
    public GroupDTO leaveGroup(HttpServletRequest request, Long groupId) {
        //test group
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");
        if(Objects.equals(group.getGroupName(), "Ich")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Personal Group can't be left");

        UUID userId = getUserId(request);
        Pair<KPerson, Group> pair = databaseService.removePersonFromGroup(userId, groupId);
        if(pair.getSecond().getMembers().isEmpty()){
            databaseService.removeGroup(groupId);
            groupDocumentService.deleteDocument(groupId);
        }
        return MapperUtil.GroupToDTO(pair.getSecond());
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");
        if(Objects.equals(group.getGroupName(), "Ich")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Personal Group can't be deleted");
        databaseService.removeGroup(groupId);
        groupDocumentService.deleteDocument(groupId);
        return MapperUtil.GroupToDTO(group);
    }

    //Invitaion
    @Override
    public InvitationDTO invite(InviteDTO newInvitation, HttpServletRequest request) {
        // check if person is inviting themself
        UUID registeredUserId = getUserId(request);
        KPerson registeredUser = databaseService.getPersonById(registeredUserId);
        if(Objects.equals(newInvitation.username, registeredUser.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't invite themself");
        //Check also if userId and groupID exists
        Invitation invitation = databaseService.addInvitation(newInvitation.username, newInvitation.groupId, registeredUserId);

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
        listDto.sort(Comparator.comparing(InvitationDTO::getDate));
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

    @Override
    public Collection<PersonDTO> getAllUser() {
        Collection<PersonDTO> personsDTO = new HashSet<>();

        List<KPerson> persons = keycloakRepository.findAll();
        persons.forEach(person -> {
            personsDTO.add(MapperUtil.PersonToDTO(person));

        });
        return personsDTO;
    }


    private UUID getUserId(HttpServletRequest request){
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getPrincipal().toString();
        return UUID.fromString(userId);
    }

    private void newMongoGroup(Long groupId){
        // open groupDoc in Mongo
        var d = new GroupDocument();
        d.groupId = groupId;
        GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);

        // create default Categories
        CategoryDTO miete = new CategoryDTO("Miete");
        CategoryDTO lebensmittel = new CategoryDTO("Lebensmittel");
        CategoryDTO restaurant = new CategoryDTO("Restaurant");

        groupDocumentService.insertCategory(savedGroupDoc.groupId,  MapperUtil.DTOToCategory(miete));
        groupDocumentService.insertCategory(savedGroupDoc.groupId,  MapperUtil.DTOToCategory(lebensmittel));
        groupDocumentService.insertCategory(savedGroupDoc.groupId,  MapperUtil.DTOToCategory(restaurant));

    }
}
