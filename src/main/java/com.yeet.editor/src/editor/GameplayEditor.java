package editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import messenger.external.KeyInputEvent;
import renderer.external.RenderSystem;
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.*;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameplayEditor extends EditorSuper{
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private Consumer consumer;
    private ObservableList<String> inputTypes;
    private ListView<String> inputDisplay;
    private TextArea userInput;


    public GameplayEditor(Group root, EditorManager em) {
        super(root,em);
        Text t = getRS().makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        inputTypes = FXCollections.observableArrayList();

        VBox vb1 = makeVBox1();
        vb1.setLayoutX(100.0);
        vb1.setLayoutY(100.0);
        root.getChildren().add(vb1);

        VBox vb2 = makeVBox2();
        vb2.setLayoutX(700.0);
        vb2.setLayoutY(100.0);
        root.getChildren().add(vb2);
    }

    public TextArea createUserCommandLine() {
        TextArea input = new TextArea();
        input.setPrefWidth(200);
        input.setPrefHeight(50);
        return input;
    }

    public VBox makeVBox1(){
        VBox vbox = new VBox(20.0);
        TextBox splashField = myRS.makeTextField(consumer,"Splash Screen",0.0,0.0,10.0,50.0);
        TextBox mainBGMField = myRS.makeTextField(consumer,"Main Background Music",0.0,0.0,10.0,50.0);
        List<String> options = Arrays.asList(DEFAULT_GAME_MODES);
        SwitchButton sb = myRS.makeSwitchButtons(options,false,
                Color.WHITE,
                Color.BLACK,
                8.0,200.0,200.0,400.0,50.0);
        userInput = createUserCommandLine();
        inputDisplay = new ListView<>();
        inputDisplay.setMaxHeight(200.0);
        userInput.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addInputItem();
            } });


        vbox.getChildren().addAll(splashField,mainBGMField,sb,userInput,inputDisplay);
        return vbox;
    }

    private void addInputItem(){
        String text = userInput.getText();
        if(text.indexOf(" ") >= 0 || text.indexOf("_") >= 0 || inputTypes.contains(text)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Blank spaces, underscores, and repeat inputs are invalid");
            errorAlert.showAndWait();
            userInput.clear();
        }else {
            userInput.clear();
            inputTypes.add(text);
            inputDisplay.setItems(inputTypes);
        }
    }

    public VBox makeVBox2(){
        VBox vbox = new VBox(10.0);
        SliderBox gravity = myRS.makeSlider("Gravity",consumer,0.0,0.0,150.0);
        SliderBox friction = myRS.makeSlider("Friction",consumer,0.0,0.0,150.0);
        vbox.getChildren().addAll(gravity,friction);
        return vbox;
    }






    public String toString(){
        return "GameplayEditor";
    }


    public Set<String> getInputTypes(){
        Set<String> ret = new HashSet<>(inputTypes);
        return ret;
    }
}
