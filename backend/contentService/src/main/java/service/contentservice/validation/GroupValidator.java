package service.contentservice.validation;

import service.contentservice.persistence.relational.entity.Group;

import java.util.List;

public class GroupValidator implements IValidator<Group>{
    @Override
    public boolean validate(Group toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getGroupName() == null) return false;
        return !toValidate.getGroupName().isBlank();
    }
}
