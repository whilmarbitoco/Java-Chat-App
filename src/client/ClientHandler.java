package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientHandler implements Runnable{
    BufferedReader inReader;
    Client client;

    public ClientHandler(Client client) {
        inReader = new BufferedReader(new InputStreamReader(System.in));
        this.client = client;
    }

    @Override
    public void run() {
        try{
            while (!this.client.client.isClosed()) {
                String message = inReader.readLine();
                if (message.equals("/quit")) {
                    inReader.close();
                    client.shutdown();
                } else {
                    client.sendMessage(message);
                }
            }
        } catch (IOException e) {
            client.shutdown();
        }
    }
}
