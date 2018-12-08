package editor;

import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;

public class InputEditor extends EditorSuper {


    VBox v;
    private TextArea userInput;
    private ListView<String> inputDisplay;
    private ObservableList<String> inputTypes;

    private Text keyShower;


    public InputEditor(Group root, EditorManager em){
        super(root,em);
        inputTypes = FXCollections.observableArrayList();
        inputDisplay = new ListView<>();
        inputDisplay.setMaxHeight(200.0);
        makeVBox1();
        v.setLayoutX(100.0);
        v.setLayoutY(50.0);
        setRequirements();
        keyShower = myRS.makeText("PRESS KEY", true, 20,
                Color.DARKTURQUOISE,  400.0, 50.0);
        this.setOnKeyPressed(e -> updateKey(e));
        root.getChildren().add(keyShower);

    }

    private void updateKey(KeyEvent e){
        keyShower.setText(e.getText());
    }

    private void setRequirements(){
        inputTypes.add("UP");
        inputTypes.add("DOWN");
        inputTypes.add("LEFT");
        inputTypes.add("RIGHT");
        inputDisplay.setItems(inputTypes);
    }

    @Override
    public String toString(){
        return "Input Editor";
    }


    private void makeVBox1(){
        v = new VBox(20.0);
        userInput = createUserCommandLine();
        inputDisplay = new ListView<>();
        inputDisplay.setMaxHeight(200.0);
        userInput.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addInputItem();
            } });
        v.getChildren().addAll(userInput,inputDisplay);
        root.getChildren().add(v);
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
            //System.out.println("text = " + text);
            for (int i = 0; i < inputTypes.size(); i++){
                System.out.println(inputTypes.get(i));
            }
        }
    }

    public Set<String> getInputTypes(){
        return new HashSet<>(inputTypes);
    }
    private TextArea createUserCommandLine() {
        TextArea input = new TextArea();
        input.setPrefWidth(200);
        input.setPrefHeight(50);
        return input;
    }


}
