package xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import renderer.external.RenderSystem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class parses an xml file for the information wanted by a user.
 * There is a validation check for a VOOGASalad tag in the top-level nodes of the xml file.
 * @author ak457
 */
public class XMLParser implements Parser {

    private Document xmlDocument;
    private RenderSystem renderSys;

    public XMLParser(File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            xmlDocument = doc;
            NodeList docNodes = xmlDocument.getChildNodes();
            boolean verified = false;
            for(int i = 0; i < docNodes.getLength(); i++) {
                if(docNodes.item(i) instanceof Element) {
                    Element elem = (Element) docNodes.item(i);
                    if(elem.getTagName().equals("VOOGASalad")) {
                        verified = true;
                        System.out.println("success");
                    }
                }
            }
            if(!verified) {
                throw new IOException("No VOOGASalad tag");
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            System.out.println("An error has occurred during initialization.");
        }
    }

    public HashMap<String, ArrayList<String>> parseFileForElement(String element) {
        HashMap<String, ArrayList<String>> attributeMap = new HashMap<>();
        NodeList elemNodes = xmlDocument.getElementsByTagName(element);
        for(int i = 0; i < elemNodes.getLength(); i++) {
            Element elem = (Element) elemNodes.item(i);
            System.out.println(elem.getTagName());
            NamedNodeMap elemAttributes = elem.getAttributes();
            for (int j = 0; j < elemAttributes.getLength(); j++) {
                Node attributeNode = elemAttributes.item(j);
                if(attributeNode instanceof Attr) {
                Attr attribute = (Attr) attributeNode;
                attributeMap.putIfAbsent(attribute.getName(), new ArrayList<>());
                attributeMap.get(attribute.getName()).add(attribute.getValue());
                }
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