package input.Internal;

import messenger.external.KeyInputEvent;

import java.util.*;

public class Parser {

    private Map<String, String> attackMapping;
    private TimeHandler timer;
    public Parser(){
        attackMapping = new HashMap<>();
        attackMapping.put("A", "JAB");
        attackMapping.put("S", "DOWN");
        attackMapping.put("W", "UP");
        attackMapping.put("D", "RIGHT");
        attackMapping.put("AB", "SMASH");
        attackMapping.put("SHORYUKEN", "SHORYUKEN");
        attackMapping.put("SMASH", "SMASH");
        //attackMapping.put("KAMI", "KAMI");

        timer = new TimeHandler();

    }


    /**
     * Parsing for handling combos (NOT IMPLEMENTED CORRECTLY)
     * @param q
     * @return
     *
     */
    public List<String> parse(Queue<KeyInputEvent> q){
        var output = timer.comboHandler(q);
        List<String> parsed = new ArrayList<>();
        for(String o :output){
            parsed.add(attackMapping.get(o));
        }
        return parsed;
    }

    public String parse(KeyInputEvent e){
        return attackMapping.get(e.getKey().getChar());
    }
}
