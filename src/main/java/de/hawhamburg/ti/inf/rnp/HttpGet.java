package de.hawhamburg.ti.inf.rnp;

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
        String host = "";
        int port = 80;
        String file = "";

        if(args.length<3){
            printUsage();
            System.exit(-1);
        } else if(args.length<4) {
            host = args[0];
            port = Integer.parseInt(args[1]);
            if(port<=0 || port > 65535) {
                System.err.println("Port must be between 1 and 65535");
                System.exit(-1);
            }
            file = args[2];
            if(file.isEmpty()) {
                System.err.println("Path cannot be empty");
                System.exit(-1);
            }
        }
        System.out.println("Getting " + file + " from " + host + ":" + port);
        HttpGet hg = new HttpGet(host, port, file);
    }

    private static void printUsage() {
        System.out.println("Usage:\t HttpGet <Remote Host> <Remote Service or Port> <Requested file>");
    }
}
