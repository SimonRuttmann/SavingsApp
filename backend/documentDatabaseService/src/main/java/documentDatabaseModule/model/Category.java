package documentDatabaseModule.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import documentDatabaseModule.service.EmbeddedDocumentIdentifier;

public class Category extends EmbeddedDocumentIdentifier {
    @Field(targetType = FieldType.STRING)
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {
    }


}
