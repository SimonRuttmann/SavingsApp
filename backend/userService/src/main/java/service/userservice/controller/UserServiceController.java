package service.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.service.imp.KeycloakService;

import java.util.UUID;

@RestController
@RequestMapping("/userservice")
public class UserServiceController {

    private final KeycloakService keycloakService;
    private final IUserManagementService userManagementService;


    @Autowired
    public UserServiceController(KeycloakService keycloakService, IUserManagementService userManagementService) {
        this.keycloakService = keycloakService;
        this.userManagementService = userManagementService;
    }
    // test
    @GetMapping("/keycloak/{userId}")
    public String getKeycloakUser(@PathVariable String userId){
        return keycloakService.getPersonById(userId);
    }

    @GetMapping("check/user/{personId}/group/{groupId}")
    public Boolean checkIfPersonIsMember(@PathVariable UUID personId, @PathVariable Long groupId){
        return userManagementService.checkIfPersonIsMember(personId,groupId);
    }


}

