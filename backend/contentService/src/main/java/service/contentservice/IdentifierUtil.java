package service.contentservice;


import dtoAndValidation.util.Pair;
import relationalDatabaseService.model.Group;
import relationalDatabaseService.model.Person;
import relationalDatabaseService.service.IDatabaseService;

public class IdentifierUtil {

    public static Pair<Boolean, Boolean> resolveIdentifyingGroup(Long id, IDatabaseService databaseService){

        Person person = databaseService.getPersonById(id);
        Group group = databaseService.getGroupById(id);

        Boolean isInValid = person == null && group == null;
        Boolean isUserContent = person != null;

        return new Pair<>(isInValid, isUserContent);

    }


}
