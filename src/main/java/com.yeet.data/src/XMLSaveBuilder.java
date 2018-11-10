import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Example of how to create a new XML data file from a game being saved through the game editor.
 * @author Austin Kao
 */

public class XMLSaveBuilder {
    public void createNewXML(String filePath, String game) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document saveDocument = dBuilder.newDocument();
            Element root = saveDocument.createElement("game");
            saveDocument.appendChild(root);
            Attr authorAttribute = saveDocument.createAttribute("author");
            authorAttribute.setValue("yeet");
            root.setAttributeNode(authorAttribute);
            Attr nameAttribute = saveDocument.createAttribute("name");
            nameAttribute.setValue(game);
            root.setAttributeNode(nameAttribute);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(saveDocument);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            System.out.println("Cannot create save file");
        } catch (TransformerException f) {
            System.out.println("Cannot create save file");
        }
    }
}
