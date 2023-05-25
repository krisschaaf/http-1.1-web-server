package de.hawhamburg.ti.inf.rnp.webServer.src.utils;

import java.util.Map;

import static java.util.Map.entry;

public final class ResponseHandlerUtils {

    public static String getMimeType (String type){
        Map<String, String> mimeTypeMap = Map.ofEntries(
                entry(".txt", "text/plain; charset=utf-8"),
                entry(".html", "text/html; charset=utf-8"),
                entry(".htm", "text/html; charset=utf-8"),
                entry(".css", "text/css; charset=utf-8"),
                entry(".js", "application/javascript"),
                entry(".jpg", "image/jpeg"),
                entry(".jpeg", "image/jpeg"),
                entry(".png", "image/png"),
                entry(".pdf", "application/pdf"),
                entry("webp", "image/webp"),
                entry(".xml", "application/xml"),
                entry(".odt", "application/vnd.oasis.opendocument.text"),
                entry(".bib", "text/plain; charset=utf-8")
        );
        return mimeTypeMap.get(type);
    }
}
