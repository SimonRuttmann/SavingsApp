package service.contentservice.persistence;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.SavingEntry;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupDocumentService implements IGroupDocumentService{

    @Autowired
    private MongoOperations mongo;
    //@Autowired
    //public GroupDocumentService(MongoOperations mongo) {
    //    this.mongo = mongo;
    //}

    private Query getDocumentQuery(GroupDocumentIdentifier identifier){

        String isUserGroup = "isUserGroup";
        String id = "id";

        Query query = new Query();
        query.addCriteria(Criteria.
                            where(id).
                            is(identifier.getGroupId()));

        query.addCriteria(Criteria.
                            where(isUserGroup).
                            is(identifier.isUserGroup()));

        return query;
    }

    private GroupDocument getGroupDocument(Long groupId, boolean isUserDocument){
        return getGroupDocument(new GroupDocumentIdentifier(groupId, isUserDocument));
    }
    @Override
    public GroupDocument getGroupDocument(GroupDocumentIdentifier identifier) {

        return mongo.findOne(getDocumentQuery(identifier),
                             GroupDocument.class);
    }

    @Override
    public GroupDocument createDocument(GroupDocument groupDocument) {


        var document = getGroupDocument(groupDocument.groupId, groupDocument.isUserGroup);
        if(document != null) return null;

        groupDocument.setIdIfNotExists();
        groupDocument.categories.forEach(IEmbeddedDocumentIdentifier::setIdIfNotExists);
        groupDocument.savingEntries.forEach(IEmbeddedDocumentIdentifier::setIdIfNotExists);

        return mongo.save(groupDocument);
    }

    @Override
    public boolean deleteDocument(GroupDocumentIdentifier identifier) {

        return mongo.remove(getDocumentQuery(identifier),
                            GroupDocument.class).
                            wasAcknowledged();
    }

    @Override
    public Category insertCategory(GroupDocumentIdentifier identifier, Category category) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        category.setIdIfNotExists();
        document.categories.add(category);

        mongo.save(document);

        return category;
    }

    @Override
    public Category updateCategory(GroupDocumentIdentifier identifier, Category category) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var containsCategory = document.categories.removeIf(cat -> cat.getId().equals(category.getId()));
        if(!containsCategory) return null;

        document.categories.add(category);

        document.savingEntries.forEach(savingEntry -> {
            if (savingEntry.category.getId().equals(category.getId()))
                savingEntry.category = category;
        });

        mongo.save(document);

        return category;
    }

    @Override
    public void deleteCategory(GroupDocumentIdentifier identifier, ObjectId categoryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return;

        var removedCategories = document.categories.removeIf(category -> category.getId().equals(categoryId));
        if(!removedCategories) return;

        document.savingEntries.removeIf(savingEntry -> savingEntry.category.getId().equals(categoryId));

        mongo.save(document);
    }

    @Override
    public SavingEntry addSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        savingEntry.setIdIfNotExists();

        var knownCategories = resolveKnownCategories(document, savingEntry.category.getId());
        if(knownCategories.size() != 1) return null;

        document.savingEntries.add(savingEntry);
        mongo.save(document);

        return savingEntry;
    }

    @Override
    public SavingEntry updateSavingEntry(GroupDocumentIdentifier identifier, SavingEntry savingEntry) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var knownCategories = resolveKnownCategories(document, savingEntry.category.getId());
        if(knownCategories.size() != 1) return null;

        var containsSavingEntry = document.savingEntries.removeIf(entry -> entry.getId().equals(savingEntry.getId()));
        if(!containsSavingEntry) return null;

        document.savingEntries.add(savingEntry);
        mongo.save(document);

        return savingEntry;
    }

    private List<Category> resolveKnownCategories(GroupDocument document, ObjectId objectId){

         return document.categories.
                stream().
                filter(cat -> cat.getId().equals(objectId)).
                collect(Collectors.toList());
    }
    @Override
    public void deleteSavingEntry(GroupDocumentIdentifier identifier, ObjectId savingEntryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return;

        var removedEntry = document.savingEntries.removeIf(savingEntry -> savingEntry.getId() == savingEntryId);
        if(!removedEntry) return;

        mongo.save(document);
    }
}
