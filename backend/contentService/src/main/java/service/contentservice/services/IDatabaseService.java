package service.contentservice.services;

import org.springframework.stereotype.Component;
import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Invitation;
import service.contentservice.persistence.relational.entity.InvitationCompoundId;
import service.contentservice.persistence.relational.entity.Person;
import service.contentservice.util.Pair;

import java.util.Collection;

/**
 * This class defines database calls to the tables Person and Groups
 *
 * We marth this service as component, so that it can later be used with "autowire" annotation
 * Also the implementations need to be marked as component
 * When we have multiple implementations we can mark them with @ Primary (the one we want to have)
 * or we can use @Qualifier
 *
 *  Components are (spring) beans
 * @ Bean
 * @ Component Preferable for component scanning and automatic wiring.
 *
 * When should you use @Bean?
 *
 * Sometimes automatic configuration is not an option.
 * When? Let's imagine that you want to wire components from 3rd-party
 * libraries (you don't have the source code so you can't annotate its classes
 * with @Component), so automatic configuration is not possible.
 *
 * The @Bean annotation returns an object that spring should register
 * as bean in application context. The body of the method bears the logic
 * responsible for creating the instance.
 *
 * @ Autowired
 * IService myService
 */
@Component
public interface IDatabaseService {

     Person getPersonById(Long id);

     Group getGroupById(Long id);
     Collection<Group> getGroupsOfPersonId(Long id);

     Pair<Person, Group> addPersonToGroup(Long personId, Long groupId);

     Invitation addInvitation(Long personId, Long groupId);

     Invitation declineInvitation(InvitationCompoundId invitationCompoundId);

     Invitation acceptInvitation(InvitationCompoundId invitationCompoundId);

     Pair<Person, Group> removePersonFromGroup(Long personId, Long groupId);

     Person savePerson(Person person);

     void removePerson(Long id);

     Collection<Person> getPersonsOfGroupId(Long id);

     Group saveGroup(Group group);

     void removeGroup(Long id);

     Invitation saveInvitation(Invitation invitation);
}
