package service.userservice.controller;

import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.imp.KeycloakService;

import java.util.Collection;
import java.util.List;
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

//    @PostMapping("/user/register")
//    public PersonDTO registerNewUser(@RequestBody PersonDTO registerDto) {
//        return userManagementService.register(registerDto);
//    }
    @GetMapping("/user/{userId}")
    public PersonDTO getUser(@PathVariable UUID userId){
        return userManagementService.getUser(userId);
    }

    @GetMapping("/user/{userId}/groups")
    public Collection<GroupDTO> getGroups(@PathVariable UUID userId){
        return userManagementService.getAllGroupsOfPerson(userId);
    }

    @GetMapping("/user/usernames")
    public Collection<String> getGroups(){
        return userManagementService.getAllUsernames();
    }
//
//    @DeleteMapping("/user/{userId}")
//    public PersonDTO deleteUser(@PathVariable String userId) {
//        return userManagementService.deleteUser(UUID.fromString(userId));
//    }
}
