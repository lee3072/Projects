package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IPAddressInput extends JFrame{
    public IPAddressInput() {

        setTitle("Please Input Server IP");                            //Title of Window
        setSize(250,50);                   //Size of Window
        setResizable(false);                                 //Fix the Size of Window
        setLocationRelativeTo(null);                         //Make Window Open in the Center of the Screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //Make closing button close the window
        setBackground(Color.white);                          //Make Background Color White
        JTextField ipInput = new JTextField();
        ipInput.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
        ipInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10&&!ipInput.getText().isEmpty()) {
                    Main.ipAddress = ipInput.getText();
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        add(ipInput);


    }
}
