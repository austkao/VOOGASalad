package renderer.external.Structures;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Scrollable;

public class InputItem implements Scrollable {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;



    private ToggleButton button;
    private RenderSystem rs;
    private Text keyBind;
    private Font myEmphasisFont;

    public InputItem(Text desc){
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT), DEFAULT_EMPHASIS_FONTSIZE);
        rs = new RenderSystem();
        initializeButton(desc);
        button.setOnKeyPressed(e -> addKeyBinding(e));

    }

    private void addKeyBinding(KeyEvent e){
        Text keyShower = new Text();
        keyShower.setText(e.getText());
        //myScroll.modifyItem(keyShower);
        setNodeGraphic(keyShower,"");
    }

    public void setNodeGraphic(Node key,String text){
        keyBind = (Text) key;
        keyBind.setFill(Color.WHITE);
        keyBind.setFont(myEmphasisFont);
        button.setGraphic(keyBind);
    }

    public Text getKeyBind(){
        return keyBind;
    }

    @Override
    public void initializeButton(Text desc) {
        button = new ToggleButton(desc.getText(),new Text());
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        button.setPrefWidth(150.0);
        button.setPrefHeight(50.0);
        keyBind = new Text(" ");
        button.setGraphic(keyBind);
        applyStyleAndEffect(button);
    }

    private void applyStyleAndEffect(ToggleButton t) {
        rs.styleButton(t);
        rs.buttonHoverEffect(t);
        t.selectedProperty().addListener((p, ov, nv) -> {
            selectEffect(t);
        });
    }

    private void selectEffect(ToggleButton b){
        if(b.isSelected()){
            DropShadow drop = new DropShadow(12.0,Color.BLUE);
            drop.setHeight(30.0);
            b.setEffect(drop);
        }else{
            b.setEffect(null);
        }
    }


    @Override
    public ToggleButton getButton() {
        return button;
    }

    @Override
    public ToggleButton getImageButton() {
        return null;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
