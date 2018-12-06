package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.*;

public class ComboHandler {

    private Map<String, ArrayList<String>> testCombos;

    private Map<String, ArrayList<String>> attackMapping;
    private static final double COMBO_THRESHOLD = 250;
    private Node comboTree;
    private DataReceiver DR;
    private ComboFactory CFactory;

    public ComboHandler(File gameDir){
        DR = new DataReceiver(gameDir);
        CFactory = new ComboFactory(DR.getCombos());
        setUpCombos();
        setUpMapping();
    }



    /**
     * Recursively parses through our tree of combos, and if the items in q are not a combo,
     * then they are assumed to be their own separate keys. Therefore, the method will just return
     * the parsed inputs
     *
     */
    public List<String> inputHandler(List<KeyInputEvent> q){
        List<String> output = new ArrayList<>();
        while(q.size()>0) {
            String possibleCombo = parseComboTree(comboTree, new ArrayList<KeyInputEvent>(q));
            output.add(possibleCombo);
            q.remove(0);
            System.out.println(possibleCombo);
        }




        //if(q.size() == 1){
        //    return;
        //}
        //String stringEvents = "";
        //for(KeyInputEvent k : q){
        //    stringEvents+=(k.getKey().getChar());
        //}
//
        //List output = new ArrayList<>();
        //for(String combo:testCombos.keySet()){
        //    if(stringEvents.toLowerCase().contains(combo.toLowerCase())){
        //        output.add(testCombos.get(combo).get(0));
        //        stringEvents = stringEvents.replace(combo, "");
        //    }
        //    }
        //for(String remaining: Arrays.asList(stringEvents.split(""))) {
        //    if(!remaining.equals("")){
        //        output.add(remaining);
        //    }
//
        //}
        //q.clear();
        return output;


    }

    private String parseComboTree(Node root,  List<KeyInputEvent> q){
        if(root.isAtEnd()){
            return root.getChildren().get(0).getKey(); //Returns the combo name (this is a leaf node);
        }
        String nextInput = q.remove(0).getName();
        if(root.hasChild(nextInput)){
            Node child = root.getChild(nextInput);
            parseComboTree(child, q);
        }
        else if(!root.hasChild(nextInput)){
            return ""; // No Possible combos
        }
        return null;

    }

    private void setUpCombos(){
        comboTree = CFactory.createTree();
        //TODO: When input is recieved, if it is a combo, navigate the tree to verify if it is a valid combo
    }

    /**
     Obtains the key mappings from the Data Reciever
     */
    private void setUpMapping(){
        attackMapping = DR.getKeys();
    }
}
