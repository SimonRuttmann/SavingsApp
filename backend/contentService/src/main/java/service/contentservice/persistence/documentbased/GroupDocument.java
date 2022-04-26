package service.contentservice.persistence.documentbased;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Objects;

@Document()
public class GroupDocument {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    public String documentId;


    @Indexed
    @Field(targetType = FieldType.INT64)
    public Long groupId;


    @DBRef
    @Field(targetType = FieldType.ARRAY)
    public List<SavingEntry> savingEntries;


    @DBRef
    @Field(targetType = FieldType.ARRAY)
    public List<Category> categories;

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
        return Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId);
    }
}
