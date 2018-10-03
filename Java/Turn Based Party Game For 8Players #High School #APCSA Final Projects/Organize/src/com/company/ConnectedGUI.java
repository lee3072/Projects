package com.company;

import javax.swing.*;
import java.awt.*;

public class ConnectedGUI extends JFrame {
    public ConnectedGUI(){
        String string = "Server Started";
        setSize(200,50);
        setTitle(string);
        setLocationRelativeTo(null);                         //Make Window Open in the Center of the Screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //Make closing button close the window
        setBackground(Color.white);                          //Make Background Color White
        JLabel labelWarning = new JLabel(string);
        labelWarning.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelWarning);
    }
}
