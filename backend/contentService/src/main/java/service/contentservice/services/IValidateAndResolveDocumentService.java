package service.contentservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.contentservice.persistence.GroupDocumentIdentifier;
import service.contentservice.util.ValidatedValue;

@Service
public interface IValidateAndResolveDocumentService<T> {

    ValidatedValue<ResponseEntity<T>, GroupDocumentIdentifier> validateAndResolveIdentifier(
            boolean isValid, Long groupId, IDatabaseService databaseService);

}
