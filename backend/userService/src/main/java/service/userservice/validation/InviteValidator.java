package service.userservice.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import service.userservice.businessmodel.account.GroupDTO;
import service.userservice.businessmodel.account.InviteDTO;

public class InviteValidator implements IValidator<InviteDTO>{

    @Override
    public boolean validate(InviteDTO toValidate, boolean withId) {
        if(toValidate == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RequestDto is missing");
        if(toValidate.userId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId is missing");
        if(toValidate.groupId == null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "GroupId is missing");

        return true;
    }
}
