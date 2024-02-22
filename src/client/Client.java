package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
     public Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private int port;
    private String host;


    public Client(String username, int port) {
        this.username = username;
        this.port = port;
        this.host = "127.0.0.1";
    }

    @Override
    public void run() {
        try {
            client = new Socket(this.host, this.port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            sendMessage(username);

            ClientHandler clientHandler = new ClientHandler(this);

            Thread thread = new Thread(clientHandler);
            thread.start();

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("u/")) {
                    String splitMsg[] = message.split(" ", 3);
                    if (splitMsg.length >= 3) {
                        String senderUsername = splitMsg[1];
                        if (!senderUsername.equals(username)) {
                            Chat.add(senderUsername + ": " + splitMsg[2]);
                        }
                    }
                } else {
                    Chat.add(message);
                }
            }


        } catch (IOException e) {
            System.out.println("error");
            shutdown();

        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void shutdown() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            System.out.println("Error while shutting down: " + e.getMessage());
        }
    }

}
