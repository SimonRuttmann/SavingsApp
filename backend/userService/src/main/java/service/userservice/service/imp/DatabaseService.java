package service.userservice.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import service.userservice.persistence.entity.userdata.*;
import service.userservice.persistence.repository.userdata.GroupRepository;
import service.userservice.persistence.repository.userdata.InvitationRepository;
import service.userservice.persistence.repository.userdata.PersonRepository;
import service.userservice.service.IDatabaseService;
import service.userservice.util.Pair;
import mo
import javax.transaction.Transactional;
import java.util.*;

@Component
public class DatabaseService implements IDatabaseService {

    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;
    private final InvitationRepository invitationRepository;

    @Autowired
    public DatabaseService(
            PersonRepository personRepository,
            GroupRepository groupRepository,
            InvitationRepository invitationRepository) {

        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
        this.invitationRepository = invitationRepository;
    }


    //Simple find

    @Override
    @Transactional
    public Person getPersonById(UUID id){
        Optional<Person> person = personRepository.findById(id);
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

        var person = personRepository.findById(id);

        if(person.isEmpty()) return null;

        var value = person.get().getGroups();
        personRepository.detach(person.get());

        return value;
    }

    @Override
    @Transactional
    public  Collection<Person> getPersonsOfGroupId(Long id){

        Optional<Group> group =  groupRepository.findById(id);

        if(group.isEmpty()) return null;

        var value = group.get().getMembers();
        groupRepository.detach(group.get());

        return value;
    }


    //Members

    @Override
    @Transactional
    public Pair<Person, Group> addPersonToGroup(UUID personId, Long groupId){

        Person person = personRepository.getById(personId);
        Group group = groupRepository.getById(groupId);

        person.addGroup(group);

        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        personRepository.detach(person);
        groupRepository.detach(group);

        return new Pair<>(person, group);
    }

    // TODO warum wird hier nicht geprüft ob die person existiert
    @Override
    @Transactional
    public Pair<Person, Group> removePersonFromGroup(UUID personId, Long groupId){

        //check if person and group exists

        Person person = personRepository.getById(personId);
        Group group = groupRepository.getById(groupId);
        if(person == null || group == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this group and/or person do not exist.");
        // check if person is in the group
        Boolean check = checkIfPersonIsMember(personId, groupId);
        if(check == false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user with id "+personId+" is not member of group with Id "+groupId);



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
    public Invitation addInvitation(UUID personId, Long groupId) {

        Optional<Person> optPerson = personRepository.findById(personId);
        Optional<Group> optGroup = groupRepository.findById(groupId);

        if(optGroup.isEmpty() || optPerson.isEmpty()) return null;
        Person person = optPerson.get();
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

    // todo: new here invitation get deleted
    @Override
    @Transactional
    public Invitation declineInvitation(UUID userId, Long groupId) {
        Person person = getPersonById(userId);
        Group group = getGroupById(groupId);
        if(group == null || person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person or/and Group does not exist.");


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
        Person person = getPersonById(userId);
        Group group = getGroupById(groupId);
        if(group == null || person == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person or/and Group does not exist.");

        //Optional<Invitation> optInvitation = invitationRepository.findById(invitationCompoundId);
        var invitations = invitationRepository.findByInvitedPersonAndRequestedGroup(person, group);

        if(invitations.isEmpty()) return null;
        invitations.forEach(invitation -> {
            invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
            invitationRepository.saveAndFlush(invitation);

            invitationRepository.detach(invitation);
        });
        Invitation invitation = invitations.get(0);


        return invitation;
    }

    @Override
    public List<Invitation> getAllInvitations(UUID userId) {
        Person person = personRepository.getById(userId);
        List<Invitation> invitationList = invitationRepository.findByInvitedPersonAndInvitationStatus(person,InvitationStatus.OPEN);
        if (invitationList.isEmpty()) return null;
        return invitationList;
    }

    //Remove

    @Override
    @Transactional
    public void removePerson(UUID id){

         Person person = personRepository.getById(id);

         person.getGroups().forEach(group -> group.removeMember(person));
         person.getInvitations().forEach(inv -> inv.getRequestedGroup().getInvitations().remove(inv));

         invitationRepository.deleteAll(person.getInvitations());
         personRepository.delete(person);
    }

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
    public Person savePerson(Person person){



        var value = this.personRepository.saveAndFlush(person);


        personRepository.detach(person);
        return value;
    }

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
        boolean answer = false;
        var person = getPersonById(personId);
        for (Group group:person.getGroups()
             ) {
            if(group.getId() == groupId)  answer = true;
        }
        return answer;
    }

}
