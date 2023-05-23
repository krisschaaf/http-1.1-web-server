package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponderUtils;

import java.util.List;

public final class Validator {
    public static final String REQUEST_REGEX = "^(GET|POST|PUT|DELETE|HEAD|OPTIONS|PATCH)[ ]+(\\/[\\w]+)+\\.[\\w]+[ ]HTTP\\/1.1$";
    public static final String REQUEST_HEADER_REGEX = "^[\\w-]+:[ ][\\w]+$";

    public static StatusCode validateRequest(List<String> request) {
        if(!request.get(0).matches(REQUEST_REGEX)) {
            return StatusCode.BAD_REQUEST;
        }
        for (int i = 1; i < request.size(); i++) {
            if (!request.get(i).matches(REQUEST_HEADER_REGEX)) {
                return StatusCode.BAD_REQUEST;
            } else {
                if(request.get(i).length() > 100) {
                    return StatusCode.HEADER_FIELDS_TOO_LARGE;
                }
            }
        }
        return StatusCode.OK;
    }
}
