package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;

import java.util.UUID;

public class InviteDTO implements IValidatable {

    public String username;
    public Long groupId;
    //public List<Invitation> inviteList;

    public InviteDTO(String username, Long groupId) {
        this.username = username;
        this.groupId = groupId;
        //this.inviteList = inviteList;
    }

    public InviteDTO(){}
}
