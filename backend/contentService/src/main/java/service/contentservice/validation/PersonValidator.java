package service.contentservice.validation;

import service.contentservice.persistence.relational.entity.Person;

import java.util.List;

public class PersonValidator implements IValidator<Person>{
    @Override
    public boolean validate(Person toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getEmail() == null ) return false;
        if(toValidate.getEmail().isBlank()) return false;

        if(toValidate.getUsername() == null) return false;
        return !toValidate.getUsername().isBlank();
    }
}
