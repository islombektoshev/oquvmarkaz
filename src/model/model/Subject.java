package model.model;

public class Subject implements Comparable<Subject> {

    private String subjectName;
    private int subjectId;

    public Subject(String subjectName, int subjectId) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.subjectId = -1;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return subjectName;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Subject) obj).getSubjectId() == subjectId
                    || ((Subject) obj).getSubjectName().equalsIgnoreCase(subjectName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int compareTo(Subject o) {
        return (subjectId > o.subjectId) ? 1 : -1;
    }
}
