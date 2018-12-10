package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import messenger.external.CombatActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.GameOverEvent;
import messenger.external.PlayerDeathEvent;
import physics.external.PhysicsSystem;

import java.util.*;

public class PlayerManager {
    private static final int INITIAL_ID = 0;
    EventBus eventBus = EventBusFactory.getEventBus();
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
            playerMap.put(id, new HardBot(physicsSystem));
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
            eventBus.post(new PlayerDeathEvent(beingAttacked, remainingLife));
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

//    <0,3,1,2> 2 is first place
    public ArrayList<Integer> getRanking(){
        Map<Integer, Integer> map = new TreeMap<>();
        int rank = numOfPlayers;
        while(!ranking.isEmpty()){
            int id = ranking.remove();
            map.put(id, rank);
            rank--;
        }
        return new ArrayList<>(map.values());
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
