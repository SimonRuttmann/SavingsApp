package relationalDatabaseModule.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name="user_entity",
        catalog = "keycloak"
)
public class KPerson {

    @Id
    private String id;
    private String email;
    private String email_constraint;
    private Boolean email_verified;
    private Boolean enabled;
    private String federation_link;
    private String first_name;
    private String last_name;
    private String realm_id;
    private String username;
    private Long created_timestamp;
    private String service_account_client_link;
    private Integer not_before;


    public KPerson() {}

    public KPerson(String id, String email ) {
        this.id = id;
        this.email = email;

    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_member",
            joinColumns = {@JoinColumn(name="Person_Id", referencedColumnName = "Id")},
            inverseJoinColumns = {@JoinColumn(name="Group_Id", referencedColumnName = "Id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"Person_Id","Group_Id"})
    )
    Set<Group> groups = new HashSet<>();


    public void addGroup(Group group){
        if(group == null) return;
        if(this.groups.contains(group)) return;

        this.groups.add(group);
        group.addMember(this);
    }

    public void removeGroup(Group group){
        if(group == null) return;
        if(!this.groups.contains(group)) return;

        this.groups.remove(group);
    }

    /**
     * Many to many mapping by using the InvitationPersistence table to add additional attributes
     * Mapped by InvitationPersistence.invitedPersonId
     */
    @OneToMany (mappedBy="invitedPerson", fetch = FetchType.EAGER)
    Set<Invitation> invitations = new HashSet<>();

    public String getId() { return id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Set<Group> getGroups() {return groups;}
    public void setGroups(Set<Group> groups) {this.groups = groups;}

    public Set<Invitation> getInvitations() {return invitations;}
    public void setInvitations(Set<Invitation> invitations) {this.invitations = invitations;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KPerson)) return false;
        KPerson person = (KPerson) o;
        return Objects.equals(id, person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", groups=" + groups +
                ", invitations=" + invitations +
                '}';
    }
}
