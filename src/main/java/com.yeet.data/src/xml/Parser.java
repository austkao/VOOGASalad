package xml;

import java.util.HashMap;

/**
 * The Parser interface is intended for any file parser.
 * The methods encapsulated are used to extract the necessary information using the parser.
 * @author ak457
 */
public interface Parser {
    /**
     * Parse the file for the information contained within a certain element
     * @param element represents some category or object that file encapsulates
     * @return a HashMap containing the wanted information.
     * The keys should be the name of the properties the file stores.
     * The values should be the information the properties store.
     */
    HashMap parseFileForElement(String element);
}
