package service.userservice.controller;

import main.java.dtoAndValidation.dto.user.GroupDTO;
import main.java.dtoAndValidation.dto.user.PersonDTO;
import main.java.dtoAndValidation.dto.user.RegisterPersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.service.imp.KeycloakService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/userservice")
public class UserController {

    private final KeycloakService keycloakService;
    private final IUserManagementService userManagementService;


    @Autowired
    public UserController(KeycloakService keycloakService, IUserManagementService userManagementService) {
        this.keycloakService = keycloakService;
        this.userManagementService = userManagementService;
    }

    @PostMapping("/user/register")
    public PersonDTO registerNewUser(@RequestBody RegisterPersonDTO registerDto) {
        return userManagementService.register(registerDto);
    }
    @GetMapping("/user/{userId}")
    public PersonDTO getUser(@PathVariable UUID userId){
        return userManagementService.getUser(userId);
    }

    @GetMapping("/user/{userId}/groups")
    public Collection<GroupDTO> getGroups(@PathVariable UUID userId){
        return userManagementService.getAllGroupsOfPerson(userId);
    }

    @DeleteMapping("/user/{userId}")
    public PersonDTO deleteUser(@PathVariable String userId) {
        return userManagementService.deleteUser(UUID.fromString(userId));
    }
}
