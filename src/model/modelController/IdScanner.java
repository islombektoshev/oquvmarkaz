package model.modelController;

import java.util.ArrayList;
import java.util.List;
import model.model.Group;
import model.model.Student;
import model.model.Subject;
import static model.model.Data.*;

/**
 *
 * @author Islom
 */
public class IdScanner {

    public static int getEmptySubjectId() {
        List<Integer> ids = new ArrayList<>();

        for (Subject subject : subjects) {
            ids.add(subject.getSubjectId());
        }

        for (int i = 0; i < subjects.size() + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return subjects.size();
    }

    public static int getEmptyGroupId() {
        List<Integer> ids = new ArrayList<>();

        for (Group group : groups) {
            ids.add(group.getGroupId());
        }

        for (int i = 0; i < groups.size() + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return groups.size();
    }

    public static int getEmptyStudentId() {
        List<Integer> ids = new ArrayList<>();

        for (Student student : students) {
            ids.add(student.getStudentId());
        }

        for (int i = 0; i < students.size() + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return students.size();
    }

    public static int getIndexbyIdSubjet(int id) {
        for (int i = 0; i < subjects.size(); i++) {
            if (id == subjects.get(i).getSubjectId()) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndexbyIdGroup(int id) {
        for (int i = 0; i < groups.size(); i++) {
            if (id == groups.get(i).getGroupId()) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndexbyIdSutudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (id == students.get(i).getStudentId()) {
                return i;
            }
        }
        return -1;
    }
}
