package physics.external;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Line {

    private Point first;
    private Point second;

    public Line(Point first, Point second){
        this.first = first;
        this.second = second;
    }

    public double[] getXBounds(){
        double[] bounds = new double[2];
        if(this.getFirst().getX() > this.getSecond().getX()){
            bounds[0] = this.getSecond().getX();
            bounds[1] = this.getFirst().getX();
        }else{
            bounds[1] = this.getSecond().getX();
            bounds[0] = this.getFirst().getX();
        }
        return bounds;
    }

    public double[] getYBounds(){
        double[] bounds = new double[2];
        if(this.getFirst().getY() > this.getSecond().getY()){
            bounds[0] = this.getSecond().getY();
            bounds[1] = this.getFirst().getY();
        }else{
            bounds[1] = this.getSecond().getY();
            bounds[0] = this.getFirst().getY();
        }
        return bounds;
    }

    public Point getFirst(){
        return this.first;
    }

    public Point getSecond(){
        return this.second;
    }

}
