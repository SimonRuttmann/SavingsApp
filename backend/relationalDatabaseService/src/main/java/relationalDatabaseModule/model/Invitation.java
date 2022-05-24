package relationalDatabaseModule.model;


import javax.persistence.*;

/**
 * Defines the table invitation_persistence,
 * realising the M:N mapping between group and person with additional attributes
 * Therefore the primary key is created due to member pk, group pk and a creation date
 * @see InvitationCompoundId
 */
@Entity
@Table(
        name="invitation_persistence",
        catalog = "userdata",
        schema = "public"
)
public class Invitation {

    /**
     * Represents the compound primary key, containing the
     * personCompoundId and groupCompoundId
     */
    @EmbeddedId
    private InvitationCompoundId id = new InvitationCompoundId();

    /**
     * The reference to the user object
     * The id of the user is mapped within the embedded id
     * InvitationCompoundId.userCompoundId
     */
    @ManyToOne
    @MapsId("personCompoundId")
    @JoinColumn(name="Id_Invited_Person")
    private Person invitedPerson;

    /**
     * The reference to the group object
     * The id of the group is mapped within the embedded id
     * InvitationCompoundId.groupCompoundId
     */
    @ManyToOne
    @MapsId("groupCompoundId")
    @JoinColumn(name="Id_Requested_Group")
    private Group requestedGroup;


    @Column(
            name="invitation_status",
            unique = false,
            nullable = false,
            insertable = true,
            updatable = true,
            length = 255
    )
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    public InvitationCompoundId getId() {
        return id;
    }

    public void setId(InvitationCompoundId id) {
        this.id = id;
    }

    public Person getInvitedPerson() {
        return invitedPerson;
    }

    public void setInvitedPerson(Person invitedPerson) {
        this.invitedPerson = invitedPerson;
    }

    public Group getRequestedGroup() {
        return requestedGroup;
    }

    public void setRequestedGroup(Group requestedGroup) {
        this.requestedGroup = requestedGroup;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

}
