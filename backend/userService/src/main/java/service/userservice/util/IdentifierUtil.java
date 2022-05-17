package service.userservice.util;


import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Person;
import service.userservice.service.IDatabaseService;

import java.util.UUID;

public class IdentifierUtil {

    public static Pair<Boolean, Boolean> resolveIdentifyingGroup(UUID personId, Long groupId,  IDatabaseService databaseService){

        Person person = databaseService.getPersonById(personId);
        Group group = databaseService.getGroupById(groupId);

        Boolean isInValid = person == null && group == null;
        Boolean isUserContent = person != null;

        return new Pair<>(isInValid, isUserContent);

    }


}
