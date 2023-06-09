package relationalDatabaseModule.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name="Group",
        catalog = "userdata",
        schema = "public"
)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name="Id",
            unique = true,
            nullable = false,
            insertable = true,
            updatable = false,
            length = 255
    )
    private Long id;

    @Column(
            name="Groupname",
            unique = false,
            nullable = false,
            insertable = true,
            updatable = true,
            length=512)
    private String groupName;

    @Column(
            name="PersonGroupBool",
            unique = false,
            nullable = false,
            insertable = true,
            updatable = true,
            length=255)
    private Boolean personGroup;


    public Group() {}

    public Group(String groupName, Boolean personGroup) {
        this.groupName = groupName;
        this.personGroup = personGroup;
    }


    /**
     * Many to many mapping of groups and users.
     * The UserPersistence class is the owning side with the
     * UserPersistence.groups attribute defining the join table
     */
    @ManyToMany (mappedBy = "groups", fetch = FetchType.EAGER)
    Set<KPerson> members = new HashSet<>();

    public void addMember(KPerson person){
        if(person == null) return;
        if(this.members.contains(person)) return;

        this.members.add(person);
        person.addGroup(this);
    }

    public void removeMember(KPerson person){
        if(person == null) return;
        if(!this.members.contains(person)) return;

        this.members.remove(person);
    }


    /**
     * Many to many mapping by using the InvitationPersistence table to add additional attributes
     * Mapped by InvitationPersistence.requestedGroup
     */
    @OneToMany (mappedBy="requestedGroup", fetch = FetchType.EAGER)
    Set<Invitation> invitations = new HashSet<>();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Boolean getPersonGroupBool() {return personGroup;}
    public void setPersonGroupBool(Boolean personGroup) {this.personGroup = personGroup;}

    public String getGroupName() {return groupName;}
    public void setGroupName(String groupName) {this.groupName = groupName;}

    public Set<KPerson> getMembers() {return members;}
    public void setMembers(Set<KPerson> members) {this.members = members;}

    public Set<Invitation> getInvitations() {return invitations;}
    public void setInvitations(Set<Invitation> invitations) {this.invitations = invitations;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\''+
                '}';
    }
}
