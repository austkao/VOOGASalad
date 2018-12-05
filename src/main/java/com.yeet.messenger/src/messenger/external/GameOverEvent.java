package messenger.external;

public class GameOverEvent extends Event{

    private int winnerID;

    public GameOverEvent(int winnerID){
        super();
        this.winnerID = winnerID;
    }

    @Override
    public String getName() {
        return "Game Over Event: Player "+winnerID+" wins!";
    }

    public int getWinnerID(){
        return winnerID;
    }
}
