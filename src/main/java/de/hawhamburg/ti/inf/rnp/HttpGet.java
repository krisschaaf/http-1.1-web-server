package de.hawhamburg.ti.inf.rnp;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpGet {
    HttpGet(String remoteHost, int port, String file){
        try {
            Socket s = new Socket(remoteHost, port);

            PrintWriter wtr = new PrintWriter(s.getOutputStream());
            wtr.printf("GET %s HTTP/1.1\r\n", file);
            wtr.printf("Host: %s\r\n", remoteHost);
            wtr.printf("Connection: close\r\n\r\n");
            wtr.flush();

            BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String outStr;

            while((outStr = bufRead.readLine()) != null) {
                System.out.println(outStr);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
            printUsage();
            System.err.println(ex.getMessage());
            ex.getCommandLine().usage(System.err);
        }
    }

    private static void printUsage() {
        System.out.println("Usage:\t HttpGet <Remote Host> <Remote Service or Port> <Requested file>");
    }
}
