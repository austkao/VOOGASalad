package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.List;

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
    public List<String> parse(List<KeyInputEvent> q) throws Exception {
        var output = handler.inputHandler(q);
        //List<String> parsed = new ArrayList<>();
        //for(String o :output){
        //    if(o.length()>1){
        //        parsed.add(o);
        //    }
        //    else{
        //        try{
        //            parsed.add(attackMapping.get(o).get(0)); //Get the first elemtent of the value (an arraylist)
        //        }
        //        catch(Exception e){
        //            e.printStackTrace();
        //            throw new Exception();
        //        }
        //    }
        //}
        return output;
    }


    //public String parse(KeyInputEvent e){
        //return attackMapping.get(e.getKey().getChar()).get(0);
    //}
}
