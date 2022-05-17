package documentDatabaseService.documentbased.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.persistence.EmbeddedDocumentIdentifier;
import service.contentservice.util.DocObjectIdUtil;


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
