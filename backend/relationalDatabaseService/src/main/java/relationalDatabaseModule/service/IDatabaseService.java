package relationalDatabaseModule.service;

import org.springframework.stereotype.Component;
import relationalDatabaseModule.model.*;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

     KPerson getPersonById(UUID id);

     Group getGroupById(Long id);
     Collection<Group> getGroupsOfPersonId(UUID id);

     Pair<KPerson, Group> addPersonToGroup(UUID personId, Long groupId);

     Invitation addInvitation(String username, Long groupId, UUID inviterPerson);

     Invitation declineInvitation(UUID userId, Long groupId);

     Invitation acceptInvitation(UUID userId, Long groupId);

     List<Invitation> getAllInvitations(UUID userId);

     Pair<KPerson, Group> removePersonFromGroup(UUID personId, Long groupId);


     Collection<KPerson> getPersonsOfGroupId(Long id);
     KPerson getPersonByUsername(String username);


     Group saveGroup(Group group);

     void removeGroup(Long id);

     Invitation saveInvitation(Invitation invitation);
     Boolean checkIfPersonIsMember(UUID personId, Long groupId);
}
