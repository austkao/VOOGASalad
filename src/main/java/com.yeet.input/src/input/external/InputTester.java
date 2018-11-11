package input.external;

import input.Internal.InputListener;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
public class InputTester extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        JFrame ablak = new JFrame("Snake game");
        ablak.setVisible(true);
        ablak.setSize(new Dimension(600,600));
        ablak.setFocusable(true);
        ablak.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InputListener IL = new InputListener();
        ablak.addKeyListener(IL);
        ablak.setVisible(true);
    }
}
