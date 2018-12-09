package editor;

import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.Structures.InputItem;
import renderer.external.Structures.ScrollablePaneNew;

import java.util.HashSet;
import java.util.Set;

public class InputEditor extends EditorSuper {


    private VBox v;
    private TextArea userInput;
    private ObservableList<String> inputTypes;
    private ScrollablePaneNew myScroll;


    public InputEditor(Group root, EditorManager em){
        super(root,em);
        inputTypes = FXCollections.observableArrayList();
        myScroll = new ScrollablePaneNew(200.0,200.0);
        myScroll.setMaxHeight(150.0);
        myScroll.setMaxWidth(150.0);
        makeVBox1();
        userInput.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addItemtoScroll();
            } });
        v.setLayoutX(100.0);
        v.setLayoutY(50.0);
        //setRequirements();
        Button remove = myRS.makeStringButton("remove input",Color.BLACK,true,Color.WHITE,20.0,600.0,300.0,150.0,50.0);
        remove.setOnMouseClicked(e -> myScroll.removeItem());
        root.getChildren().addAll(remove);
    }



    private void addItemtoScroll(){
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
            InputItem testItem = new InputItem(new Text(text));
            myScroll.addItem(testItem);
        }
    }







//    private void setRequirements(){
//        inputTypes.add(new Text("UP"));
//        inputTypes.add(new Text("DOWN"));
//        inputTypes.add(new Text("LEFT"));
//        inputTypes.add(new Text("RIGHT"));
//        //inputDisplay.setItems(inputTypes);
//    }



    private void makeVBox1(){
        v = new VBox(20.0);
        userInput = createUserCommandLine();

        userInput.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addItemtoScroll();
            } });
        v.getChildren().addAll(userInput,myScroll.getScrollPane());
        root.getChildren().add(v);
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

    @Override
    public String toString(){
        return "Input Editor";
    }


}
