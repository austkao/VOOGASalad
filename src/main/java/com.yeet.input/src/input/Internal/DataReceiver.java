package input.Internal;

import xml.XMLParser;

import java.io.File;

/**
 * Class for setting up the files needed for key input mappings and combo mappings
 */
public class DataReceiver {

    XMLParser myParser;

    public DataReceiver(File GameDir){
        myParser = new XMLParser(GameDir);
    }
}
