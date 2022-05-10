package service.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.businessmodel.account.*;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Invitation;
import service.userservice.persistence.entity.userdata.Person;
import service.userservice.service.IUserManagementService;
import service.userservice.service.KeycloakService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
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

    /**
     *ERROR because of saving in DB
     */
    @PostMapping("/invitation/invite/")
    public Invitation invite(@RequestBody InviteDTO newInvitation){
        return userManagementService.invite(newInvitation);
    }

    /**
     *ERROR because of saving in DB
     */
    @GetMapping("/invitation/receive")
    public List<Invitation> getInvitations(HttpServletRequest request){
        return userManagementService.getInvitations(request);
    }

    @GetMapping("/group/{groupId}")
    public Collection<Person> getUsers(@PathVariable Long groupId){
        return userManagementService.getAllUserfromGroup(groupId);
    }

    @GetMapping("/user/{userId}")
    public Collection<Group> getGroups(@PathVariable UUID userId){
        return userManagementService.getAllGroupsOfPerson(userId);
    }

    @PutMapping("/invitation/accept/{groupId}")
    public Invitation acceptInvitation(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.acceptInvitation(request, groupId);
    }

    @PutMapping("/invitation/decline/{groupId}")
    public Invitation declineInvitation(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.declineInvitation(request, groupId);
    }

    @DeleteMapping("/group/leave/{groupId}")
    public GroupDTO leaveGroup(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.leaveGroup(request, groupId);
    }

    @PostMapping("/user/register")
    public Person registerNewUser(@RequestBody RegisterPersonDTO registerDto) {
        return userManagementService.register(registerDto);
    }

    @PostMapping("/group/register")
    public Group registerNewGroup(HttpServletRequest request, @RequestBody RegisterGroupDTO registerDto) {
        return userManagementService.registerGroup( request, registerDto);
    }
}

