package documentDatabaseService.documentbased.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import documentDatabaseService.documentbased.model.Category;
import documentDatabaseService.documentbased.model.GroupDocument;
import documentDatabaseService.documentbased.model.SavingEntry;

@Service
public interface IGroupDocumentService {

    GroupDocument getGroupDocument(Long identifier);
    GroupDocument createDocument(GroupDocument groupDocument);

    GroupDocument updateGroupDocument(Long identifier, GroupDocument groupDocument);
    boolean deleteDocument(Long identifier);


    Category insertCategory(Long identifier, Category category);

    Category getCategory(Long identifier, ObjectId categoryId);
    Category updateCategory(Long identifier, Category category);
    void deleteCategory(Long identifier, ObjectId categoryId);


    SavingEntry addSavingEntry(Long identifier, SavingEntry savingEntry);

    SavingEntry getSavingEntry(Long identifier, ObjectId savingEntryId);
    SavingEntry updateSavingEntry(Long identifier, SavingEntry savingEntry);
    void deleteSavingEntry(Long identifier, ObjectId savingEntryId);

}
