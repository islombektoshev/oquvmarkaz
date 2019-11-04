package wordProccesing.wordReader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Islom
 */
public class WordReader {

    static Document doc = null;

    static DocumentBuilder builder;

    static {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WordReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void read(File f) {
        try {
            doc = builder.parse(f);
            Element root = doc.getDocumentElement();
            Element pkg_part2 = (Element) root.getElementsByTagName("pkg:part").item(1);
            Element pkg_xmlData = (Element) pkg_part2.getElementsByTagName("pkg:xmlData").item(0);
            Element w_doc = (Element) pkg_part2.getElementsByTagName("w:document").item(0);
            Element w_body = (Element) pkg_part2.getElementsByTagName("w:body").item(0);
            
        } catch (SAXException ex) {
            Logger.getLogger(WordReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static enum ReadType {

        READ_FROM_TABLE,
        READ_WITH_LINE
    }
}
