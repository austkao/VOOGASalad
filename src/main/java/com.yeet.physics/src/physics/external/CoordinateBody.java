package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinateBody {


    private Dimensions dims;
    private Rectangle2D hitBox;

    private List<SubRectangle> subRects;

    private Map<SubRectangle, Side> sideRects;
    private Map<String, SubRectangle> sideTags;

    private Coordinate pos;

    private double offsetX;
    private double offsetY;
    private int xSubRects = 1;
    private int ySubRects = 1;
    private double subSizeX;
    private double subSizeY;

    public CoordinateBody(Coordinate start, Dimensions dims){
        this.dims = dims;
        this.pos = start;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
        this.sideRects = new HashMap<>();
        this.sideTags = new HashMap<>();
        this.drawSubRects();
    }

    public void drawSubRects(){
        this.offsetX = this.getDims().getSizeX()/4;
        this.offsetY = this.getDims().getSizeY()/4;
        this.subSizeX = this.dims.getSizeX()/xSubRects - 2*offsetX;
        this.subSizeY = this.dims.getSizeY()/ySubRects - 2*offsetY;

        double posX = this.getPos().getX();
        double posY = this.getPos().getY();
        drawTop(posX, posY);
        drawRight(posX, posY);
        drawLeft(posX, posY);
        drawBottom(posX, posY);
    }

    public void drawTop(double posX, double posY){
        SubRectangle top = new SubRectangle(posX + offsetX, posY, subSizeX, offsetY);
        sideRects.put(top, new Side("TOP"));
        sideTags.put("TOP", top);
    }

    public void updateTop(double posX, double posY){
        sideTags.get("TOP").setPosX(posX + offsetX);
        sideTags.get("TOP").setPosY(posY);
    }

    public void drawRight(double posX, double posY){
        SubRectangle right = new SubRectangle(posX+offsetX+subSizeX, posY + offsetY, offsetX, subSizeY);
        sideRects.put(right, new Side("RIGHT"));
        sideTags.put("RIGHT", right);
    }

    public void updateRight(double posX, double posY){
        sideTags.get("RIGHT").setPosX(posX+offsetX+subSizeX);
        sideTags.get("RIGHT").setPosY(posY + offsetY);
    }

    public void drawBottom(double posX, double posY){
        SubRectangle bottom = new SubRectangle(posX + offsetX, posY+offsetY+subSizeY, subSizeX, offsetY);
        sideRects.put(bottom, new Side("BOTTOM"));
        sideTags.put("BOTTOM", bottom);
    }

    public void updateBottom(double posX, double posY){
        sideTags.get("BOTTOM").setPosX(posX + offsetX);
        sideTags.get("BOTTOM").setPosY(posY+offsetY+subSizeY);
    }

    public void drawLeft(double posX, double posY){
        SubRectangle left = new SubRectangle(posX, posY+offsetY, offsetX, subSizeY);
        sideRects.put(left, new Side("LEFT"));
        sideTags.put("LEFT", left);
    }

    public void updateLeft(double posX, double posY){
        sideTags.get("LEFT").setPosX(posX);
        sideTags.get("LEFT").setPosY(posY+offsetY);
    }

    public void updateRects(){
        updateTop(this.getPos().getX(), this.getPos().getY());
        updateRight(this.getPos().getX(), this.getPos().getY());
        updateBottom(this.getPos().getX(), this.getPos().getY());
        updateLeft(this.getPos().getX(), this.getPos().getY());
    }

    public void update(Coordinate newPos){
        this.pos = newPos;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), dims.getSizeX(), dims.getSizeY());
        double posX = newPos.getX();
        double posY = newPos.getY();
        drawTop(posX, posY);
        drawRight(posX, posY);
        drawLeft(posX, posY);
        drawBottom(posX, posY);
    }

    public Coordinate getPos(){
        return this.pos;
    }

    public void setPos(double x, double y){
        this.pos.setX(x);
        this.pos.setY(y);
        update(pos);
    }

    public Intersection intersects(CoordinateBody c){
        List<Side> mySides = new ArrayList<>();

        for(String tag: this.sideTags.keySet()){
            if(this.sideTags.get(tag).getMyRect().intersects(c.getHitBox())){
                    mySides.add(new Side(tag));
            }
        }
        Intersection intersect = new Intersection(mySides);
        return intersect;
    }

    /*
    INPUT: This function takes in another coordinate body
    OUTPUT: T or F depending on weather or not this body intersects with the one passed into the parameter
     */
    /*
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
        List<Side> mySides = new ArrayList<>();
        if(below && above && left && right) {
            if (aboveHit) {
                mySides.add(new Side("TOP"));
            }if (belowHit) {
                mySides.add(new Side("BOTTOM"));
            }if (leftHit) {
                mySides.add(new Side("LEFT"));
            }if (rightHit) {
                mySides.add(new Side("RIGHT"));
            }
        }
        return new Intersection(mySides);
    }
    */

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

    public double getOffsetX(){
        return this.offsetX;
    }

    public double getOffsetY(){
        return this.offsetY;
    }

    public double getSubSizeX(){
        return this.subSizeX;
    }

    public double getSubSizeY(){
        return this.subSizeY;
    }
}
