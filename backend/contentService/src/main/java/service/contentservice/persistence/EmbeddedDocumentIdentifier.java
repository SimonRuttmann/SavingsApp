package service.contentservice.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Objects;

public abstract class EmbeddedDocumentIdentifier implements IEmbeddedDocumentIdentifier {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private ObjectId id;

    @Override
    public void setIdIfNotExists() {
        if(id != null) return;
        id = new ObjectId();
    }

    @Override
    public ObjectId getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddedDocumentIdentifier)) return false;
        EmbeddedDocumentIdentifier document = (EmbeddedDocumentIdentifier) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
