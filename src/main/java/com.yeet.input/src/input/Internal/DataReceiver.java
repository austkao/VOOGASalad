package input.Internal;

import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class for setting up the files needed for key input mappings and combo mappings
 */
public class DataReceiver {

    XMLParser myParser;
    //Holds our key mappings. All of the values will be an arraylist of size 1
    private Map<String, ArrayList<String>> inputKeys;
    private Map<String, ArrayList<String>> comboKeys;
    private int numPlayers;

    public DataReceiver(File GameDir) {

        myParser = new XMLParser(new File(GameDir.getPath() + "/inputsetup.xml"));
        var x = myParser.parseFileForAttribute("players", "players");
        numPlayers = Integer.parseInt(x.get(0));
    }

    public List<Map<String, String>> getKeys(){
        List<Map<String, String>> allInputs = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            Map<String, String> inputMap = reverse(myParser.parseFileForElement("input"+Integer.toString(i)));
            allInputs.add(inputMap); //Collect the keyInputs for ALL of the players

        }
        //inputKeys = myParser.parseFileForElement("input");
        return allInputs;

    }

    public List<Map<String, String>> getCombos(){
        List<Map<String, String>> allCombos = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            Map<String, String> comboMap = reverse(myParser.parseFileForElement("combo"+Integer.toString(i)));
            allCombos.add(comboMap); //Collect the keyInputs for ALL of the players
        }

        comboKeys = myParser.parseFileForElement("combo");
        return allCombos;
    }

    /**
     *
     * For the purpose of reversing the Map that is obtained from the data processor
     */
    public Map<String,String> reverse(HashMap<String,ArrayList<String>> map) {
        Map<String,String> rev = new HashMap<>();
        for(Map.Entry<String,ArrayList<String>> entry : map.entrySet())
            rev.put(entry.getValue().get(0), entry.getKey()); // reverse and remove the arrayList inside the map
        return rev;
    }

}
