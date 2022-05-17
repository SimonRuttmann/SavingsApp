package dtoAndValidation.validation;

import documentDatabaseService.documentbased.service.GroupDocumentIdentifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import relationalDatabaseService.service.IDatabaseService;

@Service
public interface IValidateAndResolveDocumentService<T> {

    ValidatedValue<ResponseEntity<T>, GroupDocumentIdentifier> validateAndResolveIdentifier(
            boolean isValid, Long groupId, IDatabaseService databaseService);

}
