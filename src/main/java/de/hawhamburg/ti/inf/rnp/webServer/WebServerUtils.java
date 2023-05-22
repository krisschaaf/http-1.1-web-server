package de.hawhamburg.ti.inf.rnp.webServer;

public class WebServerUtils {
    public static final String CONTENT_HEADER = "<h1>Welcome to the Computer Networking Mini-WebServer</h2>";
    public static final String CONTENT_PARAGRAPH =
            "<p></p>";

    public static final String INDEX_HTML =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "    <head>\n" +
                    "        <title>Index of /</title>\n" +
                    "        <link rel=\"stylesheet\" href=\"/system/essen.css\" type=\"text/css\"/>\n" +
                    "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
                    "        <link rel=\"shortcut icon\" href=\"/system/graphics/icons/icon-simula.ico\"/>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>\n" +
                    "            NorNet Server <em>packages.nntb.no</em>\n" +
                    "        </h1>\n" +
                    "        <h2>Directory: /</h2>\n" +
                    "        <table id=\"indexlist\">\n" +
                    "            <tr class=\"indexhead\">\n" +
                    "                <th class=\"indexcolicon\">\n" +
                    "                    <img src=\"/icons/blank.gif\" alt=\"[ICO]\"/>\n" +
                    "                </th>\n" +
                    "                <th class=\"indexcolname\">\n" +
                    "                    <a href=\"?C=N;O=A\">Name</a>\n" +
                    "                </th>\n" +
                    "                <th class=\"indexcollastmod\">\n" +
                    "                    <a href=\"?C=M;O=A\">Last modified</a>\n" +
                    "                </th>\n" +
                    "                <th class=\"indexcolsize\">\n" +
                    "                    <a href=\"?C=S;O=A\">Size</a>\n" +
                    "                </th>\n" +
                    "                <th class=\"indexcoldesc\">\n" +
                    "                    <a href=\"?C=D;O=A\">Description</a>\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"indexbreakrow\">\n" +
                    "                <th colspan=\"5\">\n" +
                    "                    <hr/>\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"even\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"software/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"software/\">software/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2023-02-08 11:58  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"odd\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"rserpooldemo/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"rserpooldemo/\">rserpooldemo/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2023-02-17 12:13  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"even\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"openstack/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"openstack/\">openstack/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2023-05-08 11:39  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"odd\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"nornet-kernel/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"nornet-kernel/\">nornet-kernel/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2023-02-14 11:43  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"even\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"nornet-applications/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"nornet-applications/\">nornet-applications/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2017-09-13 16:05  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"odd\">\n" +
                    "                <td class=\"indexcolicon\">\n" +
                    "                    <a href=\"haw/\">\n" +
                    "                        <img src=\"/graphics/places/folder-documents.png\" alt=\"[DIR]\"/>\n" +
                    "                    </a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcolname\">\n" +
                    "                    <a href=\"haw/\">haw/</a>\n" +
                    "                </td>\n" +
                    "                <td class=\"indexcollastmod\">2023-04-15 21:10  </td>\n" +
                    "                <td class=\"indexcolsize\">- </td>\n" +
                    "                <td class=\"indexcoldesc\">&nbsp;</td>\n" +
                    "            </tr>\n" +
                    "            <tr class=\"indexbreakrow\">\n" +
                    "                <th colspan=\"5\">\n" +
                    "                    <hr/>\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "        <p class=\"description\">\n" +
                    "            This server is part of the <a href=\"https://www.nntb.no/\">NorNet</a>\n" +
                    "            testbed build infrastructure. Please have a look at\n" +
                    "<a href=\"https://www.nntb.no/\">https://www.nntb.no/</a>\n" +
                    "            for details!\n" +
                    "\n" +
                    "        </p>\n" +
                    "        <p class=\"description\">\n" +
                    "            Mon May 22 7:13:12 UTC 2023 – <a href=\"https://packages.nntb.no\">packages.nntb.no</a>\n" +
                    "        </p>\n" +
                    "    </body>\n" +
                    "</html>\n";
}
