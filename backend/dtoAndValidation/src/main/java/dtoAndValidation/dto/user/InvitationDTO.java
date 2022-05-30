package dtoAndValidation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import relationalDatabaseModule.model.InvitationStatus;

import java.util.Date;

@AllArgsConstructor
@Data
public class InvitationDTO {
    public Long groupId;
    public String groupName;
    public Date date = null;
    public String status = null;
    public InvitationDTO() {}

    public InvitationDTO(InvitationStatus invitationStatus, Long id, String groupName, Date date) {
        this.status = invitationStatus.toString();
        this.groupId = id;
        this.groupName = groupName;
        this.date = date;
    }
}
