package messenger.external;

public class FXVolumeEvent extends Event {

    private double volume;

    public FXVolumeEvent(double volume){
        this.volume = volume;
    }

    @Override
    public String getName() {
        return null;
    }

    public double getVolume(){
        return this.volume;
    }
}
