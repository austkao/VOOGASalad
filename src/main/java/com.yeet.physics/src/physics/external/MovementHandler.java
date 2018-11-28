package physics.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import messenger.external.EventBusFactory;
import messenger.external.MoveSuccessfulEvent;

import java.util.List;

public class MovementHandler extends InputHandler {

    private EventBus msgBus;
    private List<PhysicsObject> gameElements;

    public MovementHandler(List<PhysicsObject> gameElements){
        super(gameElements);
        this.msgBus = EventBusFactory.getEventBus();
        this.gameElements = gameElements;
    }

    @Subscribe
    public void update(MoveSuccessfulEvent mv) {
        for(PhysicsObject b: this.gameElements){
            if(mv.getInitiatorID() == b.getId()){
                PhysicsVector moveForce;
                if(mv.getDirection()){//If the force isGoingLeft
                    //TODO: HOW TO TAKE INTO ACCOUNT DIFFERENT SPEEDS BETWEEN CHARACTERS
                    moveForce = new PhysicsVector(3, -1);
                }else{
                    moveForce = new PhysicsVector(3,1);
                }
                b.addCurrentForce(moveForce);
            }
        }
    }
}
