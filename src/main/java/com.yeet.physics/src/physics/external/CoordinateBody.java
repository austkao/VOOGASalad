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

    public Intersection intersects(CoordinateBody c){

        Rectangle2D object = c.getHitBox();
        // check which side of the ground body is located
        boolean below = object.getMinY() <= this.getHitBox().getMaxY();
        boolean above = object.getMaxY() >= this.getHitBox().getMinY();
        boolean left = object.getMaxX() >= this.getHitBox().getMinX();
        boolean right = object.getMinX() <= this.getHitBox().getMaxX();

        //
        boolean bottomLeft = (left && below);
        boolean bottomRight = (right && below);
        boolean topLeft = (left && above);
        boolean topRight = (right && above);

        boolean upperLeftShallowIntersect = (object.getMaxY() - this.getHitBox().getMinY()) < (object.getMaxX() - this.getHitBox().getMinX());
        boolean upperRightShallowIntersect = (object.getMaxY() - this.getHitBox().getMinY()) < (this.getHitBox().getMaxX() - object.getMinX());
        boolean upperLeftDeepIntersect = (object.getMaxY() - this.getHitBox().getMinY()) > (object.getMaxX() - this.getHitBox().getMinX());
        boolean upperRightDeepIntersect = (object.getMaxY() - this.getHitBox().getMinY()) > (this.getHitBox().getMaxX() - object.getMinX());

        boolean lowerLeftShallowIntersect = (this.getHitBox().getMaxY() - object.getMinY()) < (object.getMaxX() - this.getHitBox().getMinX());
        boolean lowerRightShallowIntersect = (this.getHitBox().getMaxY() - object.getMinY()) < (this.getHitBox().getMaxX() - object.getMinX());
        boolean lowerLeftDeepIntersect = (this.getHitBox().getMaxY() - object.getMinY()) > (object.getMaxX() - this.getHitBox().getMinX());
        boolean lowerRightDeepIntersect = (this.getHitBox().getMaxY() - object.getMinY()) > (this.getHitBox().getMaxX() - object.getMinX());

        // Find which way the body will be stopped from
        boolean aboveHit = ((topLeft && upperLeftShallowIntersect) || (topRight && upperRightShallowIntersect) || (above && !below && !left && !right));
        boolean belowHit = ((bottomLeft && lowerLeftShallowIntersect) || (bottomRight && lowerRightShallowIntersect) || (!above && below && !left && !right));
        boolean leftHit = ((topLeft && upperLeftDeepIntersect) || (bottomLeft && lowerLeftDeepIntersect) || (!above && !below && left && !right));
        boolean rightHit = ((topRight && upperRightDeepIntersect) || (bottomRight && lowerRightDeepIntersect) || (!above && !below && !left && right));

        if(below && above && left && right) {
            if (belowHit) {
                    return new Intersection(new Side("BOTTOM"));
            }
            if (leftHit) {
                    return new Intersection(new Side("LEFT"));
            }
            if (rightHit) {
                    return new Intersection(new Side("RIGHT"));
            }
            if (aboveHit) {
             return new Intersection(new Side("TOP"));
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
