package de.hawhamburg.ti.inf.rnp.get;

import de.hawhamburg.ti.inf.rnp.get.src.GetClient;
import picocli.CommandLine;

public class GetMain {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GetClient()).execute(args);
        System.exit(exitCode);
    }
}
