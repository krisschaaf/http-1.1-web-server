package de.hawhamburg.ti.inf.rnp;

import picocli.CommandLine;

public class WebServer {
    public static void main(String[] args) {

        System.out.println("Argument count: " + args.length);

        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument " + i + ": " + args[i]);
        }

        ArgumentDecryptor argumentDecryptor = new ArgumentDecryptor();

        try {
            CommandLine.ParseResult parseResult = new CommandLine(argumentDecryptor).parseArgs(args);
            if (!CommandLine.printHelpIfRequested(parseResult)) {

                if(argumentDecryptor.getPort()<=0 || argumentDecryptor.getPort() > 65535) {
                    System.err.println("Port must be between 1 and 65535");
                    System.exit(-1);
                }
                if(argumentDecryptor.getFile().isEmpty()) {
                    System.err.println("Path cannot be empty");
                    System.exit(-1);
                }

                System.out.println("Getting " + argumentDecryptor.getFile() + " from " + argumentDecryptor.getHost() + ":" + argumentDecryptor.getPort());

                HttpGet hg = new HttpGet(argumentDecryptor.getHost(), argumentDecryptor.getPort(), argumentDecryptor.getFile());
            }
        } catch (CommandLine.ParameterException ex) {
            System.err.println(ex.getMessage());
            ex.getCommandLine().usage(System.err);
        }
    }
}
