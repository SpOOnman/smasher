package eu.spoonman.smasher.serverinfo;

import java.io.File;
import java.util.*;

public enum Games {

    QUAKE3ARENA;

    private final String path = ("src.eu.spoonman.smasher.serverinfo.").replace(".", File.separator);
    private final String extension = ".properties";
    private List<String> files;

    Games() {
        fillFiles(toString());
    }

    Games(String... files) {
        fillFiles(files);
    }

    private void fillFiles(String... files) {
        this.files = new ArrayList<String>();
        for (String file : files) {
            this.files.add(path + file.toLowerCase() + extension);
        }
    }

    public List<String> getFiles() {
        return files;
    }
}
