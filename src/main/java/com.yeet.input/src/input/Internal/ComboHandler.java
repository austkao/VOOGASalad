package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboHandler {

    private Map<String, ArrayList<String>> testCombos;

    private List<Map<String, String>> attackMapping;
    private static final double COMBO_THRESHOLD = 250;
    private List<Node> comboTrees;
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
    public Map<Integer, List<String>> inputHandler(List<KeyInputEvent> q){
        Map<Integer, List<String>> parsedInputs = new HashMap<>();

        int cnt = 0;
        for(Node playerTree : comboTrees){
            String possibleCombo = parseComboTree(playerTree, new ArrayList<>(q));
            if(possibleCombo != null && !possibleCombo.equals("")){
                var arr = new ArrayList();
                arr.add(possibleCombo);
                parsedInputs.put(cnt, arr);
            }
            cnt += 1;
        }

        // NOTE: We are assuming that since the timeframe for checking is so tiny,
        //a user will only be inputting either a combo or a noncombo
        if(parsedInputs.keySet().size() > 0){
            return parsedInputs;
        }

        cnt = 0;
        for(var playerMap : attackMapping){
            List<String> output = new ArrayList<>();
            for(KeyInputEvent input:q){
                if(playerMap.keySet().contains(input.getName())){
                    output.add(playerMap.get(input.getName()));
                }
            }
            parsedInputs.put(cnt, output);
            cnt += 1;
        }

        return parsedInputs;


    }


    private String parseComboTree(Node root,  List<KeyInputEvent> q){
        if(q.isEmpty()){
            return null;
        }
        if(root.isAtEnd()){
            return root.getChildren().get(0).getKey(); //Returns the combo name (this is a leaf node);
        }
        String nextInput = q.remove(0).getName();
        if(root.hasChild(nextInput)){
            Node child = root.getChild(nextInput);
            return parseComboTree(child, q);
        }
        else if(!root.hasChild(nextInput)){
            return null; // No Possible combos
        }
        return null;

    }

    private void setUpCombos(){
        comboTrees = CFactory.createTree();
        //TODO: When input is recieved, if it is a combo, navigate the tree to verify if it is a valid combo
    }

    /**
     Obtains the key mappings from the Data Reciever
     */
    public void setUpMapping(){
        attackMapping = DR.getKeys();
    }
}
