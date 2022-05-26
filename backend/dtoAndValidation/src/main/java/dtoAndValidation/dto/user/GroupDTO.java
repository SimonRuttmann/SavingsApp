package dtoAndValidation.dto.user;

import dtoAndValidation.validation.IValidatable;

import java.util.Objects;

public class GroupDTO implements IValidatable {

    private Long id = null;

    private String groupName;

    private Boolean personGroup = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getPersonGroupBoolean() {
        return personGroup;
    }

    public void setPersonGroupBoolean(Boolean personGroup) {
        this.personGroup = personGroup;
    }

    public GroupDTO() {}

    public GroupDTO(Long id, String groupName, Boolean personGroup) {
        this.id = id;
        this.groupName = groupName;
    }

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
