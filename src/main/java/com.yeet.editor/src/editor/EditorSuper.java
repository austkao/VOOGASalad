package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import xml.XMLParser;
import xml.XMLSaveBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Editor super class
 * @author ob29
 */

public abstract class EditorSuper extends Scene{

    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources";

    protected Group root;
    protected EditorManager myEM;
    protected RenderSystem myRS;
    protected EditorConstant myEC;

    public EditorSuper(Group root, EditorManager em){
        super(root);
        this.root = root;
        myEM = em;
        myRS = new RenderSystem();
        Text t = myRS.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().add(t);
    }

    /**
     * Creates back button to the editor landing page
     */
    public void createBack(Scene scene){
        Button back = myRS.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,myEC.BACKBUTTONXPOSTION.getValue(),0.0,150.0,50.0);
        back.setOnMouseClicked(e -> myEM.changeScene(scene));
        root.getChildren().add(back);
    }

    /**
     * Creates save button to the editor landing page
     */
    public void createSave(){
        Button save = myRS.makeStringButton("Save", Color.BLACK,true,Color.WHITE,30.0,800.0,0.0,150.0,50.0);
        //TODO figure out save functionality
        //save.setOnMouseClicked(e -> em.setEditorHomeScene());
        root.getChildren().add(save);
    }

    public HashMap<String, ArrayList<String>> loadXMLFile(String tag) {
        try {
            FileChooser loadFileChooser = myRS.makeFileChooser("xml");
            loadFileChooser.setTitle("Save File As");
            Path filePath = Paths.get(System.getProperty("user.dir"));
            File defaultFile = new File(filePath+RESOURCE_PATH);
            loadFileChooser.setInitialDirectory(defaultFile);
            File file = loadFileChooser.showOpenDialog(new Stage());
            if(file != null) {
                XMLParser parser = new XMLParser(file);
                return parser.parseFileForElement(tag);
            } else {
                throw new IOException("Cannot load file");
            }
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            return new HashMap<>();
        }
    }

    public void generateSave(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data) {
        try {
            FileChooser fileChooser = myRS.makeFileChooser("xml");
            fileChooser.setTitle("Save File As");
            File defaultFile = myEM.getGameDirectory();
            fileChooser.setInitialDirectory(defaultFile);
            File file = fileChooser.showSaveDialog(new Stage());
            if(file != null) {
                new XMLSaveBuilder(structure, data, file);
            } else {
                throw new IOException("Invalid save location");
            }
        } catch (IOException e) {
            System.out.println("An error occurred during the save process");
        }
    }
}
