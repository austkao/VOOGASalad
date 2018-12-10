package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.CombatActionEvent;
import messenger.external.JumpEvent;
import messenger.external.MoveEvent;

public class CopyNinjaBot extends Bot {

    Player target;

    public CopyNinjaBot(){

    }

    @Override
    public void step() {

    }

    @Override
    protected void transition() {

    }

    @Subscribe
    public void copyPlayerMoves(CombatActionEvent combatActionEvent){
        if(target==null) return;
        if(combatActionEvent.getInitiatorID()==this.id) return;

        if(combatActionEvent.getInitiatorID()==target.id){
            switch (combatActionEvent.getInputPlayerState()){
                case MOVING:
                    eventBus.post(new MoveEvent(this.id, true));
                    break;
                case SINGLE_JUMP:
                    eventBus.post(new JumpEvent(this.id));
                    break;
                case ATTACKING:


            }

        }

    }

}
