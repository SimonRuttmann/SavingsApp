package service.contentservice.persistence.documentbased;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import service.contentservice.persistence.DocumentbasedGeneratableIdentifiable;
import service.contentservice.validation.IValidatable;

import java.util.Objects;


public class Category extends DocumentbasedGeneratableIdentifiable implements IValidatable {
    @Field(targetType = FieldType.STRING)
    public String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

}
