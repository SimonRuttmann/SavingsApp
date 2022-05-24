package dtoAndValidation.validation;

import dtoAndValidation.dto.user.RegisterPersonDTO;

public class RegisterPersonValidator implements IValidator<RegisterPersonDTO> {

    @Override
    public boolean validate(RegisterPersonDTO toValidate, boolean withId) {
        if(toValidate == null) return false;

        if(withId && toValidate.id == null) return false;
        if(!withId && toValidate.id != null) return false;

        if(toValidate.email == null || toValidate.email.isBlank() ) return false;
        return toValidate.username != null && !toValidate.username.isBlank();
    }
}