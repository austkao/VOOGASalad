package editor.interactive;

import editor.EditorConstant;
import editor.EditorManager;
import editor.EditorScreen;
import editor.home.CharacterHome;
import editor.home.MapHome;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import xml.XMLParser;
import xml.XMLSaveBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Editor super class
 * @author ob29
 */

public abstract class EditorSuper extends Scene implements EditorScreen {

    protected Group root;
    protected EditorManager myEM;
    protected RenderSystem myRS;
    protected EditorConstant myEC;
    protected boolean isSaved;
    protected Text saved;

    public EditorSuper(Group root, EditorManager em){
        super(root);
        this.root = root;
        myEM = em;
        myRS = new RenderSystem();
        Text t = createTitle();
        root.getChildren().add(t);
        isSaved = true;
        saved = myRS.makeText("Saved", true, 20, Color.BLACK, 600.0, 700.0);
    }

    /**
     * Creates back button to the editor landing page
     */
    public Button createBack(Scene scene){
        Button back = myRS.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,myEC.BACKBUTTONXPOSITION.getValue(),0.0,150.0,50.0);
        back.setOnMouseClicked(e -> {
            if(isSaved) {
                myEM.changeScene(scene);
                if(scene instanceof MapHome) {
                    MapHome home = (MapHome) scene;
                    home.updateScroll("stages");
                } else if(scene instanceof CharacterHome) {
                    CharacterHome home = (CharacterHome) scene;
                    home.updateScroll("characters");
                }
            } else {
                myRS.createErrorAlert("Not Allowed to Go Back", "Please save your changes first");
            }
        });
        root.getChildren().add(back);
        return back;
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

    public XMLParser loadXMLFile(File xmlFile) {
        try {
            if(xmlFile != null) {
                return new XMLParser(xmlFile);
            } else {
                throw new IOException("Cannot load file");
            }
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            return null;
        }
    }

    public void generateSave(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data, File xmlFile) {
        try {
            if(xmlFile != null) {
                new XMLSaveBuilder(structure, data, xmlFile);
            } else {
                throw new IOException("Invalid save location");
            }
        } catch (IOException e) {
            System.out.println("An error occurred during the save process");
        }
    }

    public abstract String toString();

    public Text createTitle() {
        return myRS.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }
}
