package de.hawhamburg.ti.inf.rnp.webServer.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ResponseHandler implements Runnable {
    private final Socket remote;
    private final ResponseBuilder responseBuilder;

    public ResponseHandler(Socket socket) throws IOException {
        this.remote = socket;
        this.responseBuilder = new ResponseBuilder(new PrintWriter(remote.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(remote.getInputStream()));

            List<String> request = new ArrayList<>();
            String requestLine = bufferedReader.readLine();

            while(!requestLine.isEmpty()) {
                request.add(requestLine);
                requestLine = bufferedReader.readLine();
            }

            switch (Validator.validateRequest(request)) {
                case BAD_REQUEST -> this.responseBuilder.respondWithBadRequest();
                case HEADER_FIELDS_TOO_LARGE -> this.responseBuilder.respondWithRequestHeaderFieldsTooLarge();
                case OK -> {
                    String filename = request.get(0).split(" ")[1];
                    this.checkForFiles(filename);
                }
            }

            remote.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void checkForFiles(String filename) {
        if(filename.equals("/index.html")) {
            this.responseBuilder.sendIndexHtmlResponse();
        } else {
            this.responseBuilder.sendDefaultResponse();
        }
    }
}
