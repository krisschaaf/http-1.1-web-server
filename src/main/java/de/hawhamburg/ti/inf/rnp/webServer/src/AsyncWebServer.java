package de.hawhamburg.ti.inf.rnp.webServer.src;

import picocli.CommandLine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@CommandLine.Command(name = "GetClient", mixinStandardHelpOptions = true)
public class AsyncWebServer implements Runnable {

    @CommandLine.Option(names = { "-p", "--port" }, description = "Port")
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

        System.out.println("Webserver starting up on port 80");
        System.out.println("(press ctrl-c to exit)");
        try {
            s = new ServerSocket(this.port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");

        while (true) {
            Socket remote = s.accept();

            Responder responder = new Responder(remote);
            responder.run();
        }
    }
}
