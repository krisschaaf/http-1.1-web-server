package de.hawhamburg.ti.inf.rnp.webServer.src;

import picocli.CommandLine.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Command(name = "GetClient", mixinStandardHelpOptions = true)
public class WebServer implements Runnable {

    @Option(names = { "-p", "--port" }, description = "Port")
    private int port = 80;

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        ServerSocket s;

        System.out.printf("Webserver starting up on port %d\r\n", port);
        System.out.println("(press ctrl-c to exit)");

        try {
            s = new ServerSocket(this.port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        while (true) {
            System.out.println("Waiting for connection");
            Socket remote = s.accept();

            Thread thread = new Thread(new Responder(remote));
            thread.start();
        }
    }
}
