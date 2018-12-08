package physics.external.combatSystem;

import messenger.external.PlayerState;

import java.util.HashMap;
import java.util.Map;

public class Tester {

    public static void main(String[] args){
        Map<PlayerState, Integer> map =  new HashMap<>();
        map.put(PlayerState.MOVING, 0);
        map.put(PlayerState.SINGLE_JUMP, 1);
        map.put(PlayerState.ATTACKING, 2);
        map.put(PlayerState.CROUCH, 3);
        System.out.println(map.get(PlayerState.ATTACKING));

    }

}
