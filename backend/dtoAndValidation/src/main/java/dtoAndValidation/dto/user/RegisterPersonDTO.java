package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;

import java.util.UUID;

public class RegisterPersonDTO implements IValidatable {

    public UUID id;
    public String email;
    public String username;

    public RegisterPersonDTO() {}

    public RegisterPersonDTO(UUID id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
