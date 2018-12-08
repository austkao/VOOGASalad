package input.Internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ComboFactory {

    private Map<String, ArrayList<String>> myCombos;
    private Node Tree;
    private int counter;
    public ComboFactory(Map<String, ArrayList<String>> combos){
        myCombos = combos;
        Tree = new ComboNode("START");
        counter = 0;

    }


    /**
     * Creates a tree of all possible combos
     * Idea: The keys of the
     * @return
     */
    public Node createTree(){
        for(String combo : myCombos.keySet()){
            var comboList = new ArrayList<>(Arrays.asList(combo.split("")));
            addChildLoop(Tree, comboList, combo);
        }
        return Tree;
    }


    private void addChildLoop(Node root, List<String> comboList, String combo){
        if(comboList.size() == 0){
            root.addChild(new LeafNode(myCombos.get(combo).get(0)));
            return;
        }
        Node newChild;
        String let = comboList.remove(0);

        if(!root.hasChild(let)){ // so long as that child doesn't already exist
            newChild = new ComboNode(let);
            root.addChild(newChild);
        }
        else{
            newChild = root.getChild(let);
        }
        addChildLoop(newChild, comboList, combo);
    }
}
