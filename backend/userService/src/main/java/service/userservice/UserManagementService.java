package service.userservice;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.service.IGroupDocumentService;
import dtoAndValidation.dto.user.*;
import dtoAndValidation.validation.ValidatorFactory;
import model.AtomicIntegerModel;
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
import service.userservice.util.UserServiceMapper;

@Service
public class UserManagementService implements IUserManagementService {
    private final RedisDatabaseService redisDataBaseService;
    private final DatabaseService databaseService;
    private final IGroupDocumentService groupDocumentService;
    private final KeycloakRepository keycloakRepository;

    private final List<String> defaultCategories = Arrays.asList("Miete","Lebensmittel","Restaurant");

    @Autowired
    public UserManagementService(DatabaseService databaseService, IGroupDocumentService groupDocumentService, KeycloakRepository keycloakRepository, RedisDatabaseService redisDataBaseService) {
        this.databaseService = databaseService;
        this.groupDocumentService = groupDocumentService;
        this.keycloakRepository = keycloakRepository;
        this.redisDataBaseService = redisDataBaseService;
    }

    // Person


    @Override
    public PersonDTO getUser(HttpServletRequest request) {
        UUID userId = getUserId(request);
        KPerson person = databaseService.getPersonById(userId);
        if (person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id"+userId+" don't exists");
        return UserServiceMapper.PersonToDTO(person);
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

            redisDataBaseService.incrementValue(AtomicIntegerModel.COUNTUSERS);
        }
        groups.forEach( group -> groupsDto.add(UserServiceMapper.GroupToDTO(group)));
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


    // Group
    @Override
    public GroupDTO registerGroup(HttpServletRequest request, GroupDTO registerDto) {
        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(GroupDTO.class);

        if(!validator.validate(registerDto, false))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid GroupDTO in RequestBody");

        UUID userId = getUserId(request);
        Group newGroup = new Group(registerDto.getGroupName(), false);
        Group savedGroup = databaseService.saveGroup(newGroup);
        // add the creator-user to group
        databaseService.addPersonToGroup(userId, savedGroup.getId());
        newMongoGroup(savedGroup.getId());
        return UserServiceMapper.GroupToDTO(savedGroup);
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
        return UserServiceMapper.GroupToDTO(pair.getSecond());
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        Group group = databaseService.getGroupById(groupId);
        if(group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group with id "+groupId+" don't exists.");
        if(Objects.equals(group.getGroupName(), "Ich")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Personal Group can't be deleted");
        databaseService.removeGroup(groupId);
        groupDocumentService.deleteDocument(groupId);
        return UserServiceMapper.GroupToDTO(group);
    }

    //Invitaion
    @Override
    public InvitationDTO invite(InviteDTO newInvitation, HttpServletRequest request) {
        //Validate input
        var validator = ValidatorFactory.getInstance().getValidator(InviteDTO.class);

        if(!validator.validate(newInvitation, false))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid InviteDTO in Requestbody");

        // check if person is inviting themself
        UUID registeredUserId = getUserId(request);
        KPerson registeredUser = databaseService.getPersonById(registeredUserId);

        if(Objects.equals(newInvitation.username, registeredUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't invite themself");
        }

        //check if invitation exists already
        List<Invitation> invs = databaseService.getAllInvitations(registeredUserId);
        var existingInvitation = invs.stream()
                .filter(invitation -> Objects.equals(invitation.getRequestedGroup().getId(), newInvitation.groupId))
                .findFirst();

        if(existingInvitation.isPresent()) {
            return UserServiceMapper.InvitationToDTO(existingInvitation.get());
        }

        //Check also if userId and groupID exists
        Invitation invitation = databaseService.addInvitation(newInvitation.username, newInvitation.groupId, registeredUserId);

        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user or group can not be find");

        return UserServiceMapper.InvitationToDTO(invitation);
    }

    @Override
    public List<InvitationDTO> getInvitations(HttpServletRequest request) {
        List<InvitationDTO> listDto = new ArrayList<>();
        UUID userId = getUserId(request);
        List<Invitation> invitations = databaseService.getAllInvitations(userId);

        invitations.forEach(invitation ->
                listDto.add(UserServiceMapper.InvitationToDTO(invitation)));

        listDto.sort(Comparator.comparing(InvitationDTO::getDate));
        return listDto;
    }

    @Override
    public InvitationDTO acceptInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);


        Invitation invitation = databaseService.acceptInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        databaseService.addPersonToGroup(userId, groupId);

        return UserServiceMapper.InvitationToDTO(invitation);
    }

    @Override
    public InvitationDTO declineInvitation(HttpServletRequest request, Long groupId) {
        UUID userId = getUserId(request);
        Invitation invitation = databaseService.declineInvitation(userId, groupId);
        if (invitation == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Invitation is not found");
        return UserServiceMapper.InvitationToDTO(invitation);
    }

    @Override
    public Boolean checkIfPersonIsMember(UUID personId, Long groupId) {
        return databaseService.checkIfPersonIsMember(personId, groupId);
    }

    @Override
    public Collection<PersonDTO> getAllUser() {
        Collection<PersonDTO> personsDTO = new HashSet<>();

        List<KPerson> persons = keycloakRepository.findAll();
        persons.forEach(person -> personsDTO.add(UserServiceMapper.PersonToDTO(person)));
        return personsDTO;
    }


    private UUID getUserId(HttpServletRequest request){
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getPrincipal().toString();
        return UUID.fromString(userId);
    }

    private void newMongoGroup(Long groupId){

        var document = new GroupDocument();
        document.groupId = groupId;
        GroupDocument savedGroupDoc = groupDocumentService.createDocument(document);

        for (var defaultCategory : defaultCategories) {
            Category category = new Category(defaultCategory);
            groupDocumentService.insertCategory(savedGroupDoc.groupId,  category);
        }

    }
}
