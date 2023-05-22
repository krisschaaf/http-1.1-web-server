package de.hawhamburg.ti.inf.rnp;

import de.hawhamburg.ti.inf.rnp.webServer.AsyncWebServer;
import de.hawhamburg.ti.inf.rnp.get.GetClient;
import picocli.CommandLine;

public class Main {
    private static final int PORT = 80;
    public static void main(String[] args) {
        AsyncWebServer asyncWebServer = new AsyncWebServer(PORT);
        asyncWebServer.run();

        int exitCode = new CommandLine(new GetClient()).execute(args); // |7|
        System.exit(exitCode); // |8|
    }
}
