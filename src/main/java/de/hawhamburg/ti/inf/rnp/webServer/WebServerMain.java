package de.hawhamburg.ti.inf.rnp.webServer;

import de.hawhamburg.ti.inf.rnp.get.src.GetClient;
import de.hawhamburg.ti.inf.rnp.webServer.src.AsyncWebServer;
import picocli.CommandLine;

public class WebServerMain {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new AsyncWebServer()).execute(args);
        System.exit(exitCode);
    }
}
