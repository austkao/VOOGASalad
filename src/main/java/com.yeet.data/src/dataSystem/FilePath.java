package dataSystem;

public enum FilePath {
    GAMEPATH("/src/main/java/com.yeet.main/resources"),
    STAGEPATH("/stages"),
    CHARACTERPATH("/characters"),
    DATAPATH("/data"),
    BACKGROUNDPATH("/data/background"),
    BGMPATH("/data/bgm"),
    TILEPATH("/data/tiles"),
    STAGEPROPERTIES("/stageproperties.xml"),
    ATTACK("/attacks"),
    SOUND("/sounds"),
    SPRITE("/sprites"),
    CHARACTERPROPERTIES("/characterproperties.xml"),
    ATTACKPROPERTIES("/attacks/attackproperties.xml"),
    SOUNDPROPERTIES("/sounds/soundproperties.xml"),
    SPRITEPROPERTIES("/sprites/spriteproperties.xml"),
    MODE("/modes"),
    TIME("/modes/time"),
    STOCK("/modes/stock");

    private String myPath;

    FilePath(String path) {
        myPath = path;
    }

    public String getPath() {
        return myPath;
    }
}
