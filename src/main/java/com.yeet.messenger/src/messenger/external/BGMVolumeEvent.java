package messenger.external;

public class BGMVolumeEvent extends Event {

    double volume;

    //Volume is between 0 and 1
    public BGMVolumeEvent(double volume){
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
