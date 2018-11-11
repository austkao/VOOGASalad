package messenger.external;

import javafx.scene.input.KeyCode;

/** More specific {@code InputEvent} for keyboard input
 *  @author bpx
 */
public class KeyInputEvent extends InputEvent{

    private KeyCode myCode;

    /** Creates a new {@code KeyInputEvent} based on a key press
     *  @param keyCode The {@code KeyCode} for the pressed key
     */
    public KeyInputEvent(KeyCode keyCode){
        myCode = keyCode;
    }

    /** Returns a copy of the {@code KeyCode} stored in the {@code InputEvent}*/
    public KeyCode getKey(){
        return KeyCode.getKeyCode(myCode.getName());
    }

    /** Returns the {@code String} representation of the key input */
    @Override
    public String getName() {
        return myCode.getName();
    }
}
