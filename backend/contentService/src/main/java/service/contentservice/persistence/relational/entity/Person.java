package service.contentservice.persistence.relational.entity;

import service.contentservice.validation.IValidatable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name="Person",
        catalog = "userdata",
        schema = "public"
)
public class Person implements IValidatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            name="Username",
            unique = true,
            nullable = false,
            insertable = true,
            updatable = true,
            length=512)
    private String username;

    @Column(
            name="EMail",
            unique = true,
            nullable = false,
            insertable = true,
            updatable = true,
            length=512)
    private String email;


    public Person() {}

    public Person(String username, String email) {
        this.username = username;
        this.email = email;
    }


    /**
     * Many to many mapping between users and groups by using the is_member join table
     */
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

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id;}

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
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
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
