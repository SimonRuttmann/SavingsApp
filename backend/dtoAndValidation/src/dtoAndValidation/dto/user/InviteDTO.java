package dtoAndValidation.dto.user;

import service.userservice.persistence.entity.userdata.Invitation;
import service.userservice.validation.IValidatable;

import java.util.UUID;

public class InviteDTO implements IValidatable {

    public UUID userId;
    public Long groupId;
    //public List<Invitation> inviteList;

    public InviteDTO(UUID userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
        //this.inviteList = inviteList;
    }

    public InviteDTO(){}
}
