package editor.interactive;


import editor.EditorManager;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameSettingsEditor extends EditorSuper {
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private Consumer<Double> consumerG;
    private Consumer<Double> consumerF;
    private Consumer consumer;
    //private Double Gravity



    public GameSettingsEditor(Group root, EditorManager em) {
        super(root,em);
        consumerG = (x) -> System.out.println(x);
        //consumerG.accept(x);
        consumerF = (x) -> System.out.println(x);
        //consumerF.accept("Java2s.com");
        consumer = (x) -> System.out.println(x);

//        inputTypes = FXCollections.observableArrayList();

        VBox vb1 = makeVBox1();
        vb1.setLayoutX(100.0);
        vb1.setLayoutY(100.0);
        VBox vb2 = makeVBox2();
        vb2.setLayoutX(700.0);
        vb2.setLayoutY(100.0);
        root.getChildren().addAll(vb1,vb2);
    }

    public VBox makeVBox1(){
        VBox vbox = new VBox(20.0);
        TextBox splashField = myRS.makeTextField(consumer,"Splash Screen",0.0,0.0,10.0,50.0);
        TextBox mainBGMField = myRS.makeTextField(consumer,"Main Background Music",0.0,0.0,10.0,50.0);
        List<String> options = Arrays.asList(DEFAULT_GAME_MODES);
        SwitchButton sb = myRS.makeSwitchButtons(options,false, Color.WHITE, Color.BLACK,
                8.0,200.0,200.0,400.0,50.0);

        vbox.getChildren().addAll(splashField,mainBGMField,sb);//userInput,inputDisplay);
        return vbox;
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
        return "GameSettingsEditor";
    }



}
