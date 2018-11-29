package physics.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import messenger.external.EventBusFactory;
import messenger.external.MoveSuccessfulEvent;

import java.util.List;

import static java.lang.Math.PI;

public class MovementHandler extends InputHandler {

    private EventBus msgBus;
    private List<PhysicsObject> gameElements;

    public MovementHandler(List<PhysicsObject> gameElements){
        super(gameElements);
        this.msgBus = EventBusFactory.getEventBus();
        this.gameElements = gameElements;
    }

//    @Subscribe
//    public void update(MoveSuccessfulEvent mv) {
//        for(PhysicsObject b: this.gameElements){
//            if(mv.getInitiatorID() == b.getId()){
//                PhysicsVector moveForce;
//                if (mv.getDirection() ==  PI / 2) { //jump
//                    moveForce = new PhysicsVector();
//                } else { // move left/right
//                    moveForce = new PhysicsVector();
//                }
//
//
//                b.addCurrentForce(moveForce);
//            }
//        }
//    }
}
