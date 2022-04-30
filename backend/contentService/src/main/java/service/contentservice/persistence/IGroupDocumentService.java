package service.contentservice.persistence;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.SavingEntry;

@Service
public interface IGroupDocumentService {

    GroupDocument getGroupDocument(GroupDocumentIdentifier identifier);
    GroupDocument createDocument(GroupDocument groupDocument);

    GroupDocument updateGroupDocument(GroupDocumentIdentifier identifier, GroupDocument groupDocument);
    boolean deleteDocument(GroupDocumentIdentifier identifier);


    Category insertCategory(GroupDocumentIdentifier identifier, Category category);

    Category getCategory(GroupDocumentIdentifier identifier, ObjectId categoryId);
    Category updateCategory(GroupDocumentIdentifier identifier, Category category);
    void deleteCategory(GroupDocumentIdentifier identifier, ObjectId categoryId);


    SavingEntry addSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry);

    SavingEntry getSavingEntry(GroupDocumentIdentifier identifier, ObjectId savingEntryId);
    SavingEntry updateSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry);
    void deleteSavingEntry(GroupDocumentIdentifier identifier, ObjectId savingEntryId);

}
