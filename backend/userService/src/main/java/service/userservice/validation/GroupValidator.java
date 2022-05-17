package service.userservice.validation;


import service.userservice.businessmodel.account.GroupDTO;

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
