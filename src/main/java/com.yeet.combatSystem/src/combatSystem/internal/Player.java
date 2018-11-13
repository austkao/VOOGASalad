package combatSystem.internal;

import java.util.List;

public class Player {

    private int id;
    private String name;
    private int health;
    private int powerLevel;
    private int attackDamage;
    private float attackSpeed;
    private List<Player> isAttackingTargets;
    private Player beingAttackedBy;
    private PlayerState playerState;

    public Player(){
        playerState = PlayerState.INITIAL;
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    public boolean onCombatActionEvent(CombatActionEvent event){
        playerState = playerState.changeStatesOnEvent(event);
        return ((JumpEvent)event).getResponse();
    }

    @Override
    public String toString(){
        return String.format("Player %d named %s", id, name);
    }

}
