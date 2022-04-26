package service.contentservice.persistence.relational.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class InvitationCompoundId implements Serializable {

    private Long personCompoundId;

    private Long groupCompoundId;

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    public InvitationCompoundId() {}

    public InvitationCompoundId(Long userCompoundId, Long groupCompoundId) {
        this.personCompoundId = userCompoundId;
        this.groupCompoundId = groupCompoundId;
    }


    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

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
