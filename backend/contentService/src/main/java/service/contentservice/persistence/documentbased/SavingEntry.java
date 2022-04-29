package service.contentservice.persistence.documentbased;


import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import service.contentservice.persistence.EmbeddedDocumentIdentifier;
import service.contentservice.validation.IValidatable;

import java.util.Date;
import java.util.Objects;

public class SavingEntry extends EmbeddedDocumentIdentifier implements IValidatable {

    @Field(targetType = FieldType.STRING)
    public String name;


    @Field(targetType = FieldType.DOUBLE)
    public Double costBalance;


    public Category category;


    @Field(targetType = FieldType.DATE_TIME)
    public Date creationDate;


    @Field(targetType = FieldType.STRING)
    public String creator;


    @Field(targetType = FieldType.STRING)
    public String description;

    public SavingEntry() {
    }

    public SavingEntry(
            String name, Double costBalance, Category category,
            Date creationDate, String creator, String description) {
        this.name = name;
        this.costBalance = costBalance;
        this.category = category;
        this.creationDate = creationDate;
        this.creator = creator;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingEntry)) return false;
        SavingEntry that = (SavingEntry) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
