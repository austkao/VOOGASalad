package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import renderer.external.Structures.SliderBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MapSettings {
    private static final double[] DEFAULT_SETTINGS = {200.0,0.5,400.0};

    private Stage dialog;
    private Scene myScene;
    private Group root;
    private Consumer<Double> consumerG;
    private Consumer<Double> consumerF;
    private Consumer<Double> consumerT;
    private double gravity;
    private double friction;
    private double terminal;
    private RenderSystem rs;
    private VBox v1;
    private File stageMusic;

    public MapSettings(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        root = new Group();
        myScene = new Scene(root);
        rs = new RenderSystem();
        initPhysicsValues();
        initConsumers();
        buildScene();
    }

    private void initPhysicsValues(){
        gravity = DEFAULT_SETTINGS[0];
        friction = DEFAULT_SETTINGS[1];
        terminal = DEFAULT_SETTINGS[2];
    }

    public void setScene(){
        dialog.setTitle("Map Settings");
        dialog.setScene(myScene);
        dialog.show();
    }

    private void buildScene(){
        v1 = new VBox(5);
        v1.getChildren().addAll(initSliderBoxes());
        v1.getChildren().add(makeMusicBox());
        v1.setMinWidth(200.0);
        root.getChildren().add(v1);
    }

    private void initConsumers(){
        consumerG = (x) -> gravity = x;
        consumerF = (x) -> friction = x;
        consumerT = (x) -> terminal = x;
    }

    public double[] getPhysicsValues(){
        return new double[]{gravity,friction,terminal};
    }

    public File getStageMusic(){
        return stageMusic;
    }

    private HBox makeMusicBox(){
        HBox musicbox = new HBox(10);
        Button pickMusic = rs.makeStringButton("pick main music", Color.BLACK,true, Color.WHITE,15.0,60.0,200.0,150.0,40.0);
        Text musicLabel = new Text();
        pickMusic.setOnMouseClicked(e -> {
            getMusicFile();
            musicLabel.setText(stageMusic.getName());
        });
        musicbox.getChildren().addAll(pickMusic,musicLabel);
        return musicbox;
    }

    private void getMusicFile(){
        FileChooser fileChooser = rs.makeFileChooser("audio");
        stageMusic = fileChooser.showOpenDialog(myScene.getWindow());
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
        gravity.getSlider().setMax(800.0);
        SliderBox friction = rs.makeSlider("Friction",25.0,consumerF,0.0,0.0,400.0);
        friction.getSlider().setMin(0.0);
        friction.getSlider().setMax(5);
        SliderBox terminal = rs.makeSlider("Terminal Velocity",25.0,consumerT,0.0,0.0,400.0);
        terminal.getSlider().setMin(0.0);
        terminal.getSlider().setMax(600.0);
        List<SliderBox> s = new ArrayList<>();
        s.add(gravity);
        s.add(friction);
        s.add(terminal);
        return s;
    }
}
