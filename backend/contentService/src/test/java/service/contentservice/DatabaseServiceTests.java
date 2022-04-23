package service.contentservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service.contentservice.persistence.relational.entity.Group;
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
    public void setUp(){


    }


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

        return queriedPersons;
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

        return queriedGroups;
    }


    @Test
    public void addMemberAssociations(){

        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        databaseService.addPersonToGroup(person0, group0);
        databaseService.addPersonToGroup(person1, group0);


        Assertions.assertEquals(1, person0.getGroups().size());
        Assertions.assertEquals(1, person1.getGroups().size());
        Assertions.assertEquals(2, group0.getMembers().size());

        Assertions.assertTrue(group0.getMembers().contains(person0));
        Assertions.assertTrue(group0.getMembers().contains(person1));

        Assertions.assertTrue(person0.getGroups().contains(group0));
        Assertions.assertTrue(person1.getGroups().contains(group0));

    }

    @Test
    public void removeMemberAssociations(){
        var persons = addPersons();
        var groups = addGroups();

        var person0 = persons.get(0);
        var person1 = persons.get(1);

        var group0 = groups.get(0);

        databaseService.addPersonToGroup(person0, group0);
        databaseService.addPersonToGroup(person1, group0);


        Assertions.assertEquals(1, person0.getGroups().size());
        Assertions.assertEquals(1, person1.getGroups().size());
        Assertions.assertEquals(2, group0.getMembers().size());
        Assertions.assertTrue(group0.getMembers().contains(person0));
        Assertions.assertTrue(group0.getMembers().contains(person1));
        Assertions.assertTrue(person0.getGroups().contains(group0));
        Assertions.assertTrue(person1.getGroups().contains(group0));


        databaseService.removePersonFromGroup(person0, group0);
        databaseService.removePersonFromGroup(person1, group0);


        Assertions.assertEquals(0, databaseService.getPersonsOfGroupId(group0.getId()).size());
        Assertions.assertEquals(0, databaseService.getGroupsOfPersonId(group0.getId()).size());

        System.out.println("hi");
    }

    @Test
    public void addInvitationAssociations(){

    }


    @Test
    public void editInvitationAssociations(){

    }

    @Test
    public void removeInvitationAssociations(){

    }

    @Test
    public void deletePerson(){

    }

    @Test
    public void deleteGroup(){

    }



}
