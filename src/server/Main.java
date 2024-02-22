/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author wb2c0
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server(9990);
        server.run();
    }
   
}
