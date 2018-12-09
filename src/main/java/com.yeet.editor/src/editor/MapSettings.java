package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import renderer.external.Structures.SliderBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MapSettings {
    private Stage dialog;
    private Scene myScene;
    private Group root;
    private Consumer<Double> consumerG;
    private Consumer<Double> consumerF;
    private Consumer<Double> consumerT;
    private RenderSystem rs;
    private VBox v1;

    public MapSettings(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        root = new Group();
        myScene = new Scene(root);
        rs = new RenderSystem();
        initConsumers();
        buildScene();
    }

    public void setScene(){
        dialog.setTitle("Map Settings");
        dialog.setScene(myScene);
        dialog.show();
    }

    private void buildScene(){
        v1 = new VBox(5);
        v1.getChildren().addAll(initSliderBoxes());
        v1.setMinWidth(200.0);
        root.getChildren().add(v1);
    }

    private void initConsumers(){
        consumerG = (x) -> System.out.println(x);
        consumerF = (x) -> System.out.println(x);
        consumerT = (x) -> System.out.println(x);
    }

//    private void chooseBGM(){
//        File musicFile = new File(myEM.getGameDirectoryString()+"/data/bgm");
//        ListView<String> musicList = myRS.makeDirectoryFileList(musicFile, false);
//        Stage edit = new Stage();
//        edit.setScene(new Scene(new Group(musicList)));
//        musicList.setOnMouseClicked(e -> {
//            myBGMFileName = musicList.getSelectionModel().getSelectedItem();
//            myBGM.setText(myBGMFileName);
//            edit.close();
//        });
//        edit.show();
//    }

//    private void initButtons() {
//        Button myBGMButton = myRS.makeStringButton("Set Background Music", Color.BLACK, true, Color.WHITE,
//                20.0, 800.0, 650.0, 300.0, 50.0);
//        myBGMButton.setOnMouseClicked(e -> chooseBGM());
//    }

    private List<SliderBox> initSliderBoxes(){
        SliderBox gravity = rs.makeSlider("Gravity",1.0,consumerG,0.0,0.0,400.0);
        gravity.getSlider().setMin(-1.0);
        gravity.getSlider().setMax(5.0);
        SliderBox friction = rs.makeSlider("Friction",25.0,consumerF,0.0,0.0,400.0);
        friction.getSlider().setMin(0.0);
        friction.getSlider().setMax(50.0);
        SliderBox terminal = rs.makeSlider("Terminal Velocity",25.0,consumerT,0.0,0.0,400.0);
        terminal.getSlider().setMin(0.0);
        terminal.getSlider().setMax(50.0);
        List<SliderBox> s = new ArrayList<>();
        s.add(gravity);
        s.add(friction);
        s.add(terminal);
        return s;
    }
}
