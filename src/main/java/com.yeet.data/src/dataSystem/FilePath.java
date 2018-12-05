package dataSystem;

public enum FilePath {
    GAMEPATH("/src/main/java/com.yeet.main/resources"),
    STAGEPATH("/stages"),
    CHARACTERPATH("/characters"),
    DATAPATH("/data"),
    BACKGROUNDPATH("/data/background"),
    BGMPATH("/data/bgm"),
    TILEPATH("/data/tiles");

    private String myPath;
    private String gameDirectory;

    FilePath(String path) {
        myPath = path;
    }

    public String getPath() {
        if(gameDirectory == null) {
            return myPath;
        }
        return gameDirectory + myPath;
    }

    public void setGameDirectory(String directory) {
        gameDirectory = directory;
    }
}
