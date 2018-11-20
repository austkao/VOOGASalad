package physics.external;

import java.awt.geom.Line2D;
import java.util.List;

public class Dimensions {

    private List<Double> dims;

    public Dimensions(double x, double y){
        this.dims.add(x);
        this.dims.add(y);
    }

    public Dimensions(double x, double y, double z){
        this(x, y);
        this.dims.add(z);
    }

    public Dimensions(List<Double> dims){
        this.dims = dims;
    }

    public double getSizeX(){
        if(dims.size() > 1) {
            return dims.get(0);
        }
        return 0.0;
    }

    public double getSizeY(){
        if(dims.size() > 2) {
            return dims.get(1);
        }
        return 0.0;
    }

    public double getSizeZ(){
        if(dims.size() > 3) {
            return dims.get(2);
        }else{
            return 0.0;
        }
    }

    public List<Double> getDims(){
        return this.dims;
    }

}
