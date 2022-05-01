package service.contentservice.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.contentservice.persistence.GroupDocumentIdentifier;
import service.contentservice.util.IdentifierUtil;
import service.contentservice.util.ValidatedValue;

@Service
public class ValidateAndResolveDocumentService<T> implements IValidateAndResolveDocumentService<T>{


    @Override
    public ValidatedValue<ResponseEntity<T>, GroupDocumentIdentifier> validateAndResolveIdentifier(
            boolean isInvalid, Long groupId, IDatabaseService databaseService) {

        var returnValue = new ValidatedValue<ResponseEntity<T>, GroupDocumentIdentifier>();

        if(isInvalid) {
            returnValue.setException(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            return returnValue;
        }

        var result = IdentifierUtil.resolveIdentifyingGroup(groupId, databaseService);

        if(result.getFirst()){
            returnValue.setException(new ResponseEntity<T>(HttpStatus.NOT_FOUND));
            return returnValue;
        }

        returnValue.setException(null);
        returnValue.setValue(new GroupDocumentIdentifier(groupId, result.getSecond()));
        return returnValue;
    }

}
