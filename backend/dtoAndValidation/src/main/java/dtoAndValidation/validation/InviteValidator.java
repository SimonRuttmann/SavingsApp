package dtoAndValidation.validation;

import dtoAndValidation.dto.user.InviteDTO;

public class InviteValidator implements IValidator<InviteDTO> {
    @Override
    public boolean validate(InviteDTO toValidate, boolean withId) {

        if(toValidate == null) return false;
        if(toValidate.username.isBlank()) return false;
        if(toValidate.username.isEmpty()) return false;
        if(toValidate.groupId == null) return false;
        return toValidate.groupId > 0;
    }
}
