package server;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    private Server server;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    String nickname;


    public ConnectionHandler(Socket socket, Server server) {
        this.client = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            nickname = in.readLine();
            server.broadcast(nickname + " connected...");
            String message;
            while ((message = in.readLine()) != null) {
                handleMessage(message);
            }
        } catch (IOException e) {
            shutdown();
        }

    }

    public void sendMessage(String message) {

        out.println(message);
    }

    public void handleMessage(String message) {
        System.out.println(message);
        if (message.startsWith("/quit")) {
            server.broadcast(nickname + " left the chat...");
            shutdown();
        } else {
            server.broadcast("u/ " + nickname + " " + message);
        }
    }


    public void shutdown() {
      try{
          in.close();
          out.close();
          if (!this.client.isClosed()) {
              this.client.close();
          }
      } catch (IOException e) {
          System.out.println("Server already close");
      }
    }
}
