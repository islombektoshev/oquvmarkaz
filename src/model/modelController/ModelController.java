package model.modelController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.model.Data;
import model.model.Group;
import model.model.Student;
import model.model.Subject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Islom
 */
public class ModelController {

    public static final String TAG_root = "oquvmarkaz";
    public static final String TAG_fanlar = "fanlar";
    public static final String TAG_gruhlar = "gruhlar";
    public static final String TAG_oquvchilar = "oquvchilar";
    public static final String TAG_fan = "fan";
    public static final String TAG_guruh = "guruh";
    public static final String TAG_oquvchi = "oquvchi";

    public static final String NONE_NAME = "NOMALUM";
    public static File FILE = new File("defaultdata.mdf");
    public static File EMPTYFILE = new File("emptydata.mdf");

    static {
        loadFilePath();
        read();
    }

    public static void saveFilePath(File f) {
        try {
            FILE = f;
            Properties p = new Properties();
            p.setProperty("filepath", f.getAbsolutePath());
            p.store(new FileOutputStream("apppropertes.properties"), "Filepath");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadFilePath() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(new File("apppropertes.properties")));
            FILE = new File(p.getProperty("filepath", FILE.getName()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void read() {
        Data.groups.clear();
        Data.students.clear();
        Data.subjects.clear();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(FILE);

            Element e = doc.getDocumentElement();
            NodeList tempList = e.getElementsByTagName("fanlar");
            Element fanlar = (Element) tempList.item(0);
            tempList = fanlar.getElementsByTagName("fan");

            for (int i = 0; i < tempList.getLength(); i++) {
                NamedNodeMap atribut = tempList.item(i).getAttributes();

                // ADD SUBJECTS TO DATAMODEL
                Data.subjects.add(
                        Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                        new Subject(atribut.getNamedItem("name").getNodeValue(),
                                Integer.parseInt(atribut.getNamedItem("id").getNodeValue())
                        )
                );

                // PRINT SUBJECTS TO CONSOLE
                System.out.println("subject aded: " + atribut.getNamedItem("name").getNodeValue() + "\n"
                        + "id:" + atribut.getNamedItem("id").getNodeValue());
            }
            System.out.println("ADDED ALL SUBJECTS TO DATAMODEL\n");

            Element gruhlar = (Element) e.getElementsByTagName("gruhlar").item(0);
            tempList = gruhlar.getElementsByTagName("guruh");

            for (int i = 0; i < tempList.getLength(); i++) {
                NamedNodeMap atribut = tempList.item(i).getAttributes();

                //ADD GROUP TO DATAMODEL
                Data.groups.add(
                        Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                        new Group(
                                atribut.getNamedItem("name").getNodeValue(),
                                Integer.parseInt(atribut.getNamedItem("id").getNodeValue())
                        )
                );

                System.out.println("ADDED ALL GROUPS TO DATAMODEL\n");
                // PRINT GROUP TO CONSOLE
                System.out.println(
                        "group aded: " + atribut.getNamedItem("name").getNodeValue() + "\n"
                        + "id:" + atribut.getNamedItem("id").getNodeValue()
                );
            }

            Element oquvchilar = (Element) e.getElementsByTagName("oquvchilar").item(0);
            tempList = oquvchilar.getElementsByTagName("oquvchi");

            for (int i = 0; i < tempList.getLength(); i++) {
                NamedNodeMap atribut = tempList.item(i).getAttributes();

                //ADD STUDENT TO DATAMODEL
                Data.students.add(
                        Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                        new Student(
                                Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                                atribut.getNamedItem("name").getNodeValue(),
                                atribut.getNamedItem("surename").getNodeValue(),
                                Integer.parseInt(atribut.getNamedItem("sb1").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("sb2").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("sb3").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("gr").getNodeValue()),
                                (atribut.getNamedItem("tel").getNodeValue().equals("none")
                                || atribut.getNamedItem("tel").getNodeValue().equals("")
                                || atribut.getNamedItem("tel").getNodeValue().equals("null")) ? null : atribut.getNamedItem("tel").getNodeValue()
                        )
                );

                // PRINT UBJECTS TO CONSOLE
                System.out.println(
                        "student aded: " + atribut.getNamedItem("name").getNodeValue() + "\n"
                        + "id:" + atribut.getNamedItem("id").getNodeValue()
                );
            }
            System.out.println("ADDED ALL STUDENTS TO DATAMODEL\n");

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void init() {

    }

    public void deletSubject(int id) {
        Data.subjects.remove(IdScanner.getIndexbyIdSubjet(id));
        int size = Data.subjects.size();
        for (int i = 0; i < size; i++) {
            Data.subjects.get(i).setSubjectId(i);
        }
        synchronizWithStudentForDeleting__Subject(id);
    }

    public void deletGroup(int id) {
        Data.groups.remove(IdScanner.getIndexbyIdGroup(id));
        int size = Data.groups.size();
        for (int i = 0; i < size; i++) {
            Data.groups.get(i).setGroupId(i);
        }
        synchronizWithStudentForDeleting__Group(id);
    }

    public void deletStudent(int id) {
        Data.students.remove(IdScanner.getIndexbyIdSutudent(id));
        int size = Data.students.size();
        for (int i = 0; i < size; i++) {
            Data.students.get(i).setStudentId(i);
        }
    }

    public boolean deletSubject(Subject s) {
        int id = s.getSubjectId();
        boolean del = Data.subjects.remove(s);
        int size = Data.subjects.size();
        for (int i = 0; i < size; i++) {
            Data.subjects.get(i).setSubjectId(i);
        }
        synchronizWithStudentForDeleting__Subject(id);
        return del;
    }

    public boolean deletGroup(Group g) {
        int id = g.getGroupId();
        boolean del = Data.groups.remove(g);
        int size = Data.groups.size();
        for (int i = 0; i < size; i++) {
            Data.groups.get(i).setGroupId(i);
        }
        synchronizWithStudentForDeleting__Group(id);
        return del;
    }

    public boolean deletStudent(Student s) {
        boolean del = Data.students.remove(s);
        int size = Data.students.size();
        for (int i = 0; i < size; i++) {
            Data.students.get(i).setStudentId(i);
        }
        return del;
    }

    public boolean addSubject(Subject sb) {
        boolean a = false;
        if (!Data.subjects.contains(sb)) {
            sb.setSubjectId(Data.subjects.size());
            a = Data.subjects.add(sb);
        }
        return a;
    }

    public void addSubject(Subject sb, int i) {
        if (!Data.subjects.contains(sb)) {
            Data.subjects.add(i, sb);
            for (int x = 0; x < Data.subjects.size(); x++) {
                Data.subjects.get(x).setSubjectId(x);
            }
        }
    }

    public boolean addGroup(Group gr) {
        boolean a = false;
        if (!Data.groups.contains(gr)) {
            gr.setGroupId(Data.groups.size());
            a = Data.groups.add(gr);
        }

        return a;
    }

    public void addGroup(Group gr, int i) {
        if (!Data.groups.contains(gr)) {
            Data.groups.add(i, gr);
            for (int x = 0; x < Data.groups.size(); x++) {
                Data.groups.get(x).setGroupId(x);
            }
        }
    }

    public boolean addStudent(Student s) {
        boolean a = false;
        if (!Data.students.contains(s)) {
            s.setStudentId(Data.students.size());
            a = Data.students.add(s);
        }
        return a;
    }

    public void addStudent(Student s, int i) {
        if (!Data.students.contains(s)) {
            Data.students.add(i, s);
            for (int x = 0; x < Data.students.size(); x++) {
                Data.students.get(x).setStudentId(x);
            }
        }
    }

    public void setStudent(Student s, int i) {
        Data.students.set(i, s);
        for (int x = 0; x < Data.students.size(); x++) {
            Data.students.get(x).setStudentId(x);
        }
    }

    public void setStudentOnTheEnd(Student s) {
        Data.students.set(Data.students.size() - 1, s);
        for (int x = 0; x < Data.students.size(); x++) {
            Data.students.get(x).setStudentId(x);
        }
    }

    public ObservableList<Student> getStudents() {
        ObservableList<Student> list = FXCollections.observableArrayList();
        for (int i = 0; i < Data.students.size(); i++) {
            list.add(Data.students.get(i));
        }
        return list;
    }

    public ObservableList<Group> getGroups() {
        ObservableList<Group> list = FXCollections.observableArrayList();
        for (int i = 0; i < Data.groups.size(); i++) {
            list.add(Data.groups.get(i));
        }
        return list;
    }

    public ObservableList<Subject> getSubjects() {
        ObservableList<Subject> list = FXCollections.observableArrayList();
        for (int i = 0; i < Data.subjects.size(); i++) {
            list.add(Data.subjects.get(i));
        }
        return list;
    }

    public Group getGroup(int i) {
        if (Data.groups.size() > i && i > -1) {
            return Data.groups.get(i);
        } else {
            return new Group(NONE_NAME, -1);
        }
    }

    public Subject getSubject(int i) {
        if (Data.subjects.size() > i && i > -1) {
            return Data.subjects.get(i);
        } else {
            return new Subject(NONE_NAME, -1);
        }
    }

    public Student getStudent(int i) {
        if (Data.students.size() > i && i > -1) {
            return Data.students.get(i);
        } else {
            return new Student(-1, NONE_NAME, "", -1, -1, -1, -1, null);
        }
    }

    public void resetID() {
        for (int x = 0; x < Data.groups.size(); x++) {
            Data.groups.get(x).setGroupId(x);
        }
        for (int x = 0; x < Data.subjects.size(); x++) {
            Data.subjects.get(x).setSubjectId(x);
        }
        for (int x = 0; x < Data.students.size(); x++) {
            Data.students.get(x).setStudentId(x);
        }
    }

    public void saveDocument() {
        try {
            Collections.sort(Data.students);
            Collections.sort(Data.groups);
            Collections.sort(Data.subjects);
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.newDocument();

            doc = doc.getImplementation().createDocument("uri", TAG_root, doc.getDoctype());

            Element fanlar = doc.createElement(TAG_fanlar);
            for (Subject x : Data.subjects) {
                Element t = doc.createElement(TAG_fan);
                t.setAttribute("name", x.getSubjectName());
                t.setAttribute("id", x.getSubjectId() + "");
                fanlar.appendChild(t);
            }
            doc.getDocumentElement().appendChild(fanlar);

            Element gruhlar = doc.createElement(TAG_gruhlar);
            for (Group x : Data.groups) {
                Element t = doc.createElement(TAG_guruh);
                t.setAttribute("name", x.getGroupName());
                t.setAttribute("id", x.getGroupId() + "");
                gruhlar.appendChild(t);
            }
            doc.getDocumentElement().appendChild(gruhlar);

            Element oquvchilar = doc.createElement(TAG_oquvchilar);
            for (Student x : Data.students) {
                Element t = doc.createElement(TAG_oquvchi);
                t.setAttribute("name", x.getName());
                t.setAttribute("surename", x.getSurename());
                t.setAttribute("sb1", x.getSbId1() + "");
                t.setAttribute("sb2", x.getSbId2() + "");
                t.setAttribute("sb3", x.getSbId3() + "");
                t.setAttribute("gr", x.getGrId() + "");
                t.setAttribute("tel", x.getTel());
                t.setAttribute("id", x.getStudentId() + "");

                oquvchilar.appendChild(t);
            }
            doc.getDocumentElement().appendChild(oquvchilar);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer t = factory.newTransformer();
            DOMSource dOMSource = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(FILE));

            t.transform(dOMSource, result);
            
            saveFilePath(FILE);

        } catch (ParserConfigurationException | SecurityException | FileNotFoundException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Bu metod Subject lardan biri o'chirilganda Student klasidaki subjectlarni
     * to'rilab chiqadi yani ochirilgandan keyin student ning sbId1,sbId2,sbId3
     * lari o'zgartradi chunki bu endi bittaga kam bo'lishi kerak
     *
     */
    public void synchronizWithStudentForDeleting__Subject(int i) {
        for (Student x : Data.students) {
            if (x.getSbId1() == i) {
                x.setSbId1(-1);
            }
            if (x.getSbId2() == i) {
                x.setSbId2(-1);
            }
            if (x.getSbId3() == i) {
                x.setSbId3(-1);
            }

            if (x.getSbId1() > i) {
                x.setSbId1(x.getSbId1() - 1);
            }
            if (x.getSbId2() > i) {
                x.setSbId2(x.getSbId2() - 1);
            }
            if (x.getSbId3() > i) {
                x.setSbId3(x.getSbId3() - 1);
            }

        }
    }

    /**
     * Bu metod Group lardan biri o'chirilganda Student klasidaki gruhlarni
     * to'rilab chiqadi yani ochirilgandan keyin student ning grId sini
     * o'zgartriadi chunki bu endi bittaga kam bo'lishi kerak
     *
     */
    public void synchronizWithStudentForDeleting__Group(int i) {
        for (Student x : Data.students) {
            if (x.getGrId() == i) {
                x.setGrId(-1);
            }

            if (x.getGrId() > i) {
                x.setGrId(x.getGrId() - 1);
            }
        }
    }

    public static int sizeofStudents() {
        return Data.students.size();
    }

    public static int sizeofGroups() {
        return Data.groups.size();
    }

    public static int sizeofSubjects() {
        return Data.subjects.size();
    }
}
