package service.contentservice.persistence.relational.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvitationCompoundId implements Serializable {

    private Long personCompoundId;

    private Long groupCompoundId;


    public InvitationCompoundId() {}

    public InvitationCompoundId(Long userCompoundId, Long groupCompoundId) {
        this.personCompoundId = userCompoundId;
        this.groupCompoundId = groupCompoundId;
    }


    public Long getPersonCompoundId() {return personCompoundId;}
    public void setPersonCompoundId(Long userId) {this.personCompoundId = userId;}

    public Long getGroupCompoundId() {return groupCompoundId;}
    public void setGroupCompoundId(Long groupId) {this.groupCompoundId = groupId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvitationCompoundId)) return false;
        InvitationCompoundId that = (InvitationCompoundId) o;
        return Objects.equals(personCompoundId, that.personCompoundId) && Objects.equals(groupCompoundId, that.groupCompoundId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCompoundId, groupCompoundId);
    }
}
