package input.Internal;

import messenger.external.KeyInputEvent;

import java.util.*;

public class TimeHandler {

    private Map<String, ArrayList<String>> testCombos;

    private static final double COMBO_THRESHOLD = 250;

    public TimeHandler(Map<String, ArrayList<String>> combos){
        testCombos = combos;
    }



    public List<String> comboHandler(Queue<KeyInputEvent> q){
        //if(q.size() == 1){
        //    return;
        //}
        String stringEvents = "";
        for(KeyInputEvent k : q){
            stringEvents+=(k.getKey().getChar());
        }

        List output = new ArrayList<>();
        for(String combo:testCombos.keySet()){
            if(stringEvents.toLowerCase().contains(combo.toLowerCase())){
                output.add(testCombos.get(combo).get(0));
                stringEvents = stringEvents.replace(combo, "");
            }
            }
        for(String remaining: Arrays.asList(stringEvents.split(""))) {
            if(!remaining.equals("")){
                output.add(remaining);
            }

        }
        //q.clear();
        return output;


    }
}
