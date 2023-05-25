package de.hawhamburg.ti.inf.rnp.webServer.src.website;

import java.util.Date;

public class WebsiteFile {
    private String filename;

    private double length;

    private Date lastChanged;

    private String absolutePath;

    public WebsiteFile(
            String filename,
            double length,
            Date lastChanged,
            String absolutePath
    ) {
        this.filename = filename;
        this.length = length;
        this.lastChanged = lastChanged;
        this.absolutePath = absolutePath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
