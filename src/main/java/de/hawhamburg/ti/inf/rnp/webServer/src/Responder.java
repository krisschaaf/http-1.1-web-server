package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.WebServerUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Responder implements Runnable {
    private Socket remote;

    public Responder(Socket socket) {
        this.remote = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(
                    remote.getInputStream()));

            PrintWriter out = new PrintWriter(remote.getOutputStream());

            String requestLine = "";
            List<String> request = new ArrayList<>();

            while((requestLine = bufRead.readLine()) != null) {
                    request.add(requestLine);
            }

            if (!validateRequest(request)) {
                respondWithBadRequest(out);
            }

            System.out.println("Connection, sending data.");

            if(requestLine.contains("/index.html")) {
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

    private boolean validateRequest(List<String> request) {
        return request.get(0).matches(WebServerUtils.REQUEST_REGEX);
    }

    private void respondWithBadRequest(PrintWriter out) {
        out.println("HTTP/1.1 404 BAD REQUEST");
        out.println("Content-Type: text/html");
        out.println("Server: RNP WebServer");
        out.println("Invalid Request.");
        out.flush();
    }
}
