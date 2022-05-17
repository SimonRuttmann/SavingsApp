package documentDatabaseService.documentbased.model;


import documentDatabaseService.documentbased.service.EmbeddedDocumentIdentifier;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.Objects;

public class SavingEntry extends EmbeddedDocumentIdentifier {

    @Field(targetType = FieldType.STRING)
    private String name;


    @Field(targetType = FieldType.DOUBLE)
    private Double costBalance;


    private Category category;


    @Field(targetType = FieldType.DATE_TIME)
    private Date creationDate;


    @Field(targetType = FieldType.STRING)
    private String creator;


    @Field(targetType = FieldType.STRING)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCostBalance() {
        return costBalance;
    }

    public void setCostBalance(Double costBalance) {
        this.costBalance = costBalance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
