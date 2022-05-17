package dtoAndValidation.dto.user;

import service.userservice.persistence.entity.userdata.InvitationStatus;

public class InvitationStatusDTO {
    public InvitationStatus status;
    public String groupName;
    public Long groupId;

    public InvitationStatusDTO() {}

    public InvitationStatusDTO(InvitationStatus status, String groupName, Long groupId){
        this.status = status;
        this.groupName = groupName;
        this.groupId = groupId;
    }
}
