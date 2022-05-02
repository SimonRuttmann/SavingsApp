package service.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userservice")
public class UserServiceController {


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
