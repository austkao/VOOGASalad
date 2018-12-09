package editor.interactive;

import editor.EditorManager;
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
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class InputEditor extends EditorSuper {
    private static final int DEFAULT_NUM_TABS = 4;

    private List<TextField> userInputs;
    private List<ObservableList> inputTypes;
    private List<ScrollablePaneNew> myScrolls;
    private List<HashMap<String,String>> bindings;
    private TabPane tabs;
    private int currentTabId;


    public InputEditor(EditorManager em){
        super(new Group(), em);
        currentTabId = 1;
        bindings = new ArrayList<>();
        userInputs = new ArrayList<>();
        inputTypes = FXCollections.observableArrayList();
        myScrolls = new ArrayList<>();
        tabs = new TabPane();
        tabs.getTabs().addAll(makeTabs());
        tabs.setTabMinWidth(100.0);
//        myScroll = new ScrollablePaneNew(200.0,200.0);
//        myScroll.setMaxHeight(150.0);
//        myScroll.setMaxWidth(150.0);
//        makeVBox1();
//        setRequirements();
//        Button remove = myRS.makeStringButton("remove input",Color.BLACK,true,Color.WHITE,20.0,650.0,100.0,150.0,50.0);
//        remove.setOnMouseClicked(e ->
//                myScroll.removeItem());//TODO: REMOVE FROM INPUTTYPES AS WELL
        Button test = myRS.makeStringButton("Show Bindings",Color.BLACK,true,Color.WHITE,20.0,650.0,80.0,150.0,50.0);
        test.setOnMouseClicked(e -> {
                    getBindings();
                    System.out.println(bindings);
                });
        Button save = myRS.makeStringButton("Save",Color.BLACK,true,Color.WHITE,20.0,650.0,150.0,150.0,50.0);
        save.setOnMouseClicked(e -> createSaveFile());
        root.getChildren().addAll(tabs,test, save);
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
//=======
//        myScroll = new ScrollablePaneNew(200.0,200.0, 520, 600);
//        myScroll.setMaxHeight(150.0);
//        myScroll.setMaxWidth(150.0);
//        makeVBox1();
//        userInput.setOnKeyPressed(e ->{
//            if(e.getCode() == KeyCode.ENTER){
//                addItemtoScroll();
//            } });
//        v.setLayoutX(100.0);
//        v.setLayoutY(50.0);
//        //setRequirements();
//        Button remove = myRS.makeStringButton("remove input",Color.BLACK,true,Color.WHITE,20.0,600.0,300.0,150.0,50.0);
//        remove.setOnMouseClicked(e -> myScroll.removeItem());
//        root.getChildren().addAll(remove);
//>>>>>>> DataProcessor:src/main/java/com.yeet.editor/src/editor/interactive/InputEditor.java
    }

    private Pane generateContent(){
        Pane p = new Pane();


        //setRequirements();
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

//    private void setRequirements(){
////        inputTypes.add("UP");
////        inputTypes.add("DOWN");
////        inputTypes.add("LEFT");
////        inputTypes.add("RIGHT");
//        myScroll.addItem(new InputItem(new Text("UP")));
//        myScroll.addItem(new InputItem(new Text("DOWN")));
//        myScroll.addItem(new InputItem(new Text("LEFT")));
//        myScroll.addItem(new InputItem(new Text("RIGHT")));
//    }

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
        for(int i = 0; i < DEFAULT_NUM_TABS; i++) {
            bindings.add(new HashMap<>());
            for (Scrollable s : myScrolls.get(i).getItems()) {
                String move = s.getButton().getText();
                Text keyText = (Text) s.getButton().getGraphic();
                String key = keyText.getText();
                bindings.get(i).put(move, key);
            }
        }
        return bindings;
    }

    private TextField createUserCommandLine() {
        TextField t = new TextField();
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

    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        TreeSet<String> moves = new TreeSet<>();
        for(HashMap<String, String> bindingsMap : getBindings()) {
            if(bindingsMap.isEmpty()) {
                continue;
            }
            for(String move : bindingsMap.keySet()) {
                move.replace("\n","").replace("\r","");
                String key = bindingsMap.get(move);
                key.replace("\n","").replace("\r","");
                moves.add(move);
                data.putIfAbsent(move, new ArrayList<>());
                data.get(move).add(key);
                System.out.println(move);
                System.out.println(bindingsMap.get(move));
            }
        }
        structure.put("input", new ArrayList<>(moves));
        File save = Paths.get(myEM.getGameDirectoryString(), "inputsetuptest.xml").toFile();
        generateSave(structure, data, save);
    }
}
