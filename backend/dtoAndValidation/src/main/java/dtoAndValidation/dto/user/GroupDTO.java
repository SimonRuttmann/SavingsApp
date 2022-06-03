package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO implements IValidatable {

    private Long id = null;

    private String groupName;

    private Boolean personGroup = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDTO)) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return Objects.equals(id, groupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
