package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Parser {

    private Map<String, ArrayList<String>> attackMapping;
    private TimeHandler timer;
    DataReceiver DR;

    public Parser(File gameDir){
        DR = new DataReceiver(gameDir);
        setUpMapping();
        timer = new TimeHandler(DR.getCombos());

    }


    /**
     * Parsing for handling combos (NOT IMPLEMENTED CORRECTLY)
     * @param q
     * @return
     *
     */
    public List<String> parse(Queue<KeyInputEvent> q) throws Exception {
        var output = timer.comboHandler(q);
        List<String> parsed = new ArrayList<>();
        for(String o :output){
            if(o.length()>1){
                parsed.add(o);
            }
            else{
                try{
                    parsed.add(attackMapping.get(o).get(0)); //Get the first elemtent of the value (an arraylist)
                }
                catch(Exception e){
                    e.printStackTrace();
                    throw new Exception();
                }
            }
        }
        return parsed;
    }

    /**
     Obtains the key mappings from the Data Reciever
     */
    private void setUpMapping(){
        attackMapping = DR.getKeys();
    }

    public String parse(KeyInputEvent e){
        return attackMapping.get(e.getKey().getChar()).get(0);
    }
}
