package de.hawhamburg.ti.inf.rnp.webServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Responder implements Runnable {
    private Socket remote;

    public Responder(Socket socket) {
        this.remote = socket;
    }

    @Override
    public void run() {
        try {
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
