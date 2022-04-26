package service.contentservice.validation;

import service.contentservice.persistence.relational.entity.Group;

import java.util.List;

public class ValidateGroup implements IValidator<Group>{
    @Override
    public List<String> validate(Group toValidate) {
        return null;
    }
}
