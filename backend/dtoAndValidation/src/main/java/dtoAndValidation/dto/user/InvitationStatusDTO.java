package dtoAndValidation.dto.user;


public class InvitationStatusDTO {
    public String status;
    public String groupName;
    public Long groupId;

    public InvitationStatusDTO() {}

    public InvitationStatusDTO(String status, String groupName, Long groupId){
        this.status = status;
        this.groupName = groupName;
        this.groupId = groupId;
    }
}
