package service.contentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.contentservice.persistence.relational.entity.*;
import service.contentservice.persistence.relational.repository.GroupRepository;
import service.contentservice.persistence.relational.repository.InvitationRepository;
import service.contentservice.persistence.relational.repository.PersonRepository;
import service.contentservice.util.Pair;

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
    public Person getPersonById(Long id){
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
    public Collection<Group> getGroupsOfPersonId(Long id){

        Optional<Person> person = personRepository.findById(id);

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
    public Pair<Person, Group> addPersonToGroup(Long personId, Long groupId){

        Person person = personRepository.getById(personId);
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
    public Pair<Person, Group> removePersonFromGroup(Long personId, Long groupId){

        Person person = personRepository.getById(personId);
        Group group = groupRepository.getById(groupId);

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
    public Invitation addInvitation(Long personId, Long groupId) {

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

    @Override
    @Transactional
    public Invitation declineInvitation(InvitationCompoundId invitationCompoundId) {

        Optional<Invitation> optInvitation = invitationRepository.findById(invitationCompoundId);

        if(optInvitation.isEmpty()) return null;
        Invitation invitation = optInvitation.get();

        invitation.setInvitationStatus(InvitationStatus.DECLINED);
        invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
        return invitation;
    }

    @Override
    @Transactional
    public Invitation acceptInvitation(InvitationCompoundId invitationCompoundId) {

        Optional<Invitation> optInvitation = invitationRepository.findById(invitationCompoundId);

        if(optInvitation.isEmpty()) return null;
        Invitation invitation = optInvitation.get();

        invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
        invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
        return invitation;
    }

    //Remove

    @Override
    @Transactional
    public void removePerson(Long id){

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

}
