package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.event.Event;
import messenger.external.*;

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
        File defaultFile = event.getDirectory();
        defaultFile.mkdir();
        createDirectory(defaultFile.getPath() + myFP.STAGEPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.CHARACTERPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.DATAPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.BACKGROUNDPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.BGMPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.TILEPATH.getPath());
        createDirectory(defaultFile.getPath() + myFP.MODE.getPath());
        createDirectory(defaultFile.getPath() + myFP.TIME.getPath());
        createDirectory(defaultFile.getPath() + myFP.STOCK.getPath());
    }

    @Subscribe
    public void saveGameFiles(Event event) {
        //myEB.post(event);
    }

    @Subscribe
    public void createStageFiles(CreateStageEvent event) {
        File gameStageDirectory = event.getDirectory();
        gameStageDirectory.mkdir();
        File stageProperties = new File(gameStageDirectory.getPath()+myFP.STAGEPROPERTIES.getPath());
        try {
            stageProperties.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create stage file");
        }
    }

    @Subscribe
    public void createCharacterFiles(CreateCharacterEvent event) {
        File characterDirectory = event.getDirectory();
        characterDirectory.mkdir();
        createDirectory(characterDirectory.getPath()+ myFP.ATTACK.getPath());
        createDirectory(characterDirectory.getPath()+ myFP.SOUND.getPath());
        createDirectory(characterDirectory.getPath()+ myFP.SPRITE.getPath());
        createFile(characterDirectory.getPath() + myFP.CHARACTERPROPERTIES.getPath());
        createFile(characterDirectory.getPath() + myFP.ATTACKPROPERTIES.getPath());
        createFile(characterDirectory.getPath() + myFP.SOUNDPROPERTIES.getPath());
        createFile(characterDirectory.getPath() + myFP.SPRITEPROPERTIES.getPath());
    }

    @Subscribe
    public void deleteDirectory(DeleteDirectoryEvent event) {
        File directory = event.getDirectory();
        while(directory.exists()) {
            deleteFile(directory);
        }
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
    }

    private void createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {

        }
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
