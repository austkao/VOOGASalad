package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dataSubsystem.GameFileSetup;
import javafx.event.Event;
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
        filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("game");
            }
        };
    }

    @Subscribe
    public void createInitialGameFiles(Event event) {
        new GameFileSetup();
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File resources = new File(userPath+myFP.GAMEPATH.getPath());
        int numGames = resources.listFiles(filter).length;
        File defaultFile = new File(resources.getPath() + "/game" + numGames);
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
