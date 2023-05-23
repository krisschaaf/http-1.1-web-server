package de.hawhamburg.ti.inf.rnp.webServer.src;

import de.hawhamburg.ti.inf.rnp.webServer.src.utils.ResponderUtils;

import java.util.List;

public final class Validator {

    public static StatusCode validateRequest(List<String> request) {
        if(!request.get(0).matches(ResponderUtils.REQUEST_REGEX)) {
            return StatusCode.BAD_REQUEST;
        }
        for (int i = 1; i < request.size(); i++) {
            if (!request.get(i).matches(ResponderUtils.REQUEST_HEADER_REGEX)) {
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
