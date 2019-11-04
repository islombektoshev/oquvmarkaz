package model.model;

/**
 *
 * @author Islom
 */
public class Group {
    private String groupName;
    private int groupId;

    public Group(String groupName, int groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
