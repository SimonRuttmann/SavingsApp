package service.contentservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.InvitationStatus;
import service.contentservice.persistence.relational.entity.Person;
import service.contentservice.persistence.relational.repository.GroupRepository;
import service.contentservice.persistence.relational.repository.PersonRepository;
import service.contentservice.services.IDatabaseService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DatabaseServiceTests {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    public IDatabaseService databaseService;



    @Test
    public List<Person> addPersons(){

        personRepository.deleteAll();
        List<Person> persons = new ArrayList<>();
        persons.add(databaseService.savePerson(new Person("aMan", "aMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("bMan", "bMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("cMan", "cMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("dMan", "dan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("eMan", "eMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("fMan", "fMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("gMan", "gMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("hMan", "hMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("iMan", "iMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("jMan", "jMan@gmail.com")));
        persons.add(databaseService.savePerson(new Person("kMan", "kMan@gmail.com")));
        List<Person> queriedPersons =  personRepository.findAll();

        //noinspection ConstantConditions
        Assertions.assertTrue(queriedPersons.size() == persons.size() && queriedPersons.size() == 11);

        return persons;
    }

    @Test
    public List<Group> addGroups(){

        groupRepository.deleteAll();
        List<Group> groups = new ArrayList<>();
        groups.add(databaseService.saveGroup(new Group("aGroup")));
        groups.add(databaseService.saveGroup(new Group("bGroup")));
        groups.add(databaseService.saveGroup(new Group("cGroup")));
        groups.add(databaseService.saveGroup(new Group("dGroup")));
        groups.add(databaseService.saveGroup(new Group("eGroup")));
        groups.add(databaseService.saveGroup(new Group("fGroup")));
        groups.add(databaseService.saveGroup(new Group("gGroup")));
        groups.add(databaseService.saveGroup(new Group("hGroup")));
        groups.add(databaseService.saveGroup(new Group("iGroup")));
        groups.add(databaseService.saveGroup(new Group("jGroup")));
        groups.add(databaseService.saveGroup(new Group("kGroup")));
        List<Group> queriedGroups = groupRepository.findAll();

        //noinspection ConstantConditions
        Assertions.assertTrue(queriedGroups.size() == groups.size() && queriedGroups.size() == 11);

        return groups;
    }


    @Test
    public void addMemberAssociations(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        // Add is_member relations

        var result0 = databaseService.addPersonToGroup(person0.getId(), group0.getId());
        var result1 = databaseService.addPersonToGroup(person1.getId(), group0.getId());

        // Check detached

        var id =  result0.getFirst().getId();
        var oldUsername = result0.getFirst().getUsername();
        result0.getFirst().setUsername("just another name");
        Assertions.assertEquals(databaseService.getPersonById(id).getUsername(), oldUsername);
        Assertions.assertNotEquals(databaseService.getPersonById(id).getUsername(), result0.getFirst().getUsername());

        // Check associations added in queried objects

        Assertions.assertEquals(1, result0.getFirst().getGroups().size());
        Assertions.assertEquals(1, result1.getFirst().getGroups().size());
        Assertions.assertEquals(2, result1.getSecond().getMembers().size());

        // Check result objects are linked

        Assertions.assertTrue(result0.getSecond().getMembers().contains(result0.getFirst()));
        Assertions.assertTrue(result1.getSecond().getMembers().contains(result1.getFirst()));

        // Db Check
        Assertions.assertEquals(1,
                databaseService.getPersonById(person0.getId()).
                getGroups().size());

        Assertions.assertEquals(1,
                databaseService.getPersonById(person1.getId()).
                getGroups().size());

        Assertions.assertEquals(2,
                databaseService.getGroupById(group0.getId()).
                getMembers().size());

    }

    @Test
    public void removeMemberAssociations(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        // Add is_member relations

        var detachedResultP0G0 = databaseService.addPersonToGroup(person0.getId(), group0.getId());
        var detachedResultP1G0 = databaseService.addPersonToGroup(person1.getId(), group0.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(1, detachedResultP0G0.getFirst().getGroups().size());
        Assertions.assertEquals(1, detachedResultP1G0.getFirst().getGroups().size());
        Assertions.assertEquals(2, detachedResultP1G0.getSecond().getMembers().size());

        //Remove is_member relations

        databaseService.removePersonFromGroup(person0.getId(), group0.getId());
        databaseService.removePersonFromGroup(person1.getId(), group0.getId());

        //Evaluate remove of is_member relations

        Assertions.assertEquals(0, databaseService.getPersonsOfGroupId(group0.getId()).size());
        Assertions.assertEquals(0, databaseService.getGroupsOfPersonId(person0.getId()).size());
        Assertions.assertEquals(0, databaseService.getGroupsOfPersonId(person1.getId()).size());

    }

    @Test
    public void addInvitationAssociations(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        // Add invitation relations

        var detachedInvitationP0G0 = databaseService.addInvitation(1L, 1L);
        var detachedInvitationP1G0 = databaseService.addInvitation(2L, 1L);
        databaseService.addInvitation(3L, 2L);
        databaseService.addInvitation(3L, 5L);
        databaseService.addInvitation(4L, 4L);

        // Check associations added in queried objects

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());

        Assertions.assertEquals(1, databaseService.getPersonById(person0.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(2, databaseService.getGroupById(group0.getId()).getInvitations().size());

    }


    @Test
    public void editInvitationAssociations(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        // Add invitation relations

        var detachedInvitationP0G0 = databaseService.addInvitation(person0.getId(), group0.getId());
        var detachedInvitationP1G0 = databaseService.addInvitation(person1.getId(), group0.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());

        Assertions.assertEquals(1, databaseService.getPersonById(person0.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(2, databaseService.getGroupById(group0.getId()).getInvitations().size());

        var detachedAcceptedInvitationP0G0 = databaseService.acceptInvitation(detachedInvitationP0G0.getId());

        Assertions.assertEquals(InvitationStatus.ACCEPTED, detachedAcceptedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedAcceptedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedAcceptedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());


        var detachedDeclinedInvitationP0G0 = databaseService.declineInvitation(detachedInvitationP0G0.getId());

        Assertions.assertEquals(InvitationStatus.DECLINED, detachedDeclinedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedDeclinedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedDeclinedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());



    }

    @Test
    public void deletePerson(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        // Add is_member relations

        var detachedResultP0G0 = databaseService.addPersonToGroup(person0.getId(), group0.getId());
        var detachedResultP1G0 = databaseService.addPersonToGroup(person1.getId(), group0.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(1, detachedResultP0G0.getFirst().getGroups().size());
        Assertions.assertEquals(1, detachedResultP1G0.getFirst().getGroups().size());
        Assertions.assertEquals(2, detachedResultP1G0.getSecond().getMembers().size());

        // Add invitation relations

        var detachedInvitationP0G0 = databaseService.addInvitation(person0.getId(), group0.getId());
        var detachedInvitationP1G0 = databaseService.addInvitation(person1.getId(), group0.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());

        Assertions.assertEquals(1, databaseService.getPersonById(person0.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(2, databaseService.getGroupById(group0.getId()).getInvitations().size());

        // Remove person 0, connected to group 0

        databaseService.removePerson(person0.getId());

        // Check person0 not existing, person1 preserve member, group0 one member

        Assertions.assertNull(databaseService.getPersonById(person0.getId()));
        Assertions.assertEquals(1, databaseService.getPersonsOfGroupId(group0.getId()).size());
        Assertions.assertEquals(1, databaseService.getGroupsOfPersonId(person1.getId()).size());

        // Check person0 not existing, person1 preserve invitations, group0 one invitation

        Assertions.assertEquals(1, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getGroupById(group0.getId()).getInvitations().size());

    }

    @Test
    public void deleteGroup(){

        // Create persons and groups

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);
        var person2 = persons.get(2);

        var group0 = groups.get(0);
        var group1 = groups.get(1);

        // Add is_member relations

        var detachedResultP0G0 = databaseService.addPersonToGroup(person0.getId(), group0.getId());
        var detachedResultP1G0 = databaseService.addPersonToGroup(person1.getId(), group0.getId());
        databaseService.addPersonToGroup(person2.getId(), group1.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(1, detachedResultP0G0.getFirst().getGroups().size());
        Assertions.assertEquals(1, detachedResultP1G0.getFirst().getGroups().size());
        Assertions.assertEquals(2, detachedResultP1G0.getSecond().getMembers().size());

        // Add invitation relations

        var detachedInvitationP0G0 = databaseService.addInvitation(person0.getId(), group0.getId());
        var detachedInvitationP1G0 = databaseService.addInvitation(person1.getId(), group0.getId());
        databaseService.addInvitation(person2.getId(), group1.getId());

        // Check associations added in queried objects

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP0G0.getInvitationStatus());
        Assertions.assertEquals(person0.getId(), detachedInvitationP0G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP0G0.getRequestedGroup().getId());

        Assertions.assertEquals(InvitationStatus.OPEN, detachedInvitationP1G0.getInvitationStatus());
        Assertions.assertEquals(person1.getId(), detachedInvitationP1G0.getInvitedPerson().getId());
        Assertions.assertEquals(group0.getId(), detachedInvitationP1G0.getRequestedGroup().getId());

        Assertions.assertEquals(1, databaseService.getPersonById(person0.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(2, databaseService.getGroupById(group0.getId()).getInvitations().size());

        // Remove group0, connected to p1 and p2

        databaseService.removeGroup(group0.getId());

        // Check group0 removed, p0 and p1 no members

        Assertions.assertNull(databaseService.getGroupById(group0.getId()));
        Assertions.assertEquals(0, databaseService.getGroupsOfPersonId(person0.getId()).size());
        Assertions.assertEquals(0, databaseService.getGroupsOfPersonId(person1.getId()).size());
        Assertions.assertEquals(1, databaseService.getGroupById(group1.getId()).getMembers().size());

        // Check person0 not existing, p0 and p1 no members

        Assertions.assertEquals(0, databaseService.getPersonById(person0.getId()).getInvitations().size());
        Assertions.assertEquals(0, databaseService.getPersonById(person1.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getPersonById(person2.getId()).getInvitations().size());
        Assertions.assertEquals(1, databaseService.getGroupById(group1.getId()).getInvitations().size());

    }



}
