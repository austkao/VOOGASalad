package input.Internal;

import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


/**
 * Class for setting up the files needed for key input mappings and combo mappings
 */
public class DataReceiver {

    XMLParser myParser;
    //Holds our key mappings. All of the values will be an arraylist of size 1
    private Map<String, ArrayList<String>> inputKeys;
    private Map<String, ArrayList<String>> comboKeys;

    public DataReceiver(File GameDir) {
        myParser = new XMLParser(new File(GameDir.getPath() + "/inputsetup.xml"));
    }

    public Map<String, ArrayList<String>> getKeys(){
        inputKeys = myParser.parseFileForElement("input");
        return inputKeys;

    }

    public Map<String, ArrayList<String>> getCombos(){
        comboKeys = myParser.parseFileForElement("combo");
        return comboKeys;
    }

}
