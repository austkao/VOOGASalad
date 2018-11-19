package XML;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class XMLParser {

    private Document xmlDocument;

    public XMLParser(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
            doc.getDocumentElement().normalize();
            xmlDocument = doc;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    public HashMap parseFile(Document doc) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        return map;
    }
}