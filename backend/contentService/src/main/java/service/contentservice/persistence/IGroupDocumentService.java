package service.contentservice.persistence;

import org.springframework.stereotype.Service;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.SavingEntry;

@Service
public interface IGroupDocumentService {

    GroupDocument getGroupDocument(GroupDocumentIdentifier identifier);

    GroupDocument createDocument(GroupDocument groupDocument);

    void deleteDocument(GroupDocumentIdentifier identifier);


    boolean insertCategory(GroupDocumentIdentifier identifier, Category category);

    boolean updateCategory(GroupDocumentIdentifier identifier, Category category);

    void deleteCategory(GroupDocumentIdentifier identifier, Long categoryId);


    SavingEntry addSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry);

    SavingEntry updateSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry);

    void deleteSavingEntry(GroupDocumentIdentifier identifier, Long savingEntryId);

}
