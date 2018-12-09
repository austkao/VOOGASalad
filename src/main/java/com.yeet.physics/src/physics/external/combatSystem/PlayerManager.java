package physics.external.combatSystem;

import messenger.external.CombatActionEvent;
import messenger.external.GameOverEvent;
import physics.external.PhysicsSystem;

import java.util.*;

public class PlayerManager {
    private static final int INITIAL_ID = 0;
    Map<Integer, Player> playerMap;
    int numOfPlayers;
    int numOfAlivePlayers;
    Queue<Integer> ranking;
    int winnerID;

    public PlayerManager(int numOfPlayers){
        playerMap = new HashMap<>();
        ranking = new LinkedList<>();
        this.numOfPlayers = numOfPlayers;
        for(int id = INITIAL_ID; id < numOfPlayers; id++){
            playerMap.put(id, new Player(id));
        }
    }

    public Player getPlayerByID(int id){
        if(id >= INITIAL_ID + numOfPlayers)
            throw new RuntimeException(String.format("ID %d does not exist", id));
        return playerMap.get(id);
    }

    public void setToInitialStateByID(int id){
        if(id >= INITIAL_ID + numOfPlayers)
            throw new RuntimeException(String.format("ID %d does not exist", id));
        playerMap.get(id).setToInitialState();
    }

    public void changePlayerStateByIDOnEvent(int id, CombatActionEvent event){
        if(!checkIDValid(id)){
            throw new RuntimeException(String.format("ID %d does not exist", id));
        }
        playerMap.get(id).changePlayerStateOnEvent(event);
    }

    public void setBots(List<Integer> botsID, PhysicsSystem physicsSystem){
        for(int id: botsID){
//            getPlayerByID(id).setIsBot(true);
            playerMap.put(id, new DummyBot());
        }
    }

    public int getAlivePlayers(){
        for(int id: playerMap.keySet()){
            if(playerMap.get(id).getNumOfLives()>=1){
                return id;
            }
        }
        return 0;
    }

    public boolean hurt(int beingAttacked, int attacker){
        Player playerBeingAttacked = getPlayerByID(beingAttacked);
        Player playerAttacking = getPlayerByID(attacker);
        double health = playerBeingAttacked.reduceHealth(playerAttacking.getAttackDamage());

        if(health<=0.0){
            int remainingLife = playerBeingAttacked.loseLife();
            if(remainingLife<=0){
                ranking.offer(beingAttacked);
                if(--numOfAlivePlayers == 1){
                    winnerID = attacker;
                    ranking.offer(attacker);
                    return true;
                }
            }
        }
        return false;
    }

    public Queue<Integer> getRanking(){
        return ranking;
    }

    public void setNumOfLives(int numOfLives){
        for(int id: playerMap.keySet()){
            playerMap.get(id).setNumOfLives(numOfLives);
        }
    }

    // check if the id passed in exists in the map
    private boolean checkIDValid(int id){
        return playerMap.containsKey(id);
    }

}
