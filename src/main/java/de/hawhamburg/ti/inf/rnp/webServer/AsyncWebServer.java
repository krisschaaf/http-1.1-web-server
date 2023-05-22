package de.hawhamburg.ti.inf.rnp.webServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AsyncWebServer implements Runnable {
    private int port;

    public AsyncWebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws IOException {
        ServerSocket s;

        System.out.println("Webserver starting up on port 80");
        System.out.println("(press ctrl-c to exit)");
        try {
            s = new ServerSocket(this.port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");

        while (true) {
            Socket remote = s.accept();

            Responder responder = new Responder(remote);
            responder.run();

            System.out.println("Connection, sending data.");
        }
    }
}
