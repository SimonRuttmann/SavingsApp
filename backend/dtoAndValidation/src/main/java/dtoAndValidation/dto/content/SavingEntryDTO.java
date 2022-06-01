package dtoAndValidation.dto.content;

import dtoAndValidation.validation.IValidatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingEntryDTO implements IValidatable {

    private String id;

    private String name;

    private Double costBalance;

    private CategoryDTO category;

    private Date creationDate;

    private String creator;

    private String description;

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
