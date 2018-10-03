package com.company;

import javax.swing.*;
import java.awt.*;

public class WarningGUI extends JFrame {
    public WarningGUI(String warning){
        setSize(warning.length()*10,50);
        setTitle("Warning");
        setLocationRelativeTo(null);                         //Make Window Open in the Center of the Screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //Make closing button close the window
        setBackground(Color.white);                          //Make Background Color White
        JLabel labelWarning = new JLabel(warning);
        labelWarning.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelWarning);
    }
}
