package messenger.external;

import java.util.ArrayList;
import java.util.Queue;

public class GameOverEvent extends Event{

    private int winnerID;
    private Queue<Integer> rankList;

    public GameOverEvent(int winnerID, Queue<Integer> rankList){
        super();
        this.winnerID = winnerID;
        this.rankList = rankList;
    }

    @Override
    public String getName() {
        return "Game Over Event: Player "+winnerID+" wins!";
    }

    public int getWinnerID(){
        return winnerID;
    }

    public Queue<Integer> getRankList(){
        return rankList;
    }
}
