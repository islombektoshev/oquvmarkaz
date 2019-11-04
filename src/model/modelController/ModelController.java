package model.modelController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import model.model.Abiturient;
import model.model.BlockTest;
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
    public static final String TAG_bloktestlar = "bloktestlar";
    public static final String TAG_bloktest = "bloktest";
    public static final String TAG_abiturient = "abiturient";
    public static final String TAG_ball = "ball";
    public static final String TAG_izoh = "izoh";

    public static final String NONE_NAME = "NOMALUM";
    public static File FILE = new File("Files/defaultdata.mdf");
    public static File EMPTYFILE = new File("Files/emptydata.mdf");
    public static int selectedBocktestId = 0;
    public static BlocktestCreationType blocktestCreationType = BlocktestCreationType.EVRY;
    public static BlocktestCreationType blocktestCreationTypeNOMALUM = BlocktestCreationType.EVRY;

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
        Data.bockTests.clear();
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

            //READ BLOCK TEST
            System.out.println("read blocktest");
            Element bloktestlar = (Element) e.getElementsByTagName(TAG_bloktestlar).item(0);
            tempList = bloktestlar.getElementsByTagName(TAG_bloktest);
            for (int i = 0; i < tempList.getLength(); i++) {
                Element blocktest = (Element) tempList.item(i);
                NamedNodeMap bAttr = blocktest.getAttributes();
                BlockTest blockTest = new BlockTest(
                        Integer.parseInt(bAttr.getNamedItem("id").getNodeValue()),
                        bAttr.getNamedItem("name").getNodeValue(),
                        new Date(bAttr.getNamedItem("date").getNodeValue())
                );

                NodeList abiturients = blocktest.getElementsByTagName(TAG_abiturient);
                for (int j = 0; j < abiturients.getLength(); j++) {
                    NamedNodeMap atribut = abiturients.item(j).getAttributes();

                    Abiturient abiturient = new Abiturient(
                            Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                            Integer.parseInt(atribut.getNamedItem("studentid").getNodeValue()),
                            atribut.getNamedItem("name").getNodeValue(),
                            atribut.getNamedItem("surename").getNodeValue(),
                            Integer.parseInt(atribut.getNamedItem("sb1").getNodeValue()),
                            Integer.parseInt(atribut.getNamedItem("sb2").getNodeValue()),
                            Integer.parseInt(atribut.getNamedItem("sb3").getNodeValue()),
                            Integer.parseInt(atribut.getNamedItem("gr").getNodeValue()),
                            (atribut.getNamedItem("tel").getNodeValue().equals(NONE_NAME)
                            || atribut.getNamedItem("tel").getNodeValue().equals("")
                            || atribut.getNamedItem("tel").getNodeValue().equals("null")) ? null : atribut.getNamedItem("tel").getNodeValue()
                    );
                    abiturient.setIndividualIzoh(
                            ((Element)abiturients.item(j)).getElementsByTagName(TAG_izoh).getLength()>0?
                                    ((Element)abiturients.item(j)).getElementsByTagName(TAG_izoh).item(0).getAttributes().getNamedItem("text").getNodeValue()
                                    :""
                    );
                    NamedNodeMap ball = ((Element) abiturients.item(j))
                            .getElementsByTagName(TAG_ball)
                            .item(0)
                            .getAttributes();

                    abiturient.setF1(Integer.parseInt(ball.getNamedItem("f1").getNodeValue()));
                    abiturient.setF2(Integer.parseInt(ball.getNamedItem("f2").getNodeValue()));
                    abiturient.setF3(Integer.parseInt(ball.getNamedItem("f3").getNodeValue()));
                    blockTest.add(abiturient);

                    System.out.println("\t\tadd to blocktest an abiturient\n\t\t" + abiturient.getName());
                }
                Data.bockTests.add(blockTest);
                System.out.println("\tadded blocktest " + blockTest.getName());
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
    
    public void deletAbiturient(int i){
        Data.bockTests.get(selectedBocktestId).remove(i);
    }
    
    public void deletBlockTest(int i){
        if(i>=Data.bockTests.size()) return;
        Data.bockTests.remove(i);
        synchronizSelectedBlockTestId();
    }
    
    public void deletBlockTest(BlockTest b){
        Data.bockTests.remove(b);
        synchronizSelectedBlockTestId();
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
        Collections.sort(Data.students);
        Collections.sort(Data.groups);
        Collections.sort(Data.subjects);
        Collections.sort(Data.bockTests);
        for (int x = 0; x < Data.groups.size(); x++) {
            Data.groups.get(x).setGroupId(x);
        }
        for (int x = 0; x < Data.subjects.size(); x++) {
            Data.subjects.get(x).setSubjectId(x);
        }
        for (int x = 0; x < Data.students.size(); x++) {
            Data.students.get(x).setStudentId(x);
        }
        for (int x = 0; x < Data.bockTests.size(); x++) {
            Data.bockTests.get(x).setId(x);
            Collections.sort(Data.bockTests.get(x).getAbiturients());
            for (int i = 0; i < Data.bockTests.get(x).size(); i++) {
                Data.bockTests.get(x).get(i).setId(i);
            }
        }
    }

    public void saveDocument() {
        try {
            resetID();

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

            // Ko'rilishi kerkak
            Element btlar = doc.createElement(TAG_bloktestlar);
            for (BlockTest x : Data.bockTests) {
                Element bt = doc.createElement(TAG_bloktest);
                bt.setAttribute("id", x.getId() + "");
                bt.setAttribute("name", x.getName() + "");
                bt.setAttribute("date", x.getDate().toLocaleString() + "");
                for (Abiturient z : x.getAbiturients()) {
                    Element abiturient = doc.createElement(TAG_abiturient);
                    abiturient.setAttribute("id", z.getId() + "");
                    abiturient.setAttribute("name", z.getName());
                    abiturient.setAttribute("surename", z.getSurename());
                    abiturient.setAttribute("sb1", z.getSbId1() + "");
                    abiturient.setAttribute("sb2", z.getSbId2() + "");
                    abiturient.setAttribute("sb3", z.getSbId3() + "");
                    abiturient.setAttribute("gr", z.getGrId() + "");
                    abiturient.setAttribute("tel", z.getTel());
                    abiturient.setAttribute("id", z.getId()+ "");
                    abiturient.setAttribute("studentid", z.getStudentId() + "");
                    Element ball = doc.createElement(TAG_ball);
                    ball.setAttribute("f1", z.getF1() + "");
                    ball.setAttribute("f2", z.getF2() + "");
                    ball.setAttribute("f3", z.getF3() + "");
                    
                    Element izoh = doc.createElement("izoh");
                    izoh.setAttribute("text", z.getIndividualIzoh());
                    
                    abiturient.appendChild(izoh);
                    abiturient.appendChild(ball);
                    bt.appendChild(abiturient);
                }
                btlar.appendChild(bt);
            }

            doc.getDocumentElement().appendChild(btlar);

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
     * Blocktestlarni yarartib Data.blocktests ga qo'shadi
     * @param name
     * @param date
     * @param groups
     * @param subjects
     */
    
    public void addNewBlockTest(String name, Date date, List<Group> groups, List<Subject> subjects){
        Data.bockTests.add(createBlockTest(name, date, groups, subjects));
    }
    
    /**
     *
     * @param name
     * @param groups
     * @param subjects
     * @return
     */
    public BlockTest createBlockTest(String name, List<Group> groups, List<Subject> subjects) {
       return createBlockTest(name, null, groups, subjects);
    }
    
    /**
     * Blocktestlani yaratib javob qilib qaytaradi
     * @param name
     * @param d
     * @param groups
     * @param subjects
     * @return
     */
    public BlockTest createBlockTest(String name, Date d, List<Group> groups, List<Subject> subjects) {
        List<Abiturient> abiturients = new ArrayList<>();
        int i = 0;
        for (Student x : Data.students) {
            if (isAddToBlocktest(x, groups, subjects)) {
                abiturients.add(new Abiturient(i++, (Student) x.clone()));
                System.out.println("Added " + ((Student) x.clone()).getName() + " to blosktest");
            }
        }
        System.out.println("added all student to new blocktest: ");
        //  BLOCKTESTNI SINXRONLASHTIRISH

        BlockTest blockTest = new BlockTest(
                Data.bockTests.size(),          // bloktest id
                name                            // blokest name
        );
        if (d != null) {
            blockTest.setDate(d);
        }
        blockTest.addAll(abiturients);

        return blockTest;
    }
    
    private boolean isAddToBlocktest(Student x, List<Group> groups, List<Subject> subjects){
        if (blocktestCreationType.equals(blocktestCreationType.EVRY)) {
            if (blocktestCreationTypeNOMALUM.equals(BlocktestCreationType.NOMALUMNI_OL)) {
                return  (groups.contains(x.getGroup()) || x.getGroup().getGroupId() == -1)
                    && (subjects.contains(new Subject(x.getSb1(), x.getSbId1())) || x.getSbId1() == -1)
                    && (subjects.contains(new Subject(x.getSb2(), x.getSbId2())) || x.getSbId2()== -1)
                    && (subjects.contains(new Subject(x.getSb3(), x.getSbId3())) || x.getSbId3() == -1);
            }else {
                 return groups.contains(x.getGroup())
                    && subjects.contains(new Subject(x.getSb1(), x.getSbId1()))
                    && subjects.contains(new Subject(x.getSb2(), x.getSbId2()))
                    && subjects.contains(new Subject(x.getSb3(), x.getSbId3()))
                         &&(
                               x.getGroup().getGroupId() > -1
                             && x.getSbId1() > -1
                             && x.getSbId2() > -1
                             && x.getSbId3() > -1
                         );
            }
        }else {
            if (blocktestCreationTypeNOMALUM.equals(BlocktestCreationType.NOMALUMNI_OL)) {
                return  (
                        groups.contains(x.getGroup()) 
                        || x.getGroup().getGroupId() == -1
                        )
                    && (subjects.contains(new Subject(x.getSb1(), x.getSbId1()))
                        || subjects.contains(new Subject(x.getSb2(), x.getSbId2()))
                        || subjects.contains(new Subject(x.getSb3(), x.getSbId3()))
                        || x.getSbId1() == -1
                        || x.getSbId2() == -1
                        || x.getSbId3() == -1);
            }else {
                return groups.contains(x.getGroup())
                    && (subjects.contains(new Subject(x.getSb1(), x.getSbId1()))
                        || subjects.contains(new Subject(x.getSb2(), x.getSbId2()))
                        || subjects.contains(new Subject(x.getSb3(), x.getSbId3())))
                         &&(
                               x.getGroup().getGroupId() > -1
                             && x.getSbId1() > -1
                             && x.getSbId2() > -1
                             && x.getSbId3() > -1
                         );
            }
            
        }
    }

    /**
     * Bu metod Subject lardan biri o'chirilganda Student klasidaki subjectlarni
     * to'rilab chiqadi yani ochirilgandan keyin student ning sbId1,sbId2,sbId3
     * lari o'zgartradi chunki bu endi bittaga kam bo'lishi kerak
     *
     * @param i bu qaysi element o'chirilgan bo'lsa shundan boshab  sinxronlashtiradi
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
    
    public void synchronizSelectedBlockTestId() {
       if(selectedBocktestId>=Data.bockTests.size()) selectedBocktestId--;
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
    
    
    /**
     * Blocktestni yaratishda Subjectni tanlovini hosil qilganda kerak buladigan enum
     */
    public static enum BlocktestCreationType{

        /**
         * Hech bo'lmaganda bittsi to'g'ri kelsa true qayatarish uchun
         */
         ONE,

        /**
         *Faqat hammasi to'g'ri kelsa true qaytarish uchun
         */
        EVRY,
        
        /**
         * NOMALUMARNI Olish uchun ishlatiladi
         */
        NOMALUMNI_OL,
        
        /**
         * NOMALUMARNI Olmaslik uchun ishlatiladi
         */
        NOMALUMNI_OLMA;
    }
}
