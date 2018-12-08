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
    private int health;
    private int numOfLives;
    private int powerLevel;
    private int attackDamage;
    private float attackSpeed;
    private List<Player> isAttackingTargets;
    private List<List<Integer>> hitboxes;
    private Player beingAttackedBy;
    private PlayerState playerState;

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

    /*  */
    public void setToInitialState(){
        this.playerState = PlayerState.INITIAL;
    }

    /* add who is being attacked by this player */
    public void addAttackingTargets(Player target){
        isAttackingTargets.add(target);
        this.playerState = PlayerState.ATTACKING;
        target.setBeingAttackedBy(this);
        target.playerState = PlayerState.BEING_ATTACKED;
        target.freeTargets();
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

    /** Returns the {@code Player} object's current state */
    public PlayerState getState(){
        return playerState;
    }

}
