package service.contentservice.businessmodel.content;

import service.contentservice.validation.IValidatable;

import java.util.Objects;

public class CategoryDTO implements IValidatable {

    private String id;

    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public CategoryDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
