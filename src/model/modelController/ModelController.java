package model.modelController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import model.model.Data;
import model.model.Group;
import model.model.Student;
import model.model.Subject;
import org.xml.sax.SAXException;

/**
 *
 * @author Islom
 */
public class ModelController {
    
    static  {
      //  read();
    }

    public static void read() {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse("markazmalumotlari.xml");

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

                //ADD GROUP TO DATAMODEL
                Data.students.add(
                        Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                        new Student(
                                Integer.parseInt(atribut.getNamedItem("id").getNodeValue()),
                                atribut.getNamedItem("name").getNodeValue(),
                                atribut.getNamedItem("surename").getNodeValue(),
                                Data.groups.get(Integer.parseInt(atribut.getNamedItem("gr").getNodeValue())),
                                Integer.parseInt(atribut.getNamedItem("sb1").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("sb2").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("sb3").getNodeValue()),
                                Integer.parseInt(atribut.getNamedItem("gr").getNodeValue())
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
    
    public void deletSubject(int id){
        int size = Data.subjects.size();
        Data.subjects.remove(IdScanner.getIndexbyIdSubjet(id));
        for (int i = 0; i < size; i++) {
            
        }
    }
    
    
    
}
