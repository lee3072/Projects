package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static String ipAddress ="";


    public static void main(String[] args) throws IOException, InterruptedException {

        SwingUtilities.invokeLater(() -> new IPAddressInput().setVisible(true));  //Starting visible updating GUI

        while (true){
            Thread.sleep(10);
            if(!ipAddress.isEmpty()){break;}
        }

        Client.connectToServer(ipAddress,12345);                    //Connects to the Server

    }
}