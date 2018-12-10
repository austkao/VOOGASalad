package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;
import xml.XMLParser;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

public class CombatSystem {

    EventBus eventBus;
    private PlayerManager playerManager;
    private PhysicsSystem physicsSystem;
    private List<Integer> botList;
    private HashMap<Integer, Point2D> playerMap;
    private XMLParser xmlParser;
    private File gameDir;
    private HashMap<Integer, ArrayList<Double>> characterStats;


//    public CombatSystem(Player bot){
//        eventBus = EventBusFactory.getEventBus();
//        bot.id = 1;
//        playerManager = new PlayerManager(1);
//    }

    public CombatSystem(HashMap<Integer, Point2D> playerMap, HashMap<Integer, Rectangle2D> tileMap, PhysicsSystem physicsSystem, File gameDir, Map<Integer, String> characterNames){
        characterStats = new HashMap<>();
        // get character stats
        for(int id: characterNames.keySet()){
            String name = characterNames.get(id);
            xmlParser = new XMLParser(Paths.get(gameDir.getPath(), "characters", name, "characterproperties.xml").toFile());
            HashMap<String, ArrayList<String>> map = xmlParser.parseFileForElement("character");
            ArrayList<Double> stats = new ArrayList<>();
            // get attack damage
            Double damage = Double.parseDouble(map.get("attack").get(0));
            // get defense
            Double defense = Double.parseDouble(map.get("defense").get(0));
            // get health
            Double health = Double.parseDouble(map.get("health").get(0));
            stats.add(damage);
            stats.add(defense);
            stats.add(health);
            characterStats.put(id, stats);
        }

        xmlParser = new XMLParser(Paths.get(gameDir.getPath(), "characters","Lucina1","attacks","attackproperties.xml").toFile());
        HashMap<String, ArrayList<String>> map = xmlParser.parseFileForElement("attack");
        System.out.println(characterNames);
        System.out.println(map);

        eventBus = EventBusFactory.getEventBus();
        this.playerMap = playerMap;
//        playerManager = new PlayerManager(playerMap.keySet().size());
        this.physicsSystem = physicsSystem;
        // register players to physics engine
        for(int i = 0; i < playerMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(0, PhysicsSystem.DEFAULT_MASS, playerMap.get(i).getX(), playerMap.get(i).getY(),40,60);
        }
        // register tiles to physics engine
        for(int i=0;i < tileMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(2,0, tileMap.get(i).getX(),tileMap.get(i).getY(),tileMap.get(i).getWidth(),tileMap.get(i).getHeight());
        }
    }

    /** Returns the {@code PlayerState} of the player specified
     *  @param id The player to retrieve the state for
     */
    public PlayerState getPlayerState(int id){
        return playerManager.getPlayerByID(id).getPlayerState();
    }

    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
        if(!botList.contains(id)){
            playerManager.changePlayerStateByIDOnEvent(id, event);
        }

    }

    @Subscribe
    public void onIdleEvent(IdleEvent idleEvent){
        int id = idleEvent.getId();
        if(playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.SINGLE_JUMP
                && playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.DOUBLE_JUMP){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackSuccessfulEvent(AttackSuccessfulEvent event){
//        System.out.println("Attack!!!");
        physicsSystem.attack(event.getInitiatorID());
    }

    @Subscribe
    public void onMoveSuccessfulEvent(MoveSuccessfulEvent event){
        boolean direction = event.getDirection();
//        System.out.println("Move" + direction);
        // move left
        if(direction){
            physicsSystem.move(event.getInitiatorID(), PI);
        }
        // move right
        else{
            physicsSystem.move(event.getInitiatorID(), 0);
        }
    }

    @Subscribe
    public void onGroundIntersectEvent(GroundIntersectEvent event){
        List<Integer> playersOnGround = event.getGroundedPlayers();
        for(int id: playersOnGround){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackIntersectEvent(AttackIntersectEvent event){
        Map<Integer, Double> playersBeingRekt = new HashMap<>();
        for(List<Integer> list: event.getAttackPlayers()){
            Player playerBeingAttacked = playerManager.getPlayerByID(list.get(0));
            Player playerAttacking = playerManager.getPlayerByID(list.get(1));
            playerAttacking.addAttackingTargets(playerBeingAttacked);
            boolean result = playerManager.hurt(list.get(0), list.get(1));
            if(result){
                eventBus.post(new GameOverEvent(playerManager.winnerID, playerManager.getRanking()));
            }
            playersBeingRekt.put(playerBeingAttacked.id, playerManager.getPlayerByID(list.get(0)).getHealth());
        }
        eventBus.post(new GetRektEvent(playersBeingRekt));
    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){
        physicsSystem.jump(event.getInitiatorID());
    }

    @Subscribe
    public void onGameStart(GameStartEvent gameStartEvent){
        botList = gameStartEvent.getBots();
        playerManager = new PlayerManager(playerMap.size(), characterStats);
        playerManager.setBots(botList, physicsSystem);
        String type = gameStartEvent.getGameType().toLowerCase();
        if(type.equals("stock")){
            int life = gameStartEvent.getTypeValue();
            playerManager.setNumOfLives(life);
        }
        else{
            // timed
        }

        PlayerGraph graph = new PlayerGraph(playerManager, physicsSystem.getPositionsMap());
        for(int id: botList){
            ((Bot)playerManager.getPlayerByID(id)).setPlayerGraph(graph);
        }
        for(int id: botList){
            ((NormalBot)playerManager.getPlayerByID(id)).start();
        }
    }

    @Subscribe
    public void onTimeUpEvent(TimeUpEvent timeUpEvent){
        eventBus.post(new GameOverEvent(playerManager.winnerID, playerManager.getRanking()));
    }

}