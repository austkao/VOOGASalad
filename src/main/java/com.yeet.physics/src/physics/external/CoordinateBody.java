package physics.external;

import java.util.List;

public class CoordinateBody {


    private Dimensions dims;

    private Coordinate coords;

    private List<Double> centerCoords;

    private List<Line> path;

    private Coordinate pos;

    public CoordinateBody(Coordinate start, Dimensions dims){
        this.pos = start;

    }

    /*
    INPUT: This function takes in another coordinate body
    OUTPUT: T or F depending on weather or not this body intersects with the one passed into the parameter
     */
    public boolean intersects(CoordinateBody c){



        return false; 
    }

}
