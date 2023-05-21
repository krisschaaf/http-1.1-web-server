package de.hawhamburg.ti.inf.rnp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpGet {

    HttpGet(WebServer webServer){
        String remoteHost = webServer.getHost();
        int port = webServer.getPort();
        String file = webServer.getFile();

        try {
            Socket s = new Socket(remoteHost, port);

            PrintWriter wtr = new PrintWriter(s.getOutputStream());
            wtr.printf("GET %s HTTP/1.1\r\n", file);
            wtr.printf("Host: %s\r\n", remoteHost);
            wtr.printf("Connection: close\r\n\r\n");
            wtr.flush();

            BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String outStr;

            if(webServer.getContentRangeStart() == -1) {
                //The whole Data will be passed
                while((outStr = bufRead.readLine()) != null) {
                    System.out.println(outStr);
                }
            } else if (webServer.getContentRangeStart() != 1 && webServer.getContentRangeEnd() == -1) {
                // Data from given Content-Range start will be passed
                int passedDataInBytes = 0;

                while((outStr = bufRead.readLine()) != null) { //TODO read by byte and not by line
                    passedDataInBytes += outStr.getBytes().length;
                    if(passedDataInBytes > webServer.getContentRangeStart()) {
                        System.out.println(outStr);
                    }
                }
            } else if (webServer.getContentRangeStart() != 1 && webServer.getContentRangeEnd() != -1) {
                // Data in between given Content-Range start and Content-Range end will be passed
                int passedDataInBytes = 0;

                while((outStr = bufRead.readLine()) != null) { //TODO read by byte and not by line
                    passedDataInBytes += outStr.getBytes().length;
                    if(passedDataInBytes > webServer.getContentRangeStart()) {
                        System.out.println(outStr);
                    }
                    if(passedDataInBytes > webServer.getContentRangeEnd()) {
                        break;
                    }
                }
            } else {
                System.err.println("No valid Content-Range.");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
