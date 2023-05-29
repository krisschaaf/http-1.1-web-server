package de.hawhamburg.ti.inf.rnp.get.src;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetHandler {
    private GetClient getClient;

    GetHandler(GetClient getClient){
        this.getClient = getClient;
    }

    public void get() {
        String remoteHost = getClient.getHost();
        int port = getClient.getPort();
        String file = getClient.getFile();

        try {
            Socket socket = new Socket(remoteHost, port);

            StringBuilder request = new StringBuilder();
            request.append("GET " + file +  " HTTP/1.1\r\n");
            request.append("Host: " + remoteHost + "\r\n");

            if(this.getClient.getContentRangeStart() != -1 && this.getClient.getContentRangeEnd() == -1) {
                request.append("Content-Range: " + this.getClient.getContentRangeStart() + "\r\n");
            } else if (this.getClient.getContentRangeStart() != -1 && this.getClient.getContentRangeEnd() != 1) {
                request.append("Content-Range: " + this.getClient.getContentRangeStart() + "-" + this.getClient.getContentRangeEnd() + "\r\n");
            }

            request.append("Connection: close\r\n\r");

            PrintWriter wtr = new PrintWriter(socket.getOutputStream());

            if(getClient.getSlowMoBytes() != -1 && getClient.getSlowMoTime() != -1) {
                byte[] requestAsBytes = request.toString().getBytes();

                for (int i = 0; i < requestAsBytes.length; i += getClient.getSlowMoBytes()) {
                    int endIndex = Math.min(i + getClient.getSlowMoBytes(), requestAsBytes.length);
                    byte[] slice = Arrays.copyOfRange(requestAsBytes, i, endIndex);

                    String sliceText = new String(slice, StandardCharsets.UTF_8);

                    wtr.println(sliceText);
                    wtr.flush();

                    Thread.sleep(getClient.getSlowMoTime());
                }
            } else {
                wtr.println(request);
                wtr.flush();
            }

            BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            getBytesFromReader(bufRead);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBytesFromReader(BufferedReader bufRead) throws IOException {
        int byteValue;
        List<Byte> bytesToStream = new ArrayList<>();

        //The whole Data will be passed
        while((byteValue = bufRead.read()) != -1) {
            byte streamedByte = (byte) byteValue;
                bytesToStream.add(streamedByte);
        }

        send(convertListToByteArray(bytesToStream));
    }

    private byte[] convertListToByteArray(List<Byte> bytesToStream) {
        byte[] byteArray = new byte[bytesToStream.size()];
        for (int i = 0; i < bytesToStream.size(); i++) {
            byteArray[i] = bytesToStream.get(i);
        }
        return byteArray;
    }

    private void send(byte[] bytes) {
        saveText(new String(bytes, StandardCharsets.UTF_8));
    }

    private void saveText(String text) {
        System.out.println(text);
    }

}
