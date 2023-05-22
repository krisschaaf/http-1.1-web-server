package de.hawhamburg.ti.inf.rnp.get.src;

import static picocli.CommandLine.*;

@Command(name = "GetClient", mixinStandardHelpOptions = true)
public class GetClient implements Runnable {

    @Option(names = { "-h", "--host" }, description = "Host")
    private String host;

    @Option(names = { "-p", "--port" }, description = "Port")
    private int port = 80;

    @Option(names = { "-f", "--file" }, description = "File")
    private String file;

    @Option(names = { "-r", "--range" }, description = "Content-Range")
    private String contentRange = "-1";
    @Option(names = { "-s", "--slow"}, description = "Slow-Motion")
    private int[] slowMotion = {-1,-1};

    @Override
    public void run() {
        if(this.getPort()<=0 || this.getPort() > 65535) {
            System.err.println("Port must be between 1 and 65535");
            System.exit(-1);
        }
        if(this.getFile().isEmpty()) {
            System.err.println("Path cannot be empty");
            System.exit(-1);
        }

        System.out.println("Getting " + this.getFile() + " from " + this.getHost() + ":" + this.getPort());

        GetExecutor hg = new GetExecutor(this);
        hg.get();
    }

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
        return this.contentRange.matches("^[0-9]+-[0-9]+$");
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
