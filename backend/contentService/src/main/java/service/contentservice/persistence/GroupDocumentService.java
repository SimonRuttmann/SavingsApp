package service.contentservice.persistence;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.IGroupDocumentRepository;
import service.contentservice.persistence.documentbased.SavingEntry;
import service.contentservice.persistence.relational.entity.Group;

import java.util.List;

@Service
public class GroupDocumentService implements IGroupDocumentService{

    private final String isUserGroup = "isUserGroup";
    private final String id = "id";

    private Query getDocumentQuery(GroupDocumentIdentifier identifier){
        Query query = new Query();
        query.addCriteria(Criteria.where(id).is(identifier.getGroupId()));
        query.addCriteria(Criteria.where(isUserGroup).is(identifier.isUserGroup()));
        return query;
    }

    private void addIdentifier(Category category){
        category.id = new ObjectId();
    }

    private void addIdentifier(SavingEntry savingEntry){
        savingEntry.id = new ObjectId();
    }

    @Autowired
    private IGroupDocumentRepository repository;

    @Autowired
    private MongoOperations mongo;

    @Override
    public GroupDocument getGroupDocument(GroupDocumentIdentifier identifier) {
        return mongo.findOne(getDocumentQuery(identifier), GroupDocument.class);
    }

    @Override
    public GroupDocument createDocument(GroupDocument groupDocument) {
        return repository.save(groupDocument);
    }

    @Override
    public void deleteDocument(GroupDocumentIdentifier identifier) {
        mongo.remove(getDocumentQuery(identifier), GroupDocument.class);
    }

    @Override
    public Category insertCategory(GroupDocumentIdentifier identifier, Category category) {
        var document = getGroupDocument(identifier);
        if(document == null) return null;

        addIdentifier(category);
        document.categories.add(category);
        mongo.save(document);

        return category;
    }

    @Override
    public boolean updateCategory(GroupDocumentIdentifier identifier, Category category) {
        var document = getGroupDocument(identifier);

        if(document == null) return false;

        document.categories.removeIf(cat -> cat.id == category.id);
        document.categories.add(category);

        mongo.save(document);

        return true;
    }

    @Override
    public void deleteCategory(GroupDocumentIdentifier identifier, Long categoryId) {
        var document = getGroupDocument(identifier);



        document.categories.removeIf(cat -> cat.id == categoryId);

        mongo.save(document);


    }

    @Override
    public SavingEntry addSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry) {
        return null;
    }

    @Override
    public SavingEntry updateSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry) {
        return null;
    }

    @Override
    public void deleteSavingEntry(GroupDocumentIdentifier identifier, Long savingEntryId) {

    }
}
