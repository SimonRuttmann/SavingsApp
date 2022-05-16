package service.userservice.persistence.entity.keycloak;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public String getId() { return id;}

    public KPerson() {}

    public KPerson(String id, String email ) {
        this.id = id;
        this.email = email;

    }



}
