package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.event.Event;
import messenger.external.CreateGameEvent;
import messenger.external.CreateStageEvent;
import messenger.external.DeleteDirectoryEvent;
import messenger.external.EventBusFactory;

import java.io.File;
import java.io.IOException;

public class DataSystem {
    private EventBus myEB;
    private FilePath myFP;

    public DataSystem() {
        myEB = EventBusFactory.getEventBus();
        myEB.register(this);
    }

    @Subscribe
    public void createInitialGameFiles(CreateGameEvent event) {
        File defaultFile = event.getGameDirectory();
        defaultFile.mkdir();
        createDirectory(defaultFile.getPath() + myFP.STAGEPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.CHARACTERPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.DATAPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.BACKGROUNDPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.BGMPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.TILEPATH.getPath());
    }

    @Subscribe
    public void saveGameFiles(Event event) {
        //myEB.post(event);
    }

    @Subscribe
    public void createStageFiles(CreateStageEvent event) {
        File gameStageDirectory = event.getStageDirectory();
        gameStageDirectory.mkdir();
        File stageProperties = new File(gameStageDirectory.getPath()+myFP.STAGEPROPERTIES.getPath());
        try {
            stageProperties.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create stage file");
        }
    }

    @Subscribe
    public void deleteDirectory(DeleteDirectoryEvent event) {
        File directory = event.getDirectory();
        while(directory.exists()) {
            System.out.println("Entered loop");
            deleteFile(directory);
        }
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
    }

    private void deleteFile(File file) {
        File[] files = file.listFiles();
        if(files == null || files.length == 0) {
            file.delete();
        } else {
            for(File f : files) {
                deleteFile(f);
            }
        }
    }
}
