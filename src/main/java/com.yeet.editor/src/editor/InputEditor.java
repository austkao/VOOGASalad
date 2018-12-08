package editor;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class InputEditor extends EditorSuper {

    public InputEditor(Group root, EditorManager em){
        super(root,em);

    }
//    public void makeVBox1(){
//        v = new VBox(20.0);
//
//
//        userInput = createUserCommandLine();
//        inputDisplay = new ListView<>();
//        inputDisplay.setMaxHeight(200.0);
//        userInput.setOnKeyPressed(e ->{
//            if(e.getCode() == KeyCode.ENTER){
//                addInputItem();
//            } });
//        v.getChildren().addAll(userInput,inputDisplay);
//    }
//
//    private void addInputItem(){
//        String text = userInput.getText();
//        if(text.indexOf(" ") >= 0 || text.indexOf("_") >= 0 || inputTypes.contains(text)){
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//            errorAlert.setHeaderText("Input not valid");
//            errorAlert.setContentText("Blank spaces, underscores, and repeat inputs are invalid");
//            errorAlert.showAndWait();
//            userInput.clear();
//        }else {
//            userInput.clear();
//            inputTypes.add(text);
//            inputDisplay.setItems(inputTypes);
//        }
//    }
//
//    public Set<String> getInputTypes(){
//        Set<String> ret = new HashSet<>(inputTypes);
//        return ret;
//    }public TextArea createUserCommandLine() {
//        TextArea input = new TextArea();
//        input.setPrefWidth(200);
//        input.setPrefHeight(50);
//        return input;
//    }
//

}
