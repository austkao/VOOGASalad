package dataSystem;

public enum FilePath {
    GAMEPATH("/src/main/java/com.yeet.main/resources"),
    STAGEPATH("/stages"),
    CHARACTERPATH("/characters"),
    DATAPATH("/data"),
    BACKGROUNDPATH("/data/background"),
    BGMPATH("/data/bgm"),
    TILEPATH("/data/tiles"),
    STAGEPROPERTIES("/stageproperties.xml");

    private String myPath;

    FilePath(String path) {
        myPath = path;
    }

    public String getPath() {
        return myPath;
    }
}
