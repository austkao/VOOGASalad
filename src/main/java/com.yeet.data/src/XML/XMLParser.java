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

/**
 * This class parses an XML file for the information wanted by a user.
 * There is a validation check for a VOOGASalad tag in the top-level nodes of the XML file.
 * @author ak457
 */
public class XMLParser {

    private Document xmlDocument;

    public XMLParser(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
            doc.getDocumentElement().normalize();
            xmlDocument = doc;
            NodeList docNodes = xmlDocument.getChildNodes();
            boolean verified = false;
            for(int i = 0; i < docNodes.getLength(); i++) {
                if(docNodes.item(i) instanceof Element) {
                    Element elem = (Element) docNodes.item(i);
                    if(elem.getTagName().equals("VOOGASalad")) {
                        verified = true;
                    }
                }
            }
            if(!verified) {
                throw new IOException("No VOOGASalad tag");
            }
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    public HashMap<String, ArrayList<String>> parseFileForElement(String element) {
        HashMap<String, ArrayList<String>> attributeMap = new HashMap<>();
        NodeList elemNodes = xmlDocument.getElementsByTagName(element);
        for(int i = 0; i < elemNodes.getLength(); i++) {
            if(elemNodes.item(i) instanceof Attr) {
                Attr attribute = (Attr) elemNodes.item(i);
                attributeMap.putIfAbsent(attribute.getName(), new ArrayList<>());
                attributeMap.get(attribute.getName()).add(attribute.getValue());
            }
        }
        return attributeMap;
    }

    public ArrayList<String> getAttributeValues(String elementTag, String attributeTag) {
        NodeList elementList = xmlDocument.getElementsByTagName(elementTag);
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i < elementList.getLength(); i++) {
            Element elem = (Element) elementList.item(i);
            NodeList elemNodes = elem.getChildNodes();
            for(int j = 0; j < elemNodes.getLength(); j++) {
                if(elemNodes.item(j) instanceof Attr) {
                    Attr attribute = (Attr) elemNodes.item(j);
                    if(attribute.getName().equals(attributeTag)) {
                        values.add(attribute.getValue());
                    }
                }
            }
        }
        return values;
    }
}