package service.contentservice.validation;

import service.contentservice.businessmodel.account.PersonDTO;
import service.contentservice.persistence.relational.entity.Person;

public class PersonValidator implements IValidator<PersonDTO>{
    @Override
    public boolean validate(PersonDTO toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getEmail() == null ) return false;
        if(toValidate.getEmail().isBlank()) return false;

        if(toValidate.getUsername() == null) return false;
        return !toValidate.getUsername().isBlank();
    }
}