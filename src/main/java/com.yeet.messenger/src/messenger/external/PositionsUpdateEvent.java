package messenger.external;

import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.Map;

public class PositionsUpdateEvent extends Event {

    private Map<Integer, Point2D> map;

    public PositionsUpdateEvent(Map<Integer, Point2D> map){
        this.map = map;
    }

    public Map<Integer, Point2D> getPositions(){
        return this.map;
    }

    @Override
    public String getName() {
        return "PositionsUpdateEvent";
    }
}
