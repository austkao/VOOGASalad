package editor;

public enum EditorConstant {
    BACKBUTTONXPOSTION(1000);

    private double myValue;

    EditorConstant(double value) {
        myValue = value;
    }

    public double getValue() {
        return myValue;
    }
}
