package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponseBuilderUtils;

import java.io.PrintWriter;

public class ResponseBuilder {
    private PrintWriter printWriter;

    public ResponseBuilder(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void sendDefaultResponse() {
        printWriter.println(ResponseBuilderUtils.RESPONSE_OKAY);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE_HTML_HEADER);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println(ResponseBuilderUtils.CONTENT_HEADER);
        printWriter.println(ResponseBuilderUtils.CONTENT_PARAGRAPH);
        printWriter.flush();
    }

    public void sendIndexHtmlResponse() {
        printWriter.println(ResponseBuilderUtils.RESPONSE_OKAY);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE_HTML_HEADER);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println(ResponseBuilderUtils.INDEX_HTML);
        printWriter.flush();
    }

    public void respondWithBadRequest() {
        printWriter.println(ResponseBuilderUtils.RESPONSE_BAD_REQUEST);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE_HTML_HEADER);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println("Invalid Request.");
        printWriter.flush();
    }

    public void respondWithRequestHeaderFieldsTooLarge() {
        printWriter.println(ResponseBuilderUtils.RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE);
        printWriter.println(ResponseBuilderUtils.CONTENT_TYPE_HTML_HEADER);
        printWriter.println(ResponseBuilderUtils.SERVER_HEADER);
        printWriter.println("Request Header fields too large");
        printWriter.flush();
    }
}
