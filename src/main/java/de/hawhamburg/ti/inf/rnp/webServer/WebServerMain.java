package de.hawhamburg.ti.inf.rnp.webServer;

import de.hawhamburg.ti.inf.rnp.webServer.src.WebServer;
import picocli.CommandLine;

public class WebServerMain {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new WebServer()).execute(args);
        System.exit(exitCode);
    }
}
