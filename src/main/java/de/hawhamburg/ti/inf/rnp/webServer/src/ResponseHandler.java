package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.website.DirectoryListing;

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

    private final DirectoryListing directoryListing;

    public ResponseHandler(Socket socket) throws IOException {
        this.remote = socket;
        this.responseBuilder = new ResponseBuilder(new PrintWriter(remote.getOutputStream()));
        this.directoryListing = DirectoryListing.getInstance();
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
                    if(this.directoryListing.directoryContainsFile(filename)) {
                        this.returnFileAsResponse(filename);
                    } else {
                        this.returnDirectoryListingAsResponse();
                    }
                }
            }

            remote.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void returnDirectoryListingAsResponse() {
        // TODO implement me
    }

    private void returnFileAsResponse(String filename) {
        //TODO implement me
    }
}
