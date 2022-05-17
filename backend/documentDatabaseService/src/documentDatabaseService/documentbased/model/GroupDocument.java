package documentDatabaseService.documentbased.model;

import documentDatabaseService.documentbased.service.EmbeddedDocumentIdentifier;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collation = "groupDocuments")
public class GroupDocument extends EmbeddedDocumentIdentifier {

    @Indexed
    @Field(targetType = FieldType.INT64)
    public Long groupId;


    @Field(targetType = FieldType.ARRAY)
    public List<SavingEntry> savingEntries = new ArrayList<>();


    @Field(targetType = FieldType.ARRAY)
    public List<Category> categories = new ArrayList<>();


    public GroupDocument() { }


    @PersistenceConstructor
    public GroupDocument(Long groupId, List<SavingEntry> savingEntries, List<Category> categories) {
        this.groupId = groupId;
        this.savingEntries = savingEntries;
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDocument)) return false;
        GroupDocument that = (GroupDocument) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
