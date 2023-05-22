package de.hawhamburg.ti.inf.rnp.webServer;
import de.hawhamburg.ti.inf.rnp.utils.WebServerUtils;

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

            String request = ".";

            while (!request.equals(""))
                request = in.readLine();

            if (!validateRequest(request)) {
                respondWithBadRequest(out);
            }

            System.out.println("Connection, sending data.");

            if(request.contains("/index.html")) {
                this.sendIndexHtmlResponse(out);
            } else {
                this.sendDefaultResponse(out);
            }

            remote.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void sendDefaultResponse(PrintWriter out) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: RNP WebServer");
        out.println(WebServerUtils.CONTENT_HEADER);
        out.println(WebServerUtils.CONTENT_PARAGRAPH);
        out.flush();
    }

    private void sendIndexHtmlResponse(PrintWriter out) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: RNP WebServer");
        out.println(WebServerUtils.INDEX_HTML);
        out.flush();
    }

    private boolean validateRequest(String request) {
        return request.matches(WebServerUtils.REQUEST_REGEX);
    }

    private void respondWithBadRequest(PrintWriter out) {
        //TODO: implement me
        out.println("HTTP/1.1 404 BAD REQUEST");
        out.println("Content-Type: text/html");
        out.println("Server: RNP WebServer");
        out.println("Invalid Request.");
        out.flush();
    }
}
