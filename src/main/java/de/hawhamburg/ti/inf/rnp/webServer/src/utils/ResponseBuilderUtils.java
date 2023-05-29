package de.hawhamburg.ti.inf.rnp.webServer.src.utils;

public final class ResponseBuilderUtils {
    public static final String SERVER_HEADER = "Server: RNP WebServer";
    public static final String CONTENT_TYPE = "Content-Type: ";
    public static final String RESPONSE_OKAY = "HTTP/1.1 200 OK";
    public static final String RESPONSE_BAD_REQUEST = "HTTP/1.1 400 BAD REQUEST";
    public static final String RESPONSE_NOT_FOUND = "HTTP/1.1 404 NOT FOUND";
    public static final String RESPONSE_REQUEST_HEADER_FIELDS_TOO_LARGE = "HTTP/1.1 431 REQUEST HEADER FIELDS TOO LARGE";
    public static final String RESPONSE_METHOD_NOT_ALLOWED = "HTTP/1.1 405 METHOD NOT ALLOWED";
    public static final String REQUEST_ENTITY_TOO_LARGE = "HTTP/1.1 413 REQUEST ENTITY TOO LARGE";
}
