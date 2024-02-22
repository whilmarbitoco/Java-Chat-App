/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.util.ArrayList;

/**
 *
 * @author wb2c0
 */
public class Chat {
    private static ArrayList<String> chats = new ArrayList<>();
    
    public static void add(String chat) {
        chats.add(chat);
    }
    
    
    public static ArrayList<String> get() {
        return chats;
    }
}
