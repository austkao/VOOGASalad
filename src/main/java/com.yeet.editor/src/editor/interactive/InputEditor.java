package editor.interactive;

import editor.EditorManager;
import editor.interactive.EditorSuper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.Scrollable;
import renderer.external.Structures.InputItem;
import renderer.external.Structures.ScrollablePaneNew;

import java.util.*;

public class InputEditor extends EditorSuper {
    private static final int DEFAULT_NUM_TABS = 4;

    private List<TextArea> userInputs;
    private List<ObservableList> inputTypes;
    private List<ScrollablePaneNew> myScrolls;
    private List<HashMap<String,String>> bindings;
    private TabPane tabs;
    private int currentTabId;


    public InputEditor(Group root, EditorManager em){
        super(root,em);
        currentTabId = 1;
        bindings = new ArrayList<HashMap<String,String>>();
        userInputs = new ArrayList<>();
        inputTypes = FXCollections.observableArrayList();
        myScrolls = new ArrayList<>();
        tabs = new TabPane();
        tabs.getTabs().addAll(makeTabs());
        tabs.setTabMinWidth(100.0);
        setRequirements();

        Button test = myRS.makeStringButton("show bindings",Color.BLACK,true,Color.WHITE,20.0,650.0,80.0,150.0,50.0);
        test.setOnMouseClicked(e -> {
                    getBindings();
                    System.out.println(bindings);
                });

        root.getChildren().addAll(tabs,test);
    }



    private List<Tab> makeTabs(){
        List<Tab> tablist = new ArrayList<>();
        for(int i = 0; i < DEFAULT_NUM_TABS; i++) {
            Tab t = new Tab();
            t.setText("Player " + (i + 1));
            t.setId(Integer.toString(i+1));
            t.setOnSelectionChanged(e ->
                    currentTabId = Integer.parseInt(t.getId()));
            t.setContent(generateContent());
            tablist.add(t);
        }
        return tablist;

    }

    private Pane generateContent(){
        Pane p = new Pane();
        Button remove = myRS.makeStringButton("remove input",Color.BLACK,true,Color.WHITE,20.0,650.0,100.0,150.0,50.0);
        remove.setOnMouseClicked(e ->
                myScrolls.get(currentTabId-1).removeItem());//TODO: REMOVE FROM INPUTTYPES AS WELL
        ObservableList l = FXCollections.observableArrayList();
        inputTypes.add(l);
        p.getChildren().addAll(remove,makeVBox());
        return p;
    }


    private void addItemtoScroll(){
        String text = userInputs.get(currentTabId-1).getText();
        if(text.indexOf(" ") >= 0 || text.indexOf("_") >= 0 || inputTypes.contains(text)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Blank spaces, underscores, and repeat inputs are invalid");
            errorAlert.showAndWait();
            userInputs.get(currentTabId-1).clear();
        }else {
            userInputs.get(currentTabId-1).clear();
            InputItem testItem = new InputItem(new Text(text));
            inputTypes.get(currentTabId-1).add(text);
            myScrolls.get(currentTabId-1).addItem(testItem);
        }
    }

    private void setRequirements(){
        for(int i = 0; i < DEFAULT_NUM_TABS; i++){
            inputTypes.get(i).add("UP");
            inputTypes.get(i).add("DOWN");
            inputTypes.get(i).add("LEFT");
            inputTypes.get(i).add("RIGHT");
            myScrolls.get(i).addItem(new InputItem(new Text("UP")));
            myScrolls.get(i).addItem(new InputItem(new Text("DOWN")));
            myScrolls.get(i).addItem(new InputItem(new Text("LEFT")));
            myScrolls.get(i).addItem(new InputItem(new Text("RIGHT")));
        }

    }

    private VBox makeVBox(){
        VBox v = new VBox(20.0);
        ScrollablePaneNew sp = new ScrollablePaneNew(200.0,200.0,300.0,500.0);
        sp.setMaxHeight(150.0);
        sp.setMaxWidth(150.0);
        myScrolls.add(sp);
        v.getChildren().addAll(createUserCommandLine(),sp.getScrollPane());
        v.setLayoutX(100.0);
        v.setLayoutY(80.0);
        return v;
    }


    public List<ObservableList> getInputTypes(){
        return inputTypes;
    }

    public List<HashMap<String,String>> getBindings(){
        bindings.clear();
        for(int i = 0; i < DEFAULT_NUM_TABS; i++) {
            for (Scrollable s : myScrolls.get(i).getItems()) {
                String move = s.getButton().getText();
                Text keyText = (Text) s.getButton().getGraphic();
                String key = keyText.getText();
                bindings.get(i).put(move, key);
            }
        }
        return bindings;
    }

    private TextArea createUserCommandLine() {
        TextArea t = new TextArea();
        t.setPrefWidth(200);
        t.setPrefHeight(50);
        t.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addItemtoScroll();
            } });
        userInputs.add(t);

        return t;
    }

    @Override
    public String toString(){
        return "Input Editor";
    }


}
