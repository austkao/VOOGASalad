package messenger.external;

import java.util.List;

public class AttackIntersectEvent extends Event {

    private List<List<Integer>> playersIntersecting;
    private int attacker;
    private int attacked;

    public AttackIntersectEvent(int attacker, int attacked){
        this.attacker = attacker;
        this.attacked = attacked;
    }

    public int getAttacker(){
        return this.attacker;
    }

    public int getAttacked(){
        return this.attacked;
    }

    public List<List<Integer>> getAttackPlayers(){
        return this.playersIntersecting;
    }

    @Override
    public String getName() {
        return "AttackIntersectEvent";
    }
}
