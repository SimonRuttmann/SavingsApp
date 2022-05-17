package documentDatabaseService.documentbased.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import documentDatabaseService.documentbased.model.Category;
import documentDatabaseService.documentbased.model.GroupDocument;
import documentDatabaseService.documentbased.model.SavingEntry;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupDocumentService implements IGroupDocumentService{

    private final MongoOperations mongo;

    @Autowired
    public GroupDocumentService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    private Query getDocumentQuery(Long identifier){

        String isUserGroup = "isUserGroup";
        String id = "id";

        Query query = new Query();
        query.addCriteria(  Criteria.
                            where(id).
                            is(identifier));

        return query;
    }


    @Override
    public GroupDocument getGroupDocument(Long identifier) {

        return mongo.findOne(getDocumentQuery(identifier),
                             GroupDocument.class);
    }

    @Override
    public GroupDocument createDocument(GroupDocument groupDocument) {

        var document = getGroupDocument(groupDocument.groupId);
        if(document != null) return null;

        groupDocument.setIdIfNotExists();
        groupDocument.categories.forEach(IEmbeddedDocumentIdentifier::setIdIfNotExists);
        groupDocument.savingEntries.forEach(IEmbeddedDocumentIdentifier::setIdIfNotExists);

        return mongo.save(groupDocument);
    }

    @Override
    public GroupDocument updateGroupDocument(Long identifier, GroupDocument groupDocument) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        return mongo.save(groupDocument);
    }

    @Override
    public boolean deleteDocument(Long identifier) {

         mongo.remove(getDocumentQuery(identifier),
                            GroupDocument.class);
        return true;
    }

    @Override
    public Category insertCategory(Long identifier, Category category) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        category.setIdIfNotExists();
        document.categories.add(category);

        mongo.save(document);

        return category;
    }

    @Override
    public Category getCategory(Long identifier, ObjectId categoryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var searchedCategory = document.
                categories.
                stream().
                filter(category ->  category.getId().equals(categoryId)).
                findFirst();

        if(searchedCategory.isEmpty()) return null;
        return searchedCategory.get();
    }

    @Override
    public Category updateCategory(Long identifier, Category category) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var containsCategory = document.categories.removeIf(cat -> cat.getId().equals(category.getId()));
        if(!containsCategory) return null;

        document.categories.add(category);

        document.savingEntries.forEach(savingEntry -> {
            if (savingEntry.getCategory().getId().equals(category.getId()))
                savingEntry.setCategory(category);
        });

        mongo.save(document);

        return category;
    }

    @Override
    public void deleteCategory(Long identifier, ObjectId categoryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return;

        var removedCategories = document.
                                        categories.
                                        removeIf( category ->
                                                  category.getId().equals(categoryId));

        if(!removedCategories) return;

        document.savingEntries.removeIf(savingEntry -> savingEntry.getCategory().getId().equals(categoryId));

        mongo.save(document);
    }

    @Override
    public SavingEntry addSavingEntry(Long identifier, SavingEntry savingEntry) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        savingEntry.setIdIfNotExists();

        var knownCategories = resolveKnownCategories(document, savingEntry.getCategory().getId());
        if(knownCategories.size() != 1) return null;

        document.savingEntries.add(savingEntry);
        mongo.save(document);

        return savingEntry;
    }


    @Override
    public SavingEntry getSavingEntry(Long identifier, ObjectId savingEntryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var entry = document.
                savingEntries.
                stream().
                filter(savingEntry ->  savingEntry.getId().equals(savingEntryId)).
                findFirst();

        if(entry.isEmpty()) return null;
        return entry.get();
    }

    @Override
    public SavingEntry updateSavingEntry(Long identifier, SavingEntry savingEntry) {

        var document = getGroupDocument(identifier);
        if(document == null) return null;

        var knownCategories = resolveKnownCategories(document, savingEntry.getCategory().getId());
        if(knownCategories.size() != 1) return null;

        var containsSavingEntry =   document.
                                            savingEntries.
                                            removeIf( entry ->
                                                      entry.getId().equals(savingEntry.getId()));

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
    public void deleteSavingEntry(Long identifier, ObjectId savingEntryId) {

        var document = getGroupDocument(identifier);
        if(document == null) return;

        var removedEntry =  document.
                                    savingEntries.
                                    removeIf( savingEntry ->
                                              savingEntry.getId().equals(savingEntryId));

        if(!removedEntry) return;

        mongo.save(document);
    }
}
