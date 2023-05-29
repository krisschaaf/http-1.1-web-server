package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseBuilderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseHandlerUtils.getMimeType;

public class ResponseHandler implements Runnable {
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
            String requestLine = bufferedReader.readLine();

            while(!requestLine.isEmpty()) {
                requestAsString += requestLine + "\r\n";
                requestLine = bufferedReader.readLine();
            }

            List<String> requestAsList = Arrays.stream(requestAsString.split("\r\n")).collect(Collectors.toList());

            String fileName = requestAsList.get(0).split(" ")[1];

            String fileEnding = "";

            try {
                fileName.substring(fileName.lastIndexOf("."));
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }

            String mimeType = getMimeType(fileEnding);

            Optional<String> contentRange = Optional.empty();
            for (String req: requestAsList) {
                if (req.contains("Content-Range")){
                    contentRange = Optional.of(req);
                }
            }

            switch (this.validator.validateRequest(requestAsList)) {
                case BAD_REQUEST -> {
                    this.sendResponse(this.responseBuilder.respondWithBadRequest(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_BAD_REQUEST, remote.getRemoteSocketAddress().toString(), fileName, logFile);
                }
                case HEADER_FIELDS_TOO_LARGE -> {
                    this.sendResponse(this.responseBuilder.respondWithRequestHeaderFieldsTooLarge(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE, remote.getRemoteSocketAddress().toString(), fileName, logFile);
                }
                case NOT_FOUND -> {
                    this.sendResponse(this.responseBuilder.respondWithDirectoryListing(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_NOT_FOUND, remote.getRemoteSocketAddress().toString(), fileName, logFile);
                }
                case METHOD_NOT_ALLOWED -> {
                    this.sendResponse(this.responseBuilder.respondWithMethodNowAllowed(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_METHOD_NOT_ALLOWED, remote.getRemoteSocketAddress().toString(), fileName, logFile);
                }
                case REQUEST_ENTITY_TOO_LARGE -> {
                    this.sendResponse(this.responseBuilder.respondWithRequestEntityLooLarge(mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.REQUEST_ENTITY_TOO_LARGE, remote.getRemoteSocketAddress().toString(), fileName, logFile);
                }
                case OK -> {
                    this.sendResponse(this.responseBuilder.respondWithFileContent(fileName, mimeType), contentRange);
                    this.synchronizedLogger.logResponse(ResponseBuilderUtils.RESPONSE_OKAY, remote.getRemoteSocketAddress().toString(), fileName, logFile);
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

    private String reduceResponseByContentRange(String response, Optional<String> contentRange) {
        String responseHeaders = response.split(ResponseBuilderUtils.SERVER_HEADER)[0];
        String responseContent = response.split(ResponseBuilderUtils.SERVER_HEADER)[1];

        byte[] responseContentInBytes = responseContent.getBytes();

        String contentRangeValues = contentRange.get().split("Content-Range: ")[1];
        String reducedResponseContent = "";

        try {
            if(contentRangeValues.contains("-")) {
                int contentRangeStart = Integer.parseInt(contentRangeValues.split("-")[0]);
                int contentRangeEnd = Integer.parseInt(contentRangeValues.split("-")[1]);

                reducedResponseContent = new String(Arrays.copyOfRange(responseContentInBytes, contentRangeStart, contentRangeEnd), StandardCharsets.UTF_8);
            } else {
                int contentRangeStart = Integer.parseInt(contentRangeValues);

                reducedResponseContent = new String(Arrays.copyOfRange(responseContentInBytes, contentRangeStart, responseContentInBytes.length), StandardCharsets.UTF_8);
            }
        } catch (Exception ex) {
            //TODO refactor
            if(ex instanceof ArrayIndexOutOfBoundsException || ex instanceof IllegalArgumentException) {
                this.responseBuilder.respondWithBadRequest("");
            }
        }

        return responseHeaders +
                ResponseBuilderUtils.SERVER_HEADER +
                reducedResponseContent;
    }
}
