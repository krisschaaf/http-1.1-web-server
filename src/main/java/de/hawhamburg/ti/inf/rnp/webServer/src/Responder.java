package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Responder implements Runnable {
    private Socket remote;
    private ResponseBuilder responseBuilder;

    public Responder(Socket socket) throws IOException {
        this.remote = socket;
        this.responseBuilder = new ResponseBuilder(new PrintWriter(remote.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(remote.getInputStream()));

            List<String> request = new ArrayList<>();
            String requestLine = bufferedReader.readLine();;

            while(!requestLine.isEmpty()) {
                request.add(requestLine);
                requestLine = bufferedReader.readLine();
            }

            if (!validateRequest(request)) {
                this.responseBuilder.respondWithBadRequest();
            }

            System.out.println("Connection, sending data.");

            if(request.get(0).contains("/index.html")) {
                this.responseBuilder.sendIndexHtmlResponse();
            } else {
                this.responseBuilder.sendDefaultResponse();
            }

            remote.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private boolean validateRequest(List<String> request) {
        if(!request.get(0).matches(ResponderUtils.REQUEST_REGEX)) {
            return false;
        }
        for (int i = 1; i < request.size(); i++) {
            if (!request.get(i).matches(ResponderUtils.REQUEST_HEADER_REGEX)) {
                return false;
            } else {
                if(request.get(i).length() > 100) {
                    this.responseBuilder.respondWithRequestHeaderFieldsTooLarge();
                }
            }
        }
        return true;
    }
}
