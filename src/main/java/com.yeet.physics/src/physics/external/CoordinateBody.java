package physics.external;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CoordinateBody {


    private Dimensions dims;

    private Coordinate coords;

    private List<Double> centerCoords;

    private Square hitBox;

    private List<Line> path;

    private Coordinate pos;

    public CoordinateBody(Coordinate start, Dimensions dims){
        this.pos = start;
        this.path = new ArrayList<Line>();

        Point2D.Double top0 = new Point2D.Double(start.getX(), start.getY());
        Point2D.Double top1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY());
        Line top = new Line(top0, top1);

        Point2D.Double bottom0 = new Point2D.Double(start.getX(), start.getY()+dims.getSizeY());
        Point2D.Double bottom1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY()+dims.getSizeY());
        Line bottom = new Line(bottom0, bottom1);

        Point2D.Double right0 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY());
        Point2D.Double right1 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY() + dims.getSizeY());
        Line right = new Line(right0, right1);

        Point2D.Double left0 = new Point2D.Double(start.getX(), start.getY());
        Point2D.Double left1 = new Point2D.Double(start.getX(), start.getY() + dims.getSizeY());
        Line left = new Line(left0, left1);

        this.path.add(top);
        this.path.add(bottom);
        this.path.add(right);
        this.path.add(left);
        this.hitBox = new Square(this.path);
    }

    /*
    INPUT: This function takes in another coordinate body
    OUTPUT: T or F depending on weather or not this body intersects with the one passed into the parameter
     */
    public boolean intersects(CoordinateBody c){



        return false; 
    }

}
