package dtoAndValidation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {

    private Long groupId;

    private String groupName;

    private Date date = null;

    private String status = null;

    public InvitationDTO(String invitationStatus, Long id, String groupName, Date date) {
        this.status = invitationStatus;
        this.groupId = id;
        this.groupName = groupName;
        this.date = date;
    }
}
