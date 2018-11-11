package input.external;

import messenger.external.Event;

public class ActionEvent extends Event {

    private String name;

    public ActionEvent(String actionName){
        name = actionName;
    }

    @Override
    public String getName() {
        return name;
    }
}
