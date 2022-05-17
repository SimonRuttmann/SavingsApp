package service.userservice.businessmodel.account;



import service.userservice.validation.IValidatable;

import java.util.Objects;
import java.util.UUID;

public class PersonDTO implements IValidatable {

    private UUID id;

    private String username;

    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PersonDTO() {
    }

    public PersonDTO(UUID id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
