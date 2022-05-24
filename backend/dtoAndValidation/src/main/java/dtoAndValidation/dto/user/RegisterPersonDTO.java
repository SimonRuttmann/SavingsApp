package dtoAndValidation.dto.user;

import java.util.UUID;

public class RegisterPersonDTO {

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
