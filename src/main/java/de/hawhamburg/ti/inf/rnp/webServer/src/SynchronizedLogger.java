package de.hawhamburg.ti.inf.rnp.webServer.src;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SynchronizedLogger {
    private static final SynchronizedLogger OBJ = new SynchronizedLogger();

    public static SynchronizedLogger getInstance() {
        return OBJ;
    }

    private synchronized void log(String value, String logFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileDescriptor fileDescriptor = null;

        try {
            fileOutputStream = new FileOutputStream(logFile, true);
            fileDescriptor = fileOutputStream.getFD();

            fileOutputStream.write(value.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();

            fileDescriptor.sync();
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if(fileOutputStream!=null)
                    fileOutputStream.close();
                if(fileInputStream!=null)
                    fileInputStream.close();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void logAccess(String clientAdress, String logFile) {
        StringBuilder value = new StringBuilder();
        value.append("Access by: " + clientAdress + " ");

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        value.append("at: " + timeStamp + ".");

        value.append("\r\n");

        this.log(value.toString(), logFile);
    }

    public void logResponse(String statusCode, String clientAdress, String filename, String logFile) {
        StringBuilder value = new StringBuilder();
        value.append("Response to: " + clientAdress + " ");

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        value.append("at: " + timeStamp + ", ");

        value.append("on file: " + filename + ", ");
        value.append("with status: " + statusCode + ".");
        value.append("\r\n");

        this.log(value.toString(), logFile);
    }

    public void logError(String errorMsg, String clientAdress, String logFile) {
        StringBuilder value = new StringBuilder();
        value.append("Error occured by: " + clientAdress + " ");

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        value.append("at: " + timeStamp + ", ");

        value.append("with following message: " + errorMsg + ".");

        value.append("\r\n");

        this.log(value.toString(), logFile);
    }
}
