package de.hawhamburg.ti.inf.rnp.webServer.src.website;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

// get SingletonInstance by DirectoryListing.getInstance()
public class DirectoryListing {
    private static final DirectoryListing OBJ = new DirectoryListing();
    private static final String FALLBACK_PATH = "/Users/krisschaaf";
    private static final String RELATIVE_PATH = "/IdeaProjects/http-1.1-web-server/src/main/java/de/hawhamburg/ti/inf/rnp/webServer/src/website/content";

    //TODO: use this instead
    //    private static final String ABSOLUTE_PATH = System.getProperty("user.home") != null ?
    //            System.getProperty("user.home") + RELATIVE_PATH :
    //            FALLBACK_PATH + RELATIVE_PATH;

    private static final String ABSOLUTE_PATH = FALLBACK_PATH + RELATIVE_PATH;
    private Set<String> fileSet = new HashSet<>();

    private DirectoryListing() {
        try {
            fileSet.addAll(getFileSetFromDir(ABSOLUTE_PATH));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static DirectoryListing getInstance() {
        return OBJ;
    }

    private Set<String> getFileSetFromDir(String dir) throws IOException {
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.getFileName()
                            .toString());
                } else {
                    fileSet.addAll(getFileSetFromDir(path.toAbsolutePath().toString()));
                }
            }
        }
        return fileSet;
    }

    //TODO remove me
    public static void main(String[] args) {
        DirectoryListing dl = DirectoryListing.getInstance();
    }

    public boolean directoryContainsFile(String filename) {
        return this.fileSet.contains(filename);
    }
}
