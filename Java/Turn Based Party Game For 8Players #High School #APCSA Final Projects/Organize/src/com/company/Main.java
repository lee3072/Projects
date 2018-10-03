package com.company;

import javax.swing.*;
import java.io.IOException;


public class Main {
    public static String ipAddress="";
    public static void main(String[] args) throws IOException, InterruptedException {
        SwingUtilities.invokeLater(() -> new IPAddressInput().setVisible(true));  //Starting visible updating GUI
        while (true){
            Thread.sleep(10);
            if(!ipAddress.isEmpty()){break;}
        }
        new Game();                                                  //Starts A New Game
        Server.initializeServer(ipAddress,12345);     //Starts the Server in inserted ip and port

    }
}

