package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Parser {

    private ComboHandler handler;

    public Parser(File gameDir){
        handler = new ComboHandler(gameDir); //pass in the combos to the timeHandler

    }


    /**
     * Parsing for handling combos (NOT IMPLEMENTED CORRECTLY)
     * @param q
     * @return
     *
     */
    public Map<Integer, List<String>> parse(List<KeyInputEvent> q) throws Exception {
        var output = handler.inputHandler(q);

        return output;
    }

    public void resetKeys(){
        handler.setUpMapping();
    }

}
