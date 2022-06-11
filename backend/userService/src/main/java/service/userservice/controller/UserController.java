package service.userservice.controller;

import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.imp.KeycloakService;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/user")
    public PersonDTO getUser(HttpServletRequest request){
        return userManagementService.getUser(request);
    }


    @GetMapping("/user/usernames")
    public Collection<String> getGroups(){
        return userManagementService.getAllUsernames();
    }

}
