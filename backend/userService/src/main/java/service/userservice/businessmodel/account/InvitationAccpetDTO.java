package service.userservice.businessmodel.account;

import service.userservice.persistence.entity.userdata.InvitationStatus;

public class InvitationAccpetDTO {
    public InvitationStatus status;

    public InvitationAccpetDTO() {}

    public InvitationAccpetDTO(InvitationStatus status){
        this.status = status;
    }
}
