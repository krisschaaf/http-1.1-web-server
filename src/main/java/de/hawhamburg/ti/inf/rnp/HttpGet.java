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
}
