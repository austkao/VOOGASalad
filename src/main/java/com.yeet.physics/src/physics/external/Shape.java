package physics.external;

import java.util.List;

public abstract class Shape {

    private List<Line> shapeLines;

    public Shape(List<Line> shapeLines){
        this.shapeLines = shapeLines;
    }

}
