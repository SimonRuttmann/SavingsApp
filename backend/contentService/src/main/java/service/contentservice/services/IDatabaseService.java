package service.contentservice.services;

import org.springframework.stereotype.Component;
import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Invitation;
import service.contentservice.persistence.relational.entity.Person;

import java.util.Collection;

/**
 * This class defines database calls to the tables Person and Groups
 */
@Component
public interface IDatabaseService {

     Collection<Group> getGroupsOfPersonId(Long id);

     void addPersonToGroup(Person person, Group group);

     void addInvitation(Person person, Group group);

     void declineInvitation(Invitation invitation);

     void acceptInvitation(Invitation invitation);

     void removePersonFromGroup(Person person, Group group);

     Person savePerson(Person person);

     void removePerson(Person person);

     Collection<Person> getPersonsOfGroupId(Long id);

     Group saveGroup(Group group);

     void removeGroup(Group group);

     Invitation saveInvitation(Invitation invitation);
}
