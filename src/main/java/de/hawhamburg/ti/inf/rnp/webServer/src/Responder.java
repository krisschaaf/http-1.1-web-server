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

            switch (Validator.validateRequest(request)) {
                case BAD_REQUEST:
                    this.responseBuilder.respondWithBadRequest();
                    break;
                case HEADER_FIELDS_TOO_LARGE:
                    this.responseBuilder.respondWithRequestHeaderFieldsTooLarge();
                    break;
                case OK:
                    this.checkForFiles(request.get(0).split(" ")[1]);
                    break;
            }

            remote.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void checkForFiles(String file) {
        if(file.equals("/index.html")) {
            this.responseBuilder.sendIndexHtmlResponse();
        } else {
            this.responseBuilder.sendDefaultResponse();
        }
    }
}
