package service.contentservice.persistence.documentbased;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import service.contentservice.persistence.EmbeddedDocumentIdentifier;
import service.contentservice.validation.IValidatable;


public class Category extends EmbeddedDocumentIdentifier implements IValidatable {
    @Field(targetType = FieldType.STRING)
    public String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

}
