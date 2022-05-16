package service.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.businessmodel.account.GroupDTO;
import service.userservice.businessmodel.account.PersonDTO;
import service.userservice.businessmodel.account.RegisterGroupDTO;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Person;
import service.userservice.service.IUserManagementService;
import service.userservice.service.imp.KeycloakService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/userservice")
public class GroupController {

    private final KeycloakService keycloakService;
    private final IUserManagementService userManagementService;


    @Autowired
    public GroupController(KeycloakService keycloakService, IUserManagementService userManagementService) {
        this.keycloakService = keycloakService;
        this.userManagementService = userManagementService;
    }

    @PostMapping("/group/register")
    public GroupDTO registerNewGroup(HttpServletRequest request, @RequestBody RegisterGroupDTO registerDto) {
        return userManagementService.registerGroup( request, registerDto);
    }

    @GetMapping("/group/{groupId}/")
    public GroupDTO getGroup(@PathVariable Long groupId){
        return userManagementService.getGroup(groupId);
    }

    @GetMapping("/group/{groupId}/members")
    public Collection<PersonDTO> getUsers(@PathVariable Long groupId){
        return userManagementService.getAllUserfromGroup(groupId);
    }

    @DeleteMapping("/group/leave/{groupId}")
    public GroupDTO leaveGroup(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.leaveGroup(request, groupId);
    }

    @DeleteMapping("/group/{groupId}")
    public GroupDTO deleteGroup( @PathVariable Long groupId){
        return userManagementService.deleteGroup( groupId);
    }
}
