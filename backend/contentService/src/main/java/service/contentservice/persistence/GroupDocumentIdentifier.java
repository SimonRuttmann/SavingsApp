package service.contentservice.persistence;

public class GroupDocumentIdentifier {

    private final Long groupId;
    private final boolean isUserGroup;

    public GroupDocumentIdentifier(Long groupId, boolean isUserGroup) {
        this.groupId = groupId;
        this.isUserGroup = isUserGroup;
    }

    public Long getGroupId() {
        return groupId;
    }

    public boolean isUserGroup() {
        return isUserGroup;
    }

}
