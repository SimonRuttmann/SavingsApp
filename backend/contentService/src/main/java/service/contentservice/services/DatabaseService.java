package service.contentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Invitation;
import service.contentservice.persistence.relational.entity.InvitationStatus;
import service.contentservice.persistence.relational.entity.Person;
import service.contentservice.persistence.relational.repository.GroupRepository;
import service.contentservice.persistence.relational.repository.InvitationRepository;
import service.contentservice.persistence.relational.repository.PersonRepository;

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
    public Collection<Group> getGroupsOfPersonId(Long id){

        Optional<Person> person = personRepository.findById(id);

        if(person.isEmpty()) return new HashSet<>();

        var value = person.get().getGroups();
        personRepository.detach(person.get());

        return value;
    }

    @Override
    @Transactional
    public  Collection<Person> getPersonsOfGroupId(Long id){

        Optional<Group> group =  groupRepository.findById(id);

        if(group.isEmpty()) return new HashSet<>();

        var value = group.get().getMembers();
        groupRepository.detach(group.get());

        return value;
    }


    //Members

    @Override
    @Transactional
    public void addPersonToGroup(Person person, Group group){

        personRepository.attach(person);
        groupRepository.attach(group);

        person.getGroups().add(group);
        group.getMembers().add(person);

        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        personRepository.detach(person);
        groupRepository.detach(group);

    }

    @Override
    @Transactional
    public void removePersonFromGroup(Person person, Group group){

        personRepository.attach(person);
        groupRepository.attach(group);

        person.getGroups().remove(group);
        group.getMembers().remove(person);
        person.setUsername("ala");

        personRepository.saveAndFlush(person);
        groupRepository.saveAndFlush(group);

        groupRepository.detach(group);
        personRepository.detach(person);

    }

    //Invitations

    @Override
    @Transactional
    public void addInvitation(Person person, Group group) {

        personRepository.attach(person);
        groupRepository.attach(group);

        Invitation invitation = new Invitation();
        invitation.setInvitationStatus(InvitationStatus.OPEN);

        invitation.setInvitedPerson(person);
        invitation.setRequestedGroup(group);

        invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
        groupRepository.detach(group);
        personRepository.detach(person);
    }

    @Override
    @Transactional
    public void declineInvitation(Invitation invitation) {

        invitationRepository.attach(invitation);

        invitation.setInvitationStatus(InvitationStatus.DECLINED);
        invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
    }

    @Override
    @Transactional
    public void acceptInvitation(Invitation invitation) {

        invitationRepository.attach(invitation);

        invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
        invitationRepository.saveAndFlush(invitation);

        invitationRepository.detach(invitation);
    }

    //Remove

    @Override
    @Transactional
    public void removePerson(Person person){

         personRepository.attach(person);

         person.getGroups().forEach(group -> removePersonFromGroup(person, group));
         person.getInvitations().forEach(inv -> inv.getRequestedGroup().getInvitations().remove(inv));

         personRepository.delete(person);
    }

    @Override
    @Transactional
    public void removeGroup(Group group){

        groupRepository.attach(group);

        group.getMembers().forEach(person -> person.getGroups().remove(group));
        group.getInvitations().forEach(inv -> inv.getRequestedGroup().getInvitations().remove(inv));

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
