package dataSubsystem;

import java.io.File;
import java.io.IOException;

/**
 * A class meant to handle any operations relating to the file system.
 * @author ak457
 */
public class FileSystemHandler {

    public void createDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
    }

    public void createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {

        }
    }

    private void deleteFilesWithinDirectory(File file) {
        File[] files = file.listFiles();
        if(files == null || files.length == 0) {
            file.delete();
        } else {
            for(File f : files) {
                deleteFile(f);
            }
        }
    }

    public void deleteFile(File file) {
        while (file.exists()) {
            deleteFilesWithinDirectory(file);
        }
    }
}
