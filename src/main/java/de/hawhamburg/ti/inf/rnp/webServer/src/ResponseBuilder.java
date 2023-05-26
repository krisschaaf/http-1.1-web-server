package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.DirectoryUtils;
import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseBuilderUtils;
import de.hawhamburg.ti.inf.rnp.webServer.src.website.DirectoryListing;

import java.io.*;

public class ResponseBuilder {
    private DirectoryListing directoryListing;

    public ResponseBuilder() {
        this.directoryListing = DirectoryListing.getInstance();
    }

    private String getFile(String filename) {
        String line;
        String resp = "";

        try {
            File file = new File(DirectoryUtils.ABSOLUTE_PATH + "/" + filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((line = bufferedReader.readLine()) != null) {
                resp += line;
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resp;
    }

    public String respondWithFileContent(String filename, String contentType) {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(ResponseBuilderUtils.RESPONSE_OKAY + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.CONTENT_TYPE + contentType + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.SERVER_HEADER + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.CONTENT_HEADER + "\r\n");
        stringBuilder.append(getFile(filename) + "\r\n");

        return stringBuilder.toString();
    }

    public String respondWithDirectoryListing(String contentType) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ResponseBuilderUtils.RESPONSE_NOT_FOUND + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.CONTENT_TYPE + contentType + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.SERVER_HEADER + "\r\n");
        stringBuilder.append(this.directoryListing.getDirectoryListingAsHTML() + "\r\n");

        return stringBuilder.toString();
    }

    public String respondWithBadRequest(String contentType) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ResponseBuilderUtils.RESPONSE_BAD_REQUEST + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.CONTENT_TYPE + contentType + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.SERVER_HEADER + "\r\n");
        stringBuilder.append("Invalid Request." + "\r\n");

        return stringBuilder.toString();
    }

    public String respondWithRequestHeaderFieldsTooLarge(String contentType) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ResponseBuilderUtils.RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.CONTENT_TYPE + contentType + "\r\n");
        stringBuilder.append(ResponseBuilderUtils.SERVER_HEADER + "\r\n");
        stringBuilder.append("Request Header fields too large" + "\r\n");

        return stringBuilder.toString();
    }
}
