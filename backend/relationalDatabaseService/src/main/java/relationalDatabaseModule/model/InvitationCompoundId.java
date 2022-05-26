package relationalDatabaseModule.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class InvitationCompoundId implements Serializable {
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID personCompoundId;

    private Long groupCompoundId;

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    public InvitationCompoundId() {}

    public InvitationCompoundId(UUID userCompoundId, Long groupCompoundId) {
        this.personCompoundId = userCompoundId;
        this.groupCompoundId = groupCompoundId;
    }


    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public UUID getPersonCompoundId() {return personCompoundId;}
    public void setPersonCompoundId(UUID userId) {this.personCompoundId = userId;}

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
