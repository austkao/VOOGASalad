package input.Internal;

import javafx.scene.input.KeyCode;
import messenger.external.KeyInputEvent;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    Map<KeyCode, String> attackMapping;
    public Parser(){
        attackMapping = new HashMap<>();
        attackMapping.put(KeyCode.A, "LEFT");
        attackMapping.put(KeyCode.S, "DOWN");
        attackMapping.put(KeyCode.W, "UP");
        attackMapping.put(KeyCode.D, "RIGHT");
    }


    public String parse(KeyInputEvent e){
        return attackMapping.get(e.getKey());
    }
}
