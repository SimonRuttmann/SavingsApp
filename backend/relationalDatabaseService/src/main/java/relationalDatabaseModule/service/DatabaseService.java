package relationalDatabaseModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import relationalDatabaseModule.model.*;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class DatabaseService implements IDatabaseService {

    private final KeycloakRepository personRepository;
    private final GroupRepository groupRepository;
    private final InvitationRepository invitationRepository;


    @Autowired
    public DatabaseService(
            KeycloakRepository personRepository,
            GroupRepository groupRepository,
            InvitationRepository invitationRepository) {

        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
        this.invitationRepository = invitationRepository;
    }


    //Simple find

    @Override
    @Transactional
    public KPerson getPersonById(UUID id){
        Optional<KPerson> person = personRepository.findById(id.toString());
        if(person.isEmpty()) return null;
        personRepository.detach(person.get());
        return person.get();
    }
    @Override
    @Transactional
    public KPerson getPersonByUsername(String username){
        Optional<KPerson> person = personRepository.findPersonByUsername(username);
        if(person.isEmpty()) return null;
        personRepository.detach(person.get());
        return person.get();
    }


    @Override
    @Transactional
    public Group getGroupById(Long id){
        Optional<Group> group = groupRepository.findById(id);
        if(group.isEmpty()) return null;
        groupRepository.detach(group.get());
        return group.get();
    }

    @Override
    @Transactional
    public Collection<Group> getGroupsOfPersonId(UUID id){

        var person = personRepository.findById(id.toString());

        if(person.isEmpty()) return null;

        var value = person.get().getGroups();
        personRepository.detach(person.get());

        return value;
    }

    @Override
    @Transactional
    public  Collection<KPerson> getPersonsOfGroupId(Long id){

        Optional<Group> group =  groupRepository.findById(id);

        if(group.isEmpty()) return null;

        var value = group.get().getMembers();
        groupRepository.detach(group.get());

        return value;
    }


    //Members

    @Override
    @Transactional
    public Pair<KPerson, Group> addPersonToGroup(UUID personId, Long groupId){

        KPerson person = personRepository.getById(personId.toString());
        Group group = groupRepository.getById(groupId);

        person.addGroup(group);

        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        personRepository.detach(person);
        groupRepository.detach(group);

        return new Pair<>(person, group);
    }


    @Override
    @Transactional
    public Pair<KPerson, Group> removePersonFromGroup(UUID personId, Long groupId){

        //check if person and group exists

        KPerson person = personRepository.getById(personId.toString());
        Group group = groupRepository.getById(groupId);

        // check if person is in the group

        boolean check = checkIfPersonIsMember(personId, groupId);
        if(!check) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The user with id "+personId+" is not member of group with Id "+groupId);
        }


        person.removeGroup(group);

        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        personRepository.detach(person);
        groupRepository.detach(group);

        return new Pair<>(person, group);
    }

    //Invitations

    @Override
    @Transactional
    public Invitation addInvitation(String username, Long groupId, UUID inviterPerson) {

        boolean isInviterMember = checkIfPersonIsMember(inviterPerson, groupId);
        if(!isInviterMember) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Person is not in Group it is inviting others in");
        }

        Optional<KPerson> optPerson = personRepository.findPersonByUsername(username);
        Optional<Group> optGroup = groupRepository.findById(groupId);

        if(optGroup.isEmpty() || optPerson.isEmpty()) return null;

        if(Objects.equals(optGroup.get().getGroupName(), "Ich")){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Other Users can't be invited in personal Group");
        }

        KPerson person = optPerson.get();
        Group group = optGroup.get();

        Invitation invitation = new Invitation();
        invitation.setInvitationStatus(InvitationStatus.OPEN);

        invitation.getId().setDate(new Date());
        invitation.setInvitedPerson(person);
        invitation.setRequestedGroup(group);

        group.getInvitations().add(invitation);
        person.getInvitations().add(invitation);

        invitationRepository.saveAndFlush(invitation);
        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        invitationRepository.detach(invitation);
        personRepository.detach(person);
        groupRepository.detach(group);

        return invitation;
    }


    @Override
    @Transactional
    public Invitation declineInvitation(UUID userId, Long groupId) {

        KPerson person = getPersonById(userId);
        Group group = getGroupById(groupId);

        if(group == null || person == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Person or/and Group does not exist.");
        }


        var invitations = invitationRepository.findByInvitedPersonAndRequestedGroup(person, group);

        if(invitations.isEmpty()) return null;
        Invitation invitation = invitations.get(0);

        invitation.setInvitationStatus(InvitationStatus.DECLINED);
        invitationRepository.saveAndFlush(invitation);

        return invitation;
    }

    @Override
    @Transactional
    public Invitation acceptInvitation(UUID userId, Long groupId) {

        KPerson person = getPersonById(userId);
        Group group = getGroupById(groupId);

        if(group == null || person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person or/and Group does not exist.");
        }

        var invitations = invitationRepository.findByInvitedPersonAndRequestedGroup(person, group);

        if(invitations.isEmpty()) return null;
        invitations.forEach(invitation -> {
            invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
            invitationRepository.saveAndFlush(invitation);

            invitationRepository.detach(invitation);
        });

        return invitations.get(0);
    }

    @Override
    public List<Invitation> getAllInvitations(UUID userId) {
        KPerson person = personRepository.getById(userId.toString());
        return invitationRepository.findByInvitedPersonAndInvitationStatus(person,InvitationStatus.OPEN);

    }

    //Remove


    @Override
    @Transactional
    public void removeGroup(Long id){

        Group group = groupRepository.getById(id);

        group.getMembers().forEach(person -> person.removeGroup(group));
        group.getInvitations().forEach(inv -> inv.getInvitedPerson().getInvitations().remove(inv));

        invitationRepository.deleteAll(group.getInvitations());
        this.groupRepository.delete(group);
    }

    //Saves


    @Override
    @Transactional
    public Group saveGroup(Group group){

        var value =  this.groupRepository.saveAndFlush(group);

        groupRepository.detach(group);
        return value;
    }

    @Override
    @Transactional
    public Invitation saveInvitation(Invitation invitation){

        var value = this.invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
        return value;
    }

    @Override
    public Boolean checkIfPersonIsMember(UUID personId, Long groupId) {

        var person = getPersonById(personId);

        return  person.
                getGroups().
                stream().
                anyMatch( group -> Objects.equals(group.getId(), groupId));
    }

}
