package de.hawhamburg.ti.inf.rnp.webServer.src;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Command(name = "GetClient", mixinStandardHelpOptions = true)
public class WebServer implements Runnable {

    @Option(names = { "-p", "--port" }, description = "Port")
    private int port = 80;

    @Option(names = { "-t", "--threads" }, description = "Threads")
    private int threads = 10;

    @Option(names = { "-l", "--logFile" }, description = "LogFile")
    private String logFile;

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        ServerSocket socket;

        System.out.printf("Webserver starting up on port %d\r\n", port);
        System.out.println("(press ctrl-c to exit)");

        try {
            socket = new ServerSocket(this.port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.threads);

        while (true) {
            System.out.println("Waiting for connection");
            Socket remote = socket.accept();

            Thread thread = new Thread(new ResponseHandler(remote, this.logFile));
            executor.submit(() -> thread.start());
        }
    }
}
