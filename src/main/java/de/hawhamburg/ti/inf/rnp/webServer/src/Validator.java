package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.website.DirectoryListing;

import java.util.Arrays;
import java.util.List;

public class Validator {
    private static final Validator OBJ = new Validator();
    private static final String REQUEST_REGEX = "^(GET|POST|PUT|DELETE|HEAD|OPTIONS|PATCH)[ ]+(\\/[\\w-]+)+\\.[\\w]+[ ]HTTP\\/1.1$";
    private static final String REQUEST_HEADER_REGEX = "^[\\w-]+:[ ][\\w\\.-]+$";
    private static final int MAX_REQUEST_SIZE_IN_BYTES = 16000;

    private final DirectoryListing directoryListing = DirectoryListing.getInstance();

    public static Validator getInstance() {
        return OBJ;
    }

    public StatusCode validateRequest(List<String> request) {
        String method = Arrays.stream(request.get(0).split(" ")).toList().get(0); // array operator '[0]' did for some reason not work
        String filename = Arrays.stream(request.get(0).split(" ")).toList().get(1);

        // check if first line matches pattern
        if(!request.get(0).matches(REQUEST_REGEX) || filename.contains("..")) {
            return StatusCode.BAD_REQUEST;
        }

        if(!method.equals("GET")) {
            return StatusCode.METHOD_NOT_ALLOWED;
        }

        // check if file exists
        if(!this.directoryListing.directoryContainsFile(filename)) {
            return StatusCode.NOT_FOUND;
        }

        // check if header matches pattern
        for (int i = 1; i < request.size(); i++) {
            if (!request.get(i).matches(REQUEST_HEADER_REGEX)) {
                return StatusCode.BAD_REQUEST;
            } else {
                if(request.get(i).length() > 100) {
                    return StatusCode.HEADER_FIELDS_TOO_LARGE;
                }
            }
        }

        // check if general request is too large
        if(!requestSizeOkay(request)) {
            return StatusCode.REQUEST_ENTITY_TOO_LARGE;
        }

        return StatusCode.OK;
    }

    private static boolean requestSizeOkay(List<String> request) {
        int size = 0;

        for (String row: request) {
            size += row.getBytes().length;
        }

        return size <= MAX_REQUEST_SIZE_IN_BYTES;
    }
}
