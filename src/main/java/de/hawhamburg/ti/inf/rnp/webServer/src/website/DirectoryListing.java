package de.hawhamburg.ti.inf.rnp.webServer.src.website;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// get SingletonInstance by DirectoryListing.getInstance()
public class DirectoryListing {
    private static final DirectoryListing OBJ = new DirectoryListing();
    private static final String FALLBACK_PATH = "/Users/krisschaaf";
    private static final String RELATIVE_PATH = "/IdeaProjects/http-1.1-web-server/src/main/java/de/hawhamburg/ti/inf/rnp/webServer/src/website/content";

    //TODO use this instead
//    private static final String ABSOLUTE_PATH = getAbsolutePath();
//    private static String getAbsolutePath() {
//        try {
//            return System.getProperty("user.home") + RELATIVE_PATH;
//        } catch (Exception ex) {
//            return FALLBACK_PATH + RELATIVE_PATH;
//        }
//    }

    private static final String ABSOLUTE_PATH = FALLBACK_PATH + RELATIVE_PATH;
    private final Set<String> simpleFileSet = new HashSet<>();
    private final Set<WebsiteFile> fullFileSet = new HashSet<>();

    private DirectoryListing() {
        try {
            simpleFileSet.addAll(this.getSimpleFileSetFromDir(ABSOLUTE_PATH));
            fullFileSet.addAll(this.getFullFileSetFromDir(ABSOLUTE_PATH));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static DirectoryListing getInstance() {
        return OBJ;
    }

    private Set<String> getSimpleFileSetFromDir(String dir) throws IOException {
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.getFileName()
                            .toString());
                } else {
                    fileSet.addAll(getSimpleFileSetFromDir(path.toAbsolutePath().toString()));
                }
            }
        }
        return fileSet;
    }

    private Set<WebsiteFile> getFullFileSetFromDir(String dir) throws IOException {
        Set<WebsiteFile> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    WebsiteFile file = new WebsiteFile(
                            //TODO fix
                            path.getFileName().toString(),
                            3d,
                            new Date(),
                            ""
                    );
                    fileSet.add(file);
                } else {
                    fileSet.addAll(getFullFileSetFromDir(path.toAbsolutePath().toString()));
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
        return this.simpleFileSet.contains(filename);
    }
}
