package input.Internal;

import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
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

    public List<Map<String, ArrayList<String>>> getKeys(){
        List<Map<String, ArrayList<String>>> allInputs = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            allInputs.add(myParser.parseFileForElement("input"+Integer.toString(i+1))); //Collect the keyInputs for ALL of the players

        }
        //inputKeys = myParser.parseFileForElement("input");
        return allInputs;

    }

    public Map<String, ArrayList<String>> getCombos(){
        List<Map<String, ArrayList<String>>> allCombos = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            allCombos.add(myParser.parseFileForElement("combo"+ Integer.toString(i+1))); //Collect the keyInputs for ALL of the players
        }
        comboKeys = myParser.parseFileForElement("combo");
        return comboKeys;
    }

}
