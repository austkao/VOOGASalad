package physics.external;

import java.util.List;

public abstract class Shape {

    private List<Line> path;

    public Shape(List<Line> shapeLines){
        this.path = shapeLines;
    }

}
