package service.contentservice.validation;

import service.contentservice.persistence.relational.entity.Person;

import java.util.List;

public class ValidatePerson implements IValidator<Person>{
    @Override
    public List<String> validate(Person toValidate) {
        return null;
    }
}
