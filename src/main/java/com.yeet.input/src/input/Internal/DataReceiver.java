package input.Internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import xml.XMLParser;

public class DataReceiver {
    private HashMap<String, ArrayList<String>> inputKeys;

    public DataReceiver(File file) {
        XMLParser parser = new XMLParser(file);
        inputKeys = parser.parseFileForElement("input");
    }
}
