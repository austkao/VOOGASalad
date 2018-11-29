package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameplayEditor extends EditorSuper{
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private Consumer consumer;

    public GameplayEditor(Group root, EditorManager em) {
        super(root,em);
        RenderSystem myRenderSystem = myRS;
        Text t = myRenderSystem.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        VBox vb1 = makeVBox1();
        vb1.setLayoutX(100.0);
        vb1.setLayoutY(100.0);
        root.getChildren().add(vb1);

        VBox vb2 = makeVBox2();
        vb2.setLayoutX(700.0);
        vb2.setLayoutY(100.0);
        root.getChildren().add(vb2);
    }

    public VBox makeVBox1(){
        VBox vbox = new VBox(10.0);
        TextBox splashField = myRS.makeTextField(consumer,"Splash Screen",0.0,0.0,10.0,50.0);
        TextBox mainBGMField = myRS.makeTextField(consumer,"Main Background Music",0.0,0.0,10.0,50.0);
        List<String> options = Arrays.asList(DEFAULT_GAME_MODES);
        SwitchButton sb = myRS.makeSwitchButtons(options,false,
                Color.WHITE,
                Color.BLACK,
                8.0,200.0,200.0,400.0,50.0);
        vbox.getChildren().addAll(splashField,mainBGMField,sb);
        return vbox;
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


}
