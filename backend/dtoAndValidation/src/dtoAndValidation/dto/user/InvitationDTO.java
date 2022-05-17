package dtoAndValidation.dto.user;

import java.util.Date;

public class InvitationDTO {
    public Long groupId;
    public String groupName;
    public Date date;

    public InvitationDTO() {}

    public InvitationDTO( Long groupId, String groupName, Date date) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.date = date;
    }

    public Date getCreatedOn() {
        return date;
    }
}
