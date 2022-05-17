package service.userservice.businessmodel.account;



import service.userservice.validation.IValidatable;

import java.util.Objects;

public class GroupDTO implements IValidatable {

    private Long id;

    private String groupName;

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

    public GroupDTO() {}

    public GroupDTO(Long id, String groupName) {
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
