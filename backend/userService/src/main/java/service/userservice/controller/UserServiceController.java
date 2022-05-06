package service.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.contentservice.persistence.IGroupDocumentService;
import service.contentservice.services.IDatabaseService;
import service.userservice.service.KeycloakService;

@RestController
@RequestMapping("/userservice")
public class UserServiceController {

    private final KeycloakService keycloakService;


    @Autowired
    public UserServiceController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping("/keycloak/{userId}")
    public String getKeycloakUser(@PathVariable String userId){
        return keycloakService.getPersonById(userId);
    }


    @PostMapping("/request/invite")
    public String invite(){
        return "Invitation successful";
    }

    @GetMapping("/request/receive")
    public String getInvitations(){
        return "here all of your invitations";
    }

    @GetMapping("/users/{groupId}")
    public String getUsers(){
        return "Array of userId's";
    }

    @PutMapping("/request/accept")
    public String acceptInvitation(){
        return "you accepted invitation";
    }

    @DeleteMapping("/leave/{groupId}")
    public String leaveGroup( @PathVariable Integer groupId){
        return "you left Group with Id "+groupId;
    }
}
