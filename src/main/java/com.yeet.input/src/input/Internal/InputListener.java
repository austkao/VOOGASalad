package input.Internal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("TYPED");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("HELD");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("RELEASED");
    }
}
