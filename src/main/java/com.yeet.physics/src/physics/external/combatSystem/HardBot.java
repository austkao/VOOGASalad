package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.CombatActionEvent;
import messenger.external.PlayerState;

import java.util.LinkedList;
import java.util.Queue;

public class HardBot extends Bot {

    Queue<CombatActionEvent> pattern = new LinkedList<>();

    @Override
    public void step() {

    }

    @Override
    public void transition() {

    }

    @Override
    protected PlayerState getNextState(Double[] distribution) {
        return null;
    }

    @Subscribe
    public void recordPlayerMovement(CombatActionEvent combatActionEvent){
        switch (combatActionEvent.getInputPlayerState()){

        }
    }
}
