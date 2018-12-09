package physics.external.combatSystem;

import messenger.external.CombatActionEvent;
import messenger.external.PlayerState;

import java.util.ArrayList;
import java.util.List;

/*
    Represents the character a specific player controls. Responsible for keeping track of
    the character's stats and states
    @author xp19
 */

public class Player {

    protected int id;
    private String name;
    private double health = 100.0;
    private int numOfLives;
    private int powerLevel;
    private double attackDamage = 10.0;
    private List<Player> isAttackingTargets;
    private List<List<Integer>> hitboxes;
    private Player beingAttackedBy;
    private PlayerState playerState;
    private boolean isBot;

    public Player(){
        playerState = PlayerState.INITIAL;
        isAttackingTargets = new ArrayList<>();
    }

    public Player(int id){
        this();
        this.id = id;
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    /* update the current player's state based on the event passed in */
    public void changePlayerStateOnEvent(CombatActionEvent event){
        playerState = playerState.changeStatesOnEvent(event);
    }

    /* reset player's state to default state */
    public void setToInitialState(){
        this.playerState = PlayerState.INITIAL;
    }

    /* set player's current state */
    protected void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    /* add who is being attacked by this player */
    public void addAttackingTargets(Player target){
        isAttackingTargets.add(target);
        this.playerState = PlayerState.ATTACKING;
        target.setBeingAttackedBy(this);
        target.playerState = PlayerState.BEING_ATTACKED;
        target.freeTargets();
    }

    public double reduceHealth(double amt){
        this.health -= amt;
        return this.health;
    }

    public double getAttackDamage(){
        return this.attackDamage;
    }

    public void setIsBot(boolean isBot){
        this.isBot = isBot;
    }

    /* set who is attacking this player */
    private void setBeingAttackedBy(Player attacker){
        this.beingAttackedBy = attacker;
    }

    private void freeTargets(){
        for(Player p: isAttackingTargets){
            p.setToInitialState();
        }
    }



    @Override
    public String toString(){
        return String.format("Player %d named %s", id, name);
    }

}
