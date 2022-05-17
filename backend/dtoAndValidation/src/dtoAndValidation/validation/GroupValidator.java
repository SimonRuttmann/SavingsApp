package dtoAndValidation.validation;


import dtoAndValidation.dto.user.GroupDTO;

public class GroupValidator implements IValidator<GroupDTO>{
    @Override
    public boolean validate(GroupDTO toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getGroupName() == null) return false;
        return !toValidate.getGroupName().isBlank();
    }
}
