package xml;

import java.io.File;
import java.io.IOException;

public class XMLFinder {
    public File find(String name) {
        try {
            File temp = File.createTempFile(name, ".xml");
            return temp;
        } catch (IOException e) {
            System.out.println("Nope");
            return null;
        }
    }
}
