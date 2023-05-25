package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.DirectoryUtils;
import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseBuilderUtils;
import de.hawhamburg.ti.inf.rnp.webServer.src.website.DirectoryListing;

import java.io.*;

public class ResponseBuilder {
    private PrintWriter printWriter;
    private DirectoryListing directoryListing;

    public ResponseBuilder(PrintWriter printWriter) {
        this.printWriter = printWriter;
        this.directoryListing = DirectoryListing.getInstance();
    }

    public void sendDefaultResponse(String filename, String contentType) {
        printWriter.println(ResponseBuilderUtils.RESPONSE_OKAY);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE + contentType);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println(ResponseBuilderUtils.CONTENT_HEADER);
        String line;
        String resp = "";

        try {
            File file = new File(DirectoryUtils.ABSOLUTE_PATH + "/" + filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                resp += line;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println(resp);
        printWriter.flush();
    }

    public void sendDefaultResponseWithContentRange (String filename, String mimeType, String contentRange){

    }

    public void sendIndexHtmlResponse(String contentType) {
        printWriter.println(ResponseBuilderUtils.RESPONSE_OKAY);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE + contentType);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println(ResponseBuilderUtils.INDEX_HTML);
        printWriter.flush();
    }

    public void respondWithDirectoryListing(String contentType) {
        printWriter.println(ResponseBuilderUtils.RESPONSE_NOT_FOUND);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE + contentType);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println(this.directoryListing.getDirectoryListingAsHTML());
        printWriter.flush();
    }

    public void respondWithBadRequest(String contentType) {
        printWriter.println(ResponseBuilderUtils.RESPONSE_BAD_REQUEST);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE + contentType);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println("Invalid Request.");
        printWriter.flush();
    }

    public void respondWithRequestHeaderFieldsTooLarge(String contentType) {
        printWriter.println(ResponseBuilderUtils.RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE + contentType);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println("Request Header fields too large");
        printWriter.flush();
    }
}
