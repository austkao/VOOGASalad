package physics.external;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class CoordinateBody {


    private Dimensions dims;

    //private Square hitBox;
    private Rectangle2D hitBox;

    //private List<Line> path;

    private Coordinate pos;

    public CoordinateBody(Coordinate start, Dimensions dims){
        this.dims = dims;
        this.pos = start;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
//        this.path = new ArrayList<>();
//
//        Point2D.Double top0 = new Point2D.Double(start.getX(), start.getY());
//        Point2D.Double top1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY());
//        Line top = new Line(top0, top1);
//
//        Point2D.Double bottom0 = new Point2D.Double(start.getX(), start.getY()+dims.getSizeY());
//        Point2D.Double bottom1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY()+dims.getSizeY());
//        Line bottom = new Line(bottom0, bottom1);
//
//        Point2D.Double right0 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY());
//        Point2D.Double right1 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY() + dims.getSizeY());
//        Line right = new Line(right0, right1);
//
//        Point2D.Double left0 = new Point2D.Double(start.getX(), start.getY());
//        Point2D.Double left1 = new Point2D.Double(start.getX(), start.getY() + dims.getSizeY());
//        Line left = new Line(left0, left1);
//
//        this.path.add(top);
//        this.path.add(bottom);
//        this.path.add(right);
//        this.path.add(left);
    }

    public void update(Coordinate newPos){
        this.pos = newPos;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), dims.getSizeX(), dims.getSizeY());

//        this.path = new ArrayList<Line>();
//
//        Point2D.Double top0 = new Point2D.Double(newPos.getX(), newPos.getY());
//        Point2D.Double top1 = new Point2D.Double(newPos.getX()+ dims.getSizeX(), newPos.getY());
//        Line top = new Line(top0, top1);
//
//        Point2D.Double bottom0 = new Point2D.Double(newPos.getX(), newPos.getY()+dims.getSizeY());
//        Point2D.Double bottom1 = new Point2D.Double(newPos.getX()+ dims.getSizeX(), newPos.getY()+dims.getSizeY());
//        Line bottom = new Line(bottom0, bottom1);
//
//        Point2D.Double right0 = new Point2D.Double(newPos.getX() + dims.getSizeX(), newPos.getY());
//        Point2D.Double right1 = new Point2D.Double(newPos.getX() + dims.getSizeX(), newPos.getY() + dims.getSizeY());
//        Line right = new Line(right0, right1);
//
//        Point2D.Double left0 = new Point2D.Double(newPos.getX(), newPos.getY());
//        Point2D.Double left1 = new Point2D.Double(newPos.getX(), newPos.getY() + dims.getSizeY());
//        Line left = new Line(left0, left1);
//
//        this.path.add(top);
//        this.path.add(bottom);
//        this.path.add(right);
//        this.path.add(left);
    }

    public Coordinate getPos(){
        return this.pos;
    }

    public void setPos(double x, double y){
        this.pos.setX(x);
        this.pos.setY(y);
        update(pos);
    }

    /*
    INPUT: This function takes in another coordinate body
    OUTPUT: T or F depending on weather or not this body intersects with the one passed into the parameter
     */
    public boolean intersects(CoordinateBody c){
        return (this.getHitBox().intersects(c.getHitBox()));
//        Square myBox = this.getHitBox();
//        Square theirBox = c.getHitBox();
//        boolean above = false;
//        boolean below = false;
//        boolean left = false;
//        boolean right = false;
//
//
//        if(theirBox.getMinX() > myBox.getMaxX()){
//            right = true;
//        }
//        if(theirBox.getMaxX() < myBox.getMinX()){
//            left = true;
//        }
//        if(theirBox.getMaxY() < myBox.getMinY()){
//            below = true;
//        }
//        if(theirBox.getMinY() > myBox.getMaxY()){
//            above = true;
//        }
//        return (! (above || below || left || right));
    }

    public Dimensions getDims(){
        return this.dims;
    }

    public void setDims(Dimensions dims){
        this.dims = dims;
    }

    public Rectangle2D getHitBox() {
        return this.hitBox;
    }
}
