package model.model;

/**
 *
 * @author Islom
 */
public class Group implements Comparable<Group>{
    private String groupName;
    private int groupId;

    public Group(String groupName, int groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }
    public Group(String groupName) {
        this.groupName = groupName;
        this.groupId = -1;
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

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Group) obj).getGroupId() == groupId  
                    || ((Group) obj).getGroupName().equals(groupName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int compareTo(Group o) {
        return (groupId>o.getGroupId())?1:-1;
    }
    
}
