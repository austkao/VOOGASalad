package messenger.external;

import javafx.geometry.Point2D;

import java.util.HashMap;

public class PositionsUpdateEvent extends Event {

    private HashMap<Integer, Point2D> map;

    public PositionsUpdateEvent(HashMap<Integer, Point2D> map){
        this.map = map;
    }

    public HashMap<Integer, Point2D> getPositions(){
        return this.map;
    }

    @Override
    public String getName() {
        return "PositionsUpdateEvent";
    }
}
