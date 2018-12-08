package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.List;

public class CoordinateBody {


    private Dimensions dims;
    private Rectangle2D hitBox;

    private List<SubRectangle> subRects;

    private Coordinate pos;

    private int xSubRects = 4;
    private int ySubRects = 4;

    public CoordinateBody(Coordinate start, Dimensions dims){
        this.dims = dims;
        this.pos = start;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
    }

    public void drawSubRects(){
        double subSizeX = this.dims.getSizeX()/xSubRects;
        double subSizeY = this.dims.getSizeY()/ySubRects;
        for(int y = 0; y < ySubRects; y++){
            for(int x = 0; x < xSubRects; x++){
                double locX = this.pos.getX() + (x*(subSizeX));
                double locY = this.pos.getY() + (y*(subSizeY));
                if(!((y >= 1 && (x >= 1 && x <= xSubRects-2)) && (y <= ySubRects-2 && (x >= 1 && x <= xSubRects-2)))){//Messy Logic
                    this.subRects.add(new SubRectangle(locX + (x*subSizeX),locY + (y*subSizeY), subSizeX, subSizeY ));
                }
            }
        }
    }

    public void updateSubRects(){
        for(SubRectangle sub: this.getSubRects()){
        }
    }

    public void update(Coordinate newPos){
        this.pos = newPos;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), dims.getSizeX(), dims.getSizeY());
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
    public Intersection intersects(CoordinateBody c){

        Rectangle2D object = c.getHitBox();

        boolean below = object.getY() <= (this.getHitBox().getY() + this.getDims().getSizeY());
        boolean above = (object.getY() + object.getHeight()) >= this.getHitBox().getY();
        boolean left = (object.getX() + object.getWidth()) >= this.getHitBox().getX();
        boolean right = object.getX() <= (this.getHitBox().getX() + this.getDims().getSizeX());

        // Checking which side of the brick the bouncer is hitting
        boolean aboveHit = object.getY() < this.getHitBox().getY();
        boolean belowHit = object.getY() + object.getHeight() > this.getHitBox().getY() + this.getDims().getSizeY();
        boolean leftHit = object.getX() < this.getHitBox().getX();
        boolean rightHit = object.getX() + object.getWidth() > this.getHitBox().getX() + this.getDims().getSizeX();

        if(below && above && left && right) {
            if (aboveHit) {
                return new Intersection(new Side("TOP"));
            } else if (belowHit) {
                return new Intersection(new Side("BOTTOM"));
            } else if (leftHit) {
                return new Intersection(new Side("LEFT"));
            } else if (rightHit) {
                return new Intersection(new Side("RIGHT"));
            }
        }
        return new Intersection(new Side("NONE"));
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

    public List<SubRectangle> getSubRects(){
        return this.subRects;
    }
    public void setSubRects(List<SubRectangle> mySubRects){
        this.subRects = mySubRects;
    }
}
