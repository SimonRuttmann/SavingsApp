package documentDatabaseService.documentbased.model;

import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Person;
import service.contentservice.services.IDatabaseService;

public class IdentifierUtil {

    public static Pair<Boolean, Boolean> resolveIdentifyingGroup(Long id, IDatabaseService databaseService){

        Person person = databaseService.getPersonById(id);
        Group group = databaseService.getGroupById(id);

        Boolean isInValid = person == null && group == null;
        Boolean isUserContent = person != null;

        return new Pair<>(isInValid, isUserContent);

    }


}
