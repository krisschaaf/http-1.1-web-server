package de.hawhamburg.ti.inf.rnp.webServer;

import java.io.BufferedReader;
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
        this.start();
    }

    private void start() {
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
            try {
                Socket remote = s.accept();

                System.out.println("Connection, sending data.");

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        remote.getInputStream()));

                PrintWriter out = new PrintWriter(remote.getOutputStream());

                String str = ".";

                while (!str.equals(""))
                    str = in.readLine();

                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: text/html");
                out.println("Server: RNP WebServer");
                out.println(WebServerUtils.CONTENT_HEADER);
                out.println(WebServerUtils.CONTENT_PARAGRAPH);
                out.flush();

                remote.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
