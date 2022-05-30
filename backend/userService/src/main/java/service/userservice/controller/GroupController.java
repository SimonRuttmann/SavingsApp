package service.userservice.controller;

import dtoAndValidation.dto.user.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.imp.KeycloakService;

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
    public GroupDTO registerNewGroup(HttpServletRequest request, @RequestBody GroupDTO registerDto) {
        return userManagementService.registerGroup( request, registerDto);
    }

    @GetMapping("/group/")
    public Collection<GroupDTO> getGroups(HttpServletRequest request){
        return userManagementService.getAllGroupsOfPerson(request);
    }


    @DeleteMapping("/group/leave/{groupId}")
    public GroupDTO leaveGroup(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.leaveGroup(request, groupId);
    }

    @DeleteMapping("/group/{groupId}")
    public GroupDTO deleteGroup(@PathVariable Long groupId){
        return userManagementService.deleteGroup( groupId);
    }
}
