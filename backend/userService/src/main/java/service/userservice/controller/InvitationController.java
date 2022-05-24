package service.userservice.controller;

import main.java.dtoAndValidation.dto.user.InvitationDTO;
import main.java.dtoAndValidation.dto.user.InvitationStatusDTO;
import main.java.dtoAndValidation.dto.user.InviteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.userservice.IUserManagementService;
import service.userservice.service.imp.KeycloakService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/userservice")
public class InvitationController {

    private final KeycloakService keycloakService;
    private final IUserManagementService userManagementService;


    @Autowired
    public InvitationController(KeycloakService keycloakService, IUserManagementService userManagementService) {
        this.keycloakService = keycloakService;
        this.userManagementService = userManagementService;
    }


    /**
     *ERROR because of saving in DB
     */
    @PostMapping("/invitation/invite")
    public InvitationStatusDTO invite(@RequestBody InviteDTO newInvitation){
        return userManagementService.invite(newInvitation);
    }

    /**
     *ERROR because of saving in DB
     */
    @GetMapping("/invitation/receive")
    public List<InvitationDTO> getInvitations(HttpServletRequest request){
        return userManagementService.getInvitations(request);
    }

    @PutMapping("/invitation/accept/{groupId}")
    public InvitationStatusDTO acceptInvitation(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.acceptInvitation(request, groupId);
    }

    @PutMapping("/invitation/decline/{groupId}")
    public InvitationStatusDTO declineInvitation(HttpServletRequest request, @PathVariable Long groupId){
        return userManagementService.declineInvitation(request, groupId);
    }

}
