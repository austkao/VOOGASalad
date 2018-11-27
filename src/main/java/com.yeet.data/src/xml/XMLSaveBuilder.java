package xml;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import renderer.external.RenderSystem;

/**
 * Example of how to create a new xml data file from a game being saved through the game editor.
 * May act as a superclass
 * @author ak457
 */

public class XMLSaveBuilder implements Saver {
    private static final String RESOURCE_PATH = "C:/Users/nitsu/IdeaProjects/CS308/voogasalad_yeet/src/main/java/com.yeet.main/resources";

    private Document saveDocument;
    private RenderSystem renderSys;

    public XMLSaveBuilder(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data, File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            saveDocument = dBuilder.newDocument();
            if (file != null) {
                Element root = saveDocument.createElement("VOOGASalad");
                saveDocument.appendChild(root);
                Attr authorAttribute = saveDocument.createAttribute("author");
                authorAttribute.setValue("yeet");
                root.setAttributeNode(authorAttribute);
                createElementList(root, structure, data);
                generateFile(file.getPath());
            } else {
                throw new IOException("Cannot determine new file");
            }
        } catch (ParserConfigurationException | IOException e) {
            System.out.println("Cannot initialize save file");
        }
    }

    private void createElementList(Element root, HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data) {
        int maxSize = 0;
        for(String s : data.keySet()) {
            if(data.get(s).size() > maxSize) {
                maxSize = data.get(s).size();
            }
        }
        for(int i = 0; i < maxSize; i++) {
            for(String elementTag : structure.keySet()) {
                Element tag = saveDocument.createElement(elementTag);
                root.appendChild(tag);
                for(String attributeTag : structure.get(elementTag)) {
                    Attr tagAttribute = saveDocument.createAttribute(attributeTag);
                    if(data.containsKey(attributeTag)) {
                        tagAttribute.setValue(data.get(attributeTag).get(i));
                    }
                    tag.setAttributeNode(tagAttribute);
                }
            }
        }
    }

    public void generateFile(String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(saveDocument);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException e) {
            System.out.println("Cannot create save file");
        } catch (TransformerException f) {
            System.out.println("Cannot create save file");
        }
    }
}