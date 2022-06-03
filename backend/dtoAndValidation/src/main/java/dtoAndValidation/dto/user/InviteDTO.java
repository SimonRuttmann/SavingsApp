package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;

public class InviteDTO implements IValidatable {

    public String username;
    public Long groupId;

    public InviteDTO(String username, Long groupId) {
        this.username = username;
        this.groupId = groupId;

    }

    public InviteDTO(){}
}
