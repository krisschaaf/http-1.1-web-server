package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.website.DirectoryListing;

import java.util.List;

public class Validator {
    private static final Validator OBJ = new Validator();
    public static final String REQUEST_REGEX = "^(GET)[ ]+(\\/[\\w]+)+\\.[\\w]+[ ]HTTP\\/1.1$";
    public static final String REQUEST_HEADER_REGEX = "^[\\w-]+:[ ][\\w]+$";
    public final DirectoryListing directoryListing = DirectoryListing.getInstance();

    public static Validator getInstance() {
        return OBJ;
    }

    //TODO add Method not allowed


    public StatusCode validateRequest(List<String> request) {
        // check if first line matches pattern
        if(!request.get(0).matches(REQUEST_REGEX)) {
            return StatusCode.BAD_REQUEST;
        }

        String filename = request.get(0).split(" ")[1];

        // check if file exists
        if(!this.directoryListing.directoryContainsFile(filename.substring(1))) {
            return StatusCode.NOT_FOUND;
        }

        // TODO fix
//        check if header matches pattern
//        for (int i = 1; i < request.size(); i++) {
//            if (!request.get(i).matches(REQUEST_HEADER_REGEX)) {
//                return StatusCode.BAD_REQUEST;
//            } else {
//                if(request.get(i).length() > 100) {
//                    return StatusCode.HEADER_FIELDS_TOO_LARGE;
//                }
//            }
//        }

        return StatusCode.OK;
    }
}
