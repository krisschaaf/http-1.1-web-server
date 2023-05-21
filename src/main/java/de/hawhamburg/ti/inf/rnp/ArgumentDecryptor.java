package de.hawhamburg.ti.inf.rnp;

import static picocli.CommandLine.*;

@Command(mixinStandardHelpOptions = true, version = "v3.0.0")
public class ArgumentDecryptor {

    @Parameters(index = "0", description = "Host")
    private String host;

    @Parameters(index = "1", description = "Port")
    private int port;

    @Parameters(index = "2", description = "File")
    private String file;

    @Option(names = { "-r", "--range" }, description = "Content-Range")
    private String contentRange;

    @Option(names = { "-s", "--slow"}, description = "Slow-Motion")
    private int[] slowMotion;

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getFile() {
        return this.file;
    }

    private boolean contentRangeContainsEnd() {
        return this.contentRange.contains("-");
    }

    public int getContentRangeStart() {
        if (contentRangeContainsEnd()) {
            String[] contentRanges = this.contentRange.split("-");
            return Integer.parseInt(contentRanges[0]);
        } else {
            return Integer.parseInt(contentRange);
        }
    }

    public int getContentRangeEnd() {
        if (contentRangeContainsEnd()) {
            String[] contentRanges = this.contentRange.split("-");
            return Integer.parseInt(contentRanges[1]);
        } else {
            return -1;
        }
    }

    public int getSlowMoBytes() {
        return this.slowMotion[0];
    }

    public int getSlowMoTime() {
        return this.slowMotion[1];
    }
}
