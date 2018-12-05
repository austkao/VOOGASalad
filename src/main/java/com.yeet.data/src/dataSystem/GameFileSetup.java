package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import messenger.external.EventBusFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameFileSetup {

    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources";
    private EventBus myEB;
    private FilenameFilter filter;
    private FilePath myFP;

    public GameFileSetup() {
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
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File resources = new File(userPath+myFP.GAMEPATH.getPath());
        int numGames = resources.listFiles(filter).length;
        File defaultFile = new File(resources.getPath() + "/game" + numGames);
        defaultFile.mkdir();
        File stages = new File(defaultFile.getPath()+myFP.STAGEPATH.getPath());
        File characters = new File(defaultFile.getPath()+myFP.CHARACTERPATH.getPath());
        File data = new File(defaultFile.getPath()+myFP.DATAPATH.getPath());
        File background = new File(defaultFile.getPath()+myFP.BACKGROUNDPATH.getPath());
        File bgm = new File(defaultFile.getPath()+myFP.BGMPATH.getPath());
        File tiles = new File(defaultFile.getPath()+myFP.TILEPATH.getPath());
        stages.mkdir();
        characters.mkdir();
        data.mkdir();
        background.mkdir();
        bgm.mkdir();
        tiles.mkdir();
        //myEB.post(event);
    }
}
