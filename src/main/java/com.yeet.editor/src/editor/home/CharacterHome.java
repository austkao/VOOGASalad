package editor.home;


import editor.EditorManager;
import editor.interactive.CharacterEditor;
import editor.interactive.InputEditor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;

public class CharacterHome extends EditorHome {
    private static final String PORTRAITS = "portraits/";
    private InputEditor inputEditor;

    public CharacterHome(EditorManager em){
        super(new Group(), em);
        setInputEditor();
        setEditor();
        Button input = getRender().makeStringButton("Edit Inputs",Color.BLACK,true,Color.WHITE,20.0,0.0,0.0,200.0,50.0);
        getMyBox().getChildren().add(input);
        input.setOnMouseClicked(e-> em.changeScene(inputEditor));
    }

    private void setInputEditor(){
        inputEditor = new InputEditor(new Group(), em);
        inputEditor.createBack(this);
    }

    public void setEditor(){
        myEditor = new CharacterEditor(new Group(),em, inputEditor);
        myEditor.createBack(this);
    }

    @Override
    public void createNewObject(String name) {

    }

    @Override
    protected void deleteDirectory(ButtonBase bb) {

    }


    public String toString(){
        return "Character Home";
    }

    public String getDir(){
        return PORTRAITS;
    }
}
