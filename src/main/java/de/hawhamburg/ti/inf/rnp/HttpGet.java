package de.hawhamburg.ti.inf.rnp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpGet {
    private WebServer webServer;

    HttpGet(WebServer webServer){
        this.webServer = webServer;
    }

    public void get() {
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
            getBytesFromReader(bufRead);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void getBytesFromReader(BufferedReader bufRead) throws IOException {
        int byteValue;
        int passedDataInBytes = 0;
        List<Byte> bytesToStream = new ArrayList<>();

        if(webServer.getContentRangeStart() == -1) {
            //The whole Data will be passed
            while((byteValue = bufRead.read()) != -1) {
                byte streamedByte = (byte) byteValue;
                    bytesToStream.add(streamedByte);
            }
        } else if (webServer.getContentRangeStart() != -1 && webServer.getContentRangeEnd() == -1) {
            // Data from given Content-Range start will be passed
            while((byteValue = bufRead.read()) != -1) {
                byte streamedByte = (byte) byteValue;
                passedDataInBytes ++;
                if(passedDataInBytes > webServer.getContentRangeStart()) {
                    bytesToStream.add(streamedByte);
                }
            }
        } else if (webServer.getContentRangeStart() != -1 && webServer.getContentRangeEnd() != -1) {
            // Data in between given Content-Range start and Content-Range end will be passed
            while((byteValue = bufRead.read()) != -1) {
                byte streamedByte = (byte) byteValue;
                passedDataInBytes ++;
                if(passedDataInBytes > webServer.getContentRangeStart()) {
                    bytesToStream.add(streamedByte);
                }
                if(passedDataInBytes > webServer.getContentRangeEnd()) {
                    break;
                }
            }
        } else {
            System.err.println("No valid Content-Range.");
            System.exit(-1);
        }

        convertListToByteArray(bytesToStream);
    }

    private void convertListToByteArray(List<Byte> bytesToStream) {
        byte[] byteArray = new byte[bytesToStream.size()];
        for (int i = 0; i < bytesToStream.size(); i++) {
            byteArray[i] = bytesToStream.get(i);
        }
        send(byteArray);
    }

    private void send(byte[] bytes) {
        if(webServer.getSlowMoBytes() != -1 && webServer.getSlowMoTime() != -1) {
            sendInSlowMo(bytes);
        } else {
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }
    }

    private void sendInSlowMo(byte[] bytes) {
        try {
            for (int i = 0; i < bytes.length; i += webServer.getSlowMoBytes()) {
                int endIndex = Math.min(i + webServer.getSlowMoBytes(), bytes.length);
                byte[] slice = Arrays.copyOfRange(bytes, i, endIndex);

                String sliceText = new String(slice, StandardCharsets.UTF_8);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(webServer.getSlowMoTime());
                            System.out.println(sliceText);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
