package editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;
import java.util.*;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameplayEditor extends EditorSuper{
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private Consumer<Double> consumerG;
    private Consumer<Double> consumerF;
    private Consumer consumer;
    //private Double Gravity
    private ObservableList<String> inputTypes;
    private ListView<String> inputDisplay;
    private TextArea userInput;


    public GameplayEditor(Group root, EditorManager em) {
        super(root,em);
        consumerG = (x) -> System.out.println(x);
        //consumerG.accept(x);
        consumerF = (x) -> System.out.println(x);
        //consumerF.accept("Java2s.com");
        consumer = (x) -> System.out.println(x);

        inputTypes = FXCollections.observableArrayList();

        VBox vb1 = makeVBox1();
        vb1.setLayoutX(100.0);
        vb1.setLayoutY(100.0);
        VBox vb2 = makeVBox2();
        vb2.setLayoutX(700.0);
        vb2.setLayoutY(100.0);
        root.getChildren().addAll(vb1,vb2);
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
        SwitchButton sb = myRS.makeSwitchButtons(options,false, Color.WHITE, Color.BLACK,
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
        SliderBox gravity = myRS.makeSlider("Gravity",1.0,consumer,0.0,0.0,150.0);
        gravity.getSlider().setMin(-1.0);
        gravity.getSlider().setMax(5.0);
        SliderBox friction = myRS.makeSlider("Friction",25.0,consumer,0.0,0.0,150.0);
        friction.getSlider().setMin(0.0);
        friction.getSlider().setMax(50.0);
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
