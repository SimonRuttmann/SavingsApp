package dtoAndValidation.dto.content;

import dtoAndValidation.validation.IValidatable;

import java.util.Date;
import java.util.Objects;

public class SavingEntryDTO implements IValidatable {

    private String id;

    private String name;

    private Double costBalance;

    private CategoryDTO category;

    private Date creationDate;

    private String creator;

    private String description;


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Double getCostBalance() {
        return costBalance;
    }


    public CategoryDTO getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostBalance(Double costBalance) {
        this.costBalance = costBalance;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public String getCreator() {
        return creator;
    }


    public String getDescription() {
        return description;
    }


    public SavingEntryDTO(String id, String name, Double costBalance,
                          CategoryDTO category, Date creationDate,
                          String creator, String description) {
        this.id = id;
        this.name = name;
        this.costBalance = costBalance;
        this.category = category;
        this.creationDate = creationDate;
        this.creator = creator;
        this.description = description;
    }

    public SavingEntryDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingEntryDTO)) return false;
        SavingEntryDTO that = (SavingEntryDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
