package messenger.external;

import java.io.File;

public class CreateGameEvent extends Event {
    private String name;
    private File myGameDirectory;

    public CreateGameEvent(String creationName, File gameDirectory){
        name = creationName;
        myGameDirectory = gameDirectory;
    }

    @Override
    public String getName() {
        return name;
    }

    public File getGameDirectory(){
        return myGameDirectory;
    }
}
