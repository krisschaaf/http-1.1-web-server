package de.hawhamburg.ti.inf.rnp.webServer.src.directoryListing;

// get SingletonInstance by DirectoryListing.getInstance()
public class DirectoryListing {
    private static final DirectoryListing OBJ = new DirectoryListing();

    private DirectoryListing() {

    }

    public static DirectoryListing getInstance() {
        return OBJ;
    }
}
