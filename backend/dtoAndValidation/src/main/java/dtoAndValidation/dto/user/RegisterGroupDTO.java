package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;

public class RegisterGroupDTO implements IValidatable {

    public String groupName;

    public RegisterGroupDTO() {}

    public RegisterGroupDTO(String groupName) {
        this.groupName = groupName;
    }
}
