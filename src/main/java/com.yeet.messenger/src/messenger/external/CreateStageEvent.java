package messenger.external;

import java.io.File;

public class CreateStageEvent extends Event {
    private String name;
    private File myStageDirectory;

    public CreateStageEvent(String stageName, File stageDirectory){
        name = stageName;
        myStageDirectory = stageDirectory;
    }

    @Override
    public String getName() {
        return name;
    }

    public File getStageDirectory(){
        return myStageDirectory;
    }
}
