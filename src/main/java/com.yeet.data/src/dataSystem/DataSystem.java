package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dataSubsystem.GameFileSetup;
import javafx.event.Event;
import messenger.external.CreateGameEvent;
import messenger.external.EventBusFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataSystem {
    private EventBus myEB;
    private FilenameFilter filter;
    private FilePath myFP;

    public DataSystem() {
        myEB = EventBusFactory.getEventBus();
        myEB.register(this);
        filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("game");
            }
        };
    }

    @Subscribe
    public void createInitialGameFiles(CreateGameEvent event) {
        File defaultFile = event.getGameDirectory();
        defaultFile.mkdir();
        myFP.setGameDirectory(defaultFile.getPath());
        createDirectory(myFP.STAGEPATH.getPath());
        createDirectory(myFP.CHARACTERPATH.getPath());
        createDirectory(myFP.DATAPATH.getPath());
        createDirectory(myFP.BACKGROUNDPATH.getPath());
        createDirectory(myFP.BGMPATH.getPath());
        createDirectory(myFP.TILEPATH.getPath());
        //myEB.post(event);
    }

    @Subscribe
    public void saveGameFiles(Event event) {
        //myEB.post(event);
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
    }
}
