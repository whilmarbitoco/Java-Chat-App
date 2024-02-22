package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{

    private int port;
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    public Server(int port) {
        this.port = port;
        this.connections = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
             server = new ServerSocket(port);
             System.out.println("Server running on port " + port);
           while (!server.isClosed()) {
               Socket client = server.accept();
               ConnectionHandler connectionHandler = new ConnectionHandler(client, this);
               connections.add(connectionHandler);
               Thread thread = new Thread(connectionHandler);
               thread.start();
           }

        } catch(IOException e) {
            shutdown();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
       try {

           if(!server.isClosed()) {
               server.close();
           }
           for (ConnectionHandler ch : connections) {
               ch.shutdown();
           }
       } catch (IOException e) {
           System.out.println("Server already close...");
       }
    }


}
