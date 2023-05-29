package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseBuilderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseHandlerUtils.getMimeType;

public class ResponseHandler implements Runnable {
    private static final int maxFileSize = 23; // TODO
    private final Socket remote;
    private final ResponseBuilder responseBuilder;
    private final Validator validator;
    private final SynchronizedLogger synchronizedLogger;
    private String logFile;

    public ResponseHandler(Socket socket, String logFile) {
        this.remote = socket;
        this.responseBuilder = new ResponseBuilder();
        this.validator = Validator.getInstance();
        this.logFile = logFile;
        this.synchronizedLogger = SynchronizedLogger.getInstance();
    }

    @Override
    public void run() {
        this.synchronizedLogger.logAccess(this.remote.getRemoteSocketAddress().toString(), this.logFile);

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(remote.getInputStream()));

            String requestAsString = "";
            List<String> requestAsList = new ArrayList<>(); // TODO remove
            String requestLine = bufferedReader.readLine();

            while(!requestLine.isEmpty()) {
                requestAsString += requestLine;
                requestAsList.add(requestLine); // TODO remove
                requestLine = bufferedReader.readLine();
            }

            // TODO use instead
//            List<String> requestAsList = Arrays.stream(requestAsString.split("\r\n")).collect(Collectors.toList());

            String filename = requestAsList.get(0).split(" ")[1];
            String mimeType = getMimeType(filename.substring(filename.lastIndexOf(".")));

            Optional<String> contentRange = Optional.empty();
            for (String req: requestAsList) {
                if (req.contains("Content Range")){
                    contentRange = Optional.of(req);
                }
            }

            // TODO REQUESTED_FILE_TOO_LARGE

            switch (this.validator.validateRequest(requestAsList)) {
                case BAD_REQUEST -> {
                    this.sendResponse(this.responseBuilder.respondWithBadRequest(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_BAD_REQUEST, remote.getRemoteSocketAddress().toString(), filename, logFile);
                }
                case HEADER_FIELDS_TOO_LARGE -> {
                    this.sendResponse(this.responseBuilder.respondWithRequestHeaderFieldsTooLarge(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE, remote.getRemoteSocketAddress().toString(), filename, logFile);
                }
                case NOT_FOUND -> {
                    this.sendResponse(this.responseBuilder.respondWithDirectoryListing(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_NOT_FOUND, remote.getRemoteSocketAddress().toString(), filename, logFile);
                }
                case METHOD_NOT_ALLOWED -> {
                    this.sendResponse(this.responseBuilder.respondWithMethodNowAllowed(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_METHOD_NOT_ALLOWED, remote.getRemoteSocketAddress().toString(), filename, logFile);
                }
                case OK -> {
                    this.sendResponse(this.responseBuilder.respondWithFileContent(filename, mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_OKAY, remote.getRemoteSocketAddress().toString(), filename, logFile);
                }
            }

            remote.close();
        } catch (Exception ex) {
            this.synchronizedLogger.logError(ex.getMessage(), this.remote.getRemoteSocketAddress().toString(), this.logFile);
        }
    }

    private void sendResponse(String response, Optional<String> contentRange) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(remote.getOutputStream());

            if (contentRange.isPresent()){
                printWriter.print(this.reduceResponseByContentRange(response, contentRange));
            } else {
                printWriter.print(response);
            }
        } catch (IOException ex) {
            this.synchronizedLogger.logError(ex.getMessage(), this.remote.getRemoteSocketAddress().toString(), this.logFile);
        } finally {
            printWriter.flush();
        }
    }

    // TODO implement me (copy and adjust code from getHandler)
    private String reduceResponseByContentRange(String response, Optional<String> contentRange) {
        return "";
    }
}
