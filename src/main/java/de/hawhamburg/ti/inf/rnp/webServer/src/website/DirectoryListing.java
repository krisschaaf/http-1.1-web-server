package de.hawhamburg.ti.inf.rnp.webServer.src.website;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.DirectoryUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DirectoryListing {

    private static final DirectoryListing OBJ = new DirectoryListing();

    private final Set<String> simpleFileSet = new HashSet<>();

    private final Set<File> fullFileSet = new HashSet<>();

    private DirectoryListing() {
        try {
            simpleFileSet.addAll(this.getSimpleFileSetFromDir(DirectoryUtils.ABSOLUTE_PATH));
            fullFileSet.addAll(this.getFullFileSetFromDir(DirectoryUtils.ABSOLUTE_PATH));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private Set<String> getSimpleFileSetFromDir(String dir) throws IOException {
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.toAbsolutePath().toString().split("content")[1]);
                } else {
                    fileSet.addAll(getSimpleFileSetFromDir(path.toAbsolutePath().toString()));
                }
            }
        }
        return fileSet;
    }

    private Set<File> getFullFileSetFromDir(String dir) throws IOException {
        Set<File> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    File file = new File(path.toAbsolutePath().toString());
                    fileSet.add(file);
                } else {
                    fileSet.addAll(getFullFileSetFromDir(path.toAbsolutePath().toString()));
                }
            }
        }
        return fileSet;
    }

    private String directoryListingToHTML(Set<File> directoryListing) {
        StringBuilder html = new StringBuilder();
        for (File file: directoryListing) {
            String listItem = String.format("<li>Name: %1$s, Length: %2$d, LastModified: %3$s</li>\r\n",
                    file.getName(),
                    file.length(),
                    new Date(file.lastModified()));
            html.append(listItem);
        }
        return "<ul>\r\n" + html + "</ul>";
    }

    public static DirectoryListing getInstance() {
        return OBJ;
    }

    public String getDirectoryListingAsHTML() {
        return this.directoryListingToHTML(this.fullFileSet);
    }

    public boolean directoryContainsFile(String filename) {
        return this.simpleFileSet.contains(filename);
    }
}
