package messenger.external;

import java.util.List;

public class GameStartEvent extends Event{

    private List<Integer> botList;

    public GameStartEvent(List<Integer> botList){
        this.botList = botList;
    }

    @Override
    public String getName() {
        return("Game Start Event");
    }

    /** Returns the ID of the bot players */
    public List<Integer> getBots(){
        return botList;
    }
}
