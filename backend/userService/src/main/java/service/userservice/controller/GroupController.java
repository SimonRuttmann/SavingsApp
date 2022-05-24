package service.userservice.controller;

import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.PersonDTO;
import dtoAndValidation.dto.user.RegisterGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
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
    public GroupDTO deleteGroup(@PathVariable Long groupId){
        return userManagementService.deleteGroup( groupId);
    }
}
