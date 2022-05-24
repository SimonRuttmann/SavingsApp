package dtoAndValidation.validation;
import dtoAndValidation.dto.user.RegisterGroupDTO;

public class RegisterGroupValidator implements IValidator<RegisterGroupDTO>{

    @Override
    public boolean validate(RegisterGroupDTO toValidate, boolean withId) {
        if(toValidate == null) return false;

        if(toValidate.groupName == null) return false;
        return !toValidate.groupName.isBlank();
    }
}


