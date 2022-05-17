package service.userservice.businessmodel.account;

public class InvitationDTO {
    public Long groupId;
    public String groupName;

    public InvitationDTO() {}

    public InvitationDTO( Long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
