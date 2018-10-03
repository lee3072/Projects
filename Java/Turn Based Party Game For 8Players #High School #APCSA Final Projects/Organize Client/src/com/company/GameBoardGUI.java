package com.company;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameBoardGUI extends JFrame {
    private static String allVictoryConditions= "All Victory Conditions:" + newLine()+
                    "   Victory Condition:\n        Survival of Donalbain\n        Survival of Malcome\n        Survival of Self for 6 turn"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of self\n        Survival of Fleance\n        Death of Macbeth"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of self\n        Death of Duncan\n        Death of Macbeth"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of Duncan\n        Survival of Malcom\n        Death of Macbeth\n        Death of Donalbain"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of self\n        Survival of Duncan\n        Death of Malcom"+newLine()+newLine()+
                    "   Victory Condition:\n        Death of Duncan\n        Survival of Macbeth\n        Death of Malcom or Macduff\n        Death of Banquo"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of self\n        Death of Macbeth\n        Death of Malcom\n        Death of Donalbain\n        Death of Banquo"+newLine()+newLine()+
                    "   Victory Condition:\n        Survival of self\n        Death of Duncan\n        Death of Malcom or Macduff\n        Death of Banquo";

    /**Panels that Separates Sectors of GUI*/
    private static JPanel topPanel = new JPanel();      // Panel that contains Objects related to Game System      (Located in Left Top Area of Screen)
    private static JPanel bottomPanel = new JPanel();   // Panel that contains Objects related to Message System   (Located in Left Bottom Area of Screen)
    private static JPanel rightPanel = new JPanel();    // Panel that contains victory conditions of all players

    /**Objects Contained in the topPanel (Game System)*/
    private static JLabel labelMyPlayerIndex = new JLabel();             //Label that displays Player Index of the Client
    private static JLabel labelGameStatus = new JLabel();                //Label that displays whether the Game Started or not
    private static JLabel labelName = new JLabel();                      //Label that displays Name of the Character Client received
    private static JLabel labelPosition = new JLabel();                  //Label that displays Position of the Character Client received
    private static JButton buttonFind = new JButton("Find");        //Button for ToFind Ability (dataOutput with text: "(Server)"+"(ToFind)"+"(CharacterOfPlayer)"+abilityTowardsPlayerIndex)
    private static JButton buttonKill= new JButton("Kill");         //Button for ToKill Ability (dataOutput with text: "(Server)"+"(ToKill)"+abilityTowardsPlayerIndex)
    private static JButton buttonEndTurn = new JButton("EndTurn");  //Button for Ending Player's Turn (dataOutput with text: "(EndTurn)")
    private static JLabel labelCurrentRound = new JLabel();              //Label that displays Current Round of the Game
    private static JLabel labelCurrentPlayer = new JLabel();             //Label that displays Current Player of the Game
    private static JTextArea textAreaAbilities = new JTextArea();        //TextArea displays Passive and Active Abilities the Client can use
    private static ImageIcon imageIcon;                                  //Image for icon of Character
    private static JLabel labelIcon;                                     //Label for displaying Image
    private static JTextArea textAreaVictoryCondition = new JTextArea(); //TextArea displays Victory Condition of the Client
    private static JTextArea textAreaServerOutput = new JTextArea();     //TextArea displays Output Message from Server
    private static JScrollPane scrollPaneTextAreaServerOutput =          //Scroll Pane contains TextAreaServerOutput
            new JScrollPane(
                    textAreaServerOutput,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
    private static JLabel labelVictory = new JLabel();                   //Label that displays if any Player has gained Victory
    private static String[] stringAbilityTowardsPlayerIndex = {          //String Array Contains Selectable Players for using Ability
            "Player 0","Player 1","Player 2","Player 3","Player 4","Player 5","Player 6","Player 7"
    };
    private static JComboBox comboBoxAbilityTowardsPlayerIndex =         //Combo Box for Selecting Players for using Ability
            new JComboBox(stringAbilityTowardsPlayerIndex);
    private static int abilityTowardsPlayerIndex = 0;                    //Identifier for Index of Player Ability will be used


    /**Objects Contained in the bottomPanel (Message System)*/
    private static JTextArea textAreaMessage = new JTextArea();
    private static JScrollPane scrollPaneTextAreaMessage=                                                                   //Scroll Pane contains TextAreaMessage
            new JScrollPane(
                    textAreaMessage,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
    private static JTextField textFieldMessage = new JTextField();                                                          //Text Field for Message Entrance
    private static String[] stringMessageModeSelection = {"Public Message","Private Message"};                              //String Array Contains Selectable Mode for sending Message
    private static JComboBox comboBoxMessageModeSelection = new JComboBox(stringMessageModeSelection);                      //Combo Box for Selecting Mode for sending Message
    private static String[] stringPrivateMessagePlayerSelection = {                                                         //String Array Contains Selectable Players for sending Message
            "Player 0","Player 1","Player 2","Player 3","Player 4","Player 5","Player 6","Player 7"
    };
    private static JComboBox comboBoxPrivateMessagePlayerSelection = new JComboBox(stringPrivateMessagePlayerSelection);    //Combo Box for Selecting Players for sending Message
    private static boolean publicMessage = true;                                                                            //Identifier for Mode Selection
    private static int sendingPlayerIndex = 0;                                                                              //Identifier for Index of Player Private Message will be send

    /**Objects Contained in the rightPanel (All Player Victory Conditions)*/
    private static JTextArea textAreaAllVictoryConditions = new JTextArea();

    /**Final values for Object Length, Width, and Height*/
    private static final int ScreenWIDTH = 800;
    private static final int ScreenHEIGHT = 700;
    private static final int LeftScreenWIDTH = 500;
    private static final int RightScreenWIDTH = ScreenWIDTH-LeftScreenWIDTH;
    private static final int TopPanelHEIGHT = 460;

    private static final int LargeGAP = 5;
    private static final int SmallGAP = 3;
    private static final int StringWaitingWIDTH = 105;
    private static final int StringGameStartedWIDTH = 190;

    private static final int LabelGameStatusHEIGHT= 41;


    /**Getter and Setters for GameBoardGUI*/
    public static String getTextAreaServerOutput() {
        return textAreaServerOutput.getText();
    }

    public static void setTextAreaServerOutput(String textAreaServerOutput) {
        GameBoardGUI.textAreaServerOutput.setText(textAreaServerOutput);
    }

    public static String getTextAreaMessage() {
        return textAreaMessage.getText();
    }

    public static void setTextAreaMessage(String textAreaMessage) {
        GameBoardGUI.textAreaMessage.setText(textAreaMessage);
    }

    public static JTextField getTextFieldMessage() {
        return textFieldMessage;
    }

    public static JButton getButtonEndTurn() {
        return buttonEndTurn;
    }

    public static JButton getButtonFind() {
        return buttonFind;
    }

    public static JButton getButtonKill() {
        return buttonKill;
    }

    public static JComboBox getComboBoxAbilityTowardsPlayerIndex() {
        return comboBoxAbilityTowardsPlayerIndex;
    }


    /**Constructor for GameBoard GUI*/
    public GameBoardGUI() {

        /**Creates Objects in the Frame*/
        createView();

        /**Basic Settings for JFrame*/
        setTitle("Game Macbeth");                            //Title of Window
        setSize(ScreenWIDTH,ScreenHEIGHT);                   //Size of Window
        setResizable(false);                                 //Fix the Size of Window
        setLocationRelativeTo(null);                         //Make Window Open in the Center of the Screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //Make closing button close the window
        setBackground(Color.white);                          //Make Background Color White
        setType(Type.UTILITY);                               //Set Types of JFrame to Utility
        setLayout(null);                                     //Set Absolute Layout (pointed by x and y)

    }

    private void createView(){
        /**Sets Elements of Top Panel*/
        topPanel.setLocation(0,0);
        topPanel.setSize(LeftScreenWIDTH,TopPanelHEIGHT);
        topPanel.setBackground(Color.YELLOW);
        topPanel.setLayout(null);

        /**Sets Elements of Bottom Panel*/
        bottomPanel.setLocation(0,TopPanelHEIGHT);
        bottomPanel.setSize(LeftScreenWIDTH,ScreenHEIGHT-TopPanelHEIGHT);
        bottomPanel.setBackground(Color.RED);

        /**Sets Elements of Right Panel*/
        rightPanel.setLocation(LeftScreenWIDTH,0);
        rightPanel.setSize(RightScreenWIDTH,ScreenHEIGHT);
        rightPanel.setBackground(Color.white);


        /**Add Elements to Content Pane*/
        getContentPane().add(topPanel);
        getContentPane().add(bottomPanel);
        getContentPane().add(rightPanel);

        /**Sets Elements of My Player Index Label*/
        labelMyPlayerIndex.setText("Player: "+" ");
        labelMyPlayerIndex.setBounds(0,0,100,setHeight(labelMyPlayerIndex));

        /**Sets Elements of Game Label*/
        labelGameStatus.setText("Waiting");
        labelGameStatus.setFont(new Font("Dialog",Font.BOLD,27));
        labelGameStatus.setBounds((LeftScreenWIDTH-StringWaitingWIDTH)/2,0,StringWaitingWIDTH,LabelGameStatusHEIGHT);

        /**Sets Elements of Name Label*/
        labelName.setFont(new Font("Dialog",Font.BOLD,25));

        /**Sets Elements of Position Label*/
        labelPosition.setFont(new Font("Dialog",Font.BOLD,25));

        /**Sets Elements of Player Selection Combo Box for Ability use*/
        comboBoxAbilityTowardsPlayerIndex.setBounds(LeftScreenWIDTH-setWidth(buttonEndTurn)-setWidth(comboBoxAbilityTowardsPlayerIndex)-SmallGAP,300-setHeight(buttonEndTurn)-setHeight(comboBoxAbilityTowardsPlayerIndex)-10,setWidth(comboBoxAbilityTowardsPlayerIndex),setHeight(comboBoxAbilityTowardsPlayerIndex));
        comboBoxAbilityTowardsPlayerIndex.setSelectedIndex(0);
        /**Make Combo Box Response to Clicks*/
        comboBoxAbilityTowardsPlayerIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abilityTowardsPlayerIndex = comboBoxAbilityTowardsPlayerIndex.getSelectedIndex();

            }
        });


        /**Sets Elements of Find Button*/
        buttonFind.setBounds(LeftScreenWIDTH-setWidth(buttonEndTurn)-LargeGAP,300-(setHeight(buttonEndTurn)+LargeGAP)*2,setWidth(buttonEndTurn),setHeight(buttonEndTurn));
        buttonFind.setEnabled(false);
        /**Make Button Response to Click*/
        buttonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getDataOutputStream().println("(Server)"+"(ToFind)"+"(PositionOfPlayer)"+abilityTowardsPlayerIndex);
                buttonFind.setEnabled(false);
            }
        });

        /**Sets Elements of Kill Button*/
        buttonKill.setBounds(LeftScreenWIDTH-setWidth(buttonEndTurn)-LargeGAP,300-(setHeight(buttonEndTurn)+LargeGAP)*2,setWidth(buttonEndTurn),setHeight(buttonEndTurn));
        buttonKill.setEnabled(false);
        /**Make Button Response to Click*/
        buttonKill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getDataOutputStream().println("(Server)"+"(ToKill)"+abilityTowardsPlayerIndex);
                buttonKill.setEnabled(false);
            }
        });

        /**Sets Elements of End Turn Button*/
        buttonEndTurn.setBounds(LeftScreenWIDTH-setWidth(buttonEndTurn)-LargeGAP,300-setHeight(buttonEndTurn)-LargeGAP,setWidth(buttonEndTurn),setHeight(buttonEndTurn));
        buttonEndTurn.setEnabled(false);
        /**Make Button Response to Click*/
        buttonEndTurn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Client.getDataOutputStream().println(("(EndTurn)"));

                    }
                }
        );

        /**Sets Elements of Current Round Label*/
        labelCurrentRound.setText("Current Round: "+"0");
        labelCurrentRound.setFont(new Font("Dialog",Font.BOLD,16));
        labelCurrentRound.setBounds(LeftScreenWIDTH-setWidth(labelCurrentRound)-LargeGAP,26,setWidth(labelCurrentRound),setHeight(labelCurrentRound));


        /**Sets Elements of Current Player Label*/
        labelCurrentPlayer.setText("Current Player: "+"0");
        labelCurrentPlayer.setFont(new Font("Dialog",Font.BOLD,16));
        labelCurrentPlayer.setBounds(LeftScreenWIDTH-setWidth(labelCurrentPlayer)-LargeGAP*2,50,setWidth(labelCurrentPlayer),setHeight(labelCurrentPlayer));


        /**Sets Elements of Abilities Text Area*/
        textAreaAbilities.setFont(new Font("Dialog",Font.BOLD,13));
        textAreaAbilities.setBackground(new Color(255,255,0));
        textAreaAbilities.setEditable(false);

        /**Sets Elements of Victory Condition Text Area*/
        textAreaVictoryCondition.setFont(new Font("Dialog",Font.BOLD,13));
        textAreaVictoryCondition.setBackground(new Color(255,255,0));
        textAreaVictoryCondition.setEditable(false);


        /**Sets Size For Server Output Area*/
        scrollPaneTextAreaServerOutput.setBounds(50,320,400,130);
        /**Sets Elements of Server Output Text Area*/
        textAreaServerOutput.setEditable(false);
        /**Automatically Sets the Scroll Bar of textAreaMessage to Bottom for Each Update*/
        DefaultCaret defaultCaretServerOutput = (DefaultCaret)textAreaServerOutput.getCaret();
        defaultCaretServerOutput.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);


        /**Sets Elements of Victory Label*/
        labelVictory.setText("DEFAULT");
        labelVictory.setFont(new Font("Dialog",Font.BOLD,30));
        labelVictory.setForeground(new Color(255,0,0));
        labelVictory.setBounds(70,190,LeftScreenWIDTH-70,setHeight(labelVictory));


        /**Add all components to Top Panel*/
        topPanel.add(labelMyPlayerIndex);
        topPanel.add(labelGameStatus);
        topPanel.add(labelName);
        topPanel.add(labelPosition);
        topPanel.add(textAreaAbilities);
        topPanel.add(textAreaVictoryCondition);
        topPanel.add(comboBoxAbilityTowardsPlayerIndex);
        topPanel.add(buttonKill);
        topPanel.add(buttonFind);
        topPanel.add(buttonEndTurn);
        topPanel.add(labelCurrentRound);
        topPanel.add(labelCurrentPlayer);
        topPanel.add(scrollPaneTextAreaServerOutput);
        topPanel.add(labelVictory);

        /**Sets Size For Message Output Area*/
        scrollPaneTextAreaMessage.setPreferredSize(new Dimension(400,150));
        /**Make TextAreaMessage Not Editable by the User*/
        textAreaMessage.setEditable(false);

        /**Automatically Sets the Scroll Bar of textAreaMessage to Bottom for Each Update*/
        DefaultCaret defaultCaretMessage = (DefaultCaret)textAreaMessage.getCaret();
        defaultCaretMessage.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        /**Sets Elements of Mode Selection Combo Box for Message*/
        comboBoxMessageModeSelection.setSelectedIndex(0);
        /**Make Combo Box Response to Clicks*/
        comboBoxMessageModeSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxMessageModeSelection.getSelectedIndex()==0){
                    comboBoxPrivateMessagePlayerSelection.setEnabled(false);
                    publicMessage = true;
                } else{
                    comboBoxPrivateMessagePlayerSelection.setEnabled(true);
                    publicMessage = false;
                }
            }
        });

        /**Sets Elements of PLayer Selection Combo Box for Message*/
        comboBoxPrivateMessagePlayerSelection.setSelectedIndex(0);
        comboBoxPrivateMessagePlayerSelection.setEnabled(false);
        /**Make Combo Box Response to Clicks*/
        comboBoxPrivateMessagePlayerSelection.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendingPlayerIndex = comboBoxPrivateMessagePlayerSelection.getSelectedIndex();
                    }
                }
        );

        /**Sets Elements of Text Field for Message*/
        textFieldMessage.setPreferredSize(new Dimension(300,30));
        textFieldMessage.setEnabled(false);
        /**Make Text Field Response to Key Board*/
        textFieldMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                updateTextFieldMessage(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        /**Add all components to Bottom Panel*/
        bottomPanel.add(scrollPaneTextAreaMessage);
        bottomPanel.add(comboBoxMessageModeSelection);
        bottomPanel.add(comboBoxPrivateMessagePlayerSelection);
        bottomPanel.add(textFieldMessage);

        textAreaAllVictoryConditions.setText(allVictoryConditions);
        textAreaAllVictoryConditions.setFont(new Font("Dialog",Font.PLAIN,12));
        textAreaAllVictoryConditions.setSize(RightScreenWIDTH,ScreenHEIGHT);

        rightPanel.add(textAreaAllVictoryConditions);
    }

    /**Update Information in Name Label*/
    public static void updateName(String name){
        GameBoardGUI.labelName.setText("Name: "+ name);
        GameBoardGUI.labelName.setBounds(5,40,300,30);
    }
    /**Update Information in Position Label*/
    public static void updatePosition(String position){
        GameBoardGUI.labelPosition.setText("Position: "+ position);
        GameBoardGUI.labelPosition.setBounds(5,75,450,30);
    }
    /**Return String with new line*/
    public static String newLine(){
        return "\n";
    }
    /**Resets Information in Text Field*/
    private void textFieldMessageReset(){
        textFieldMessage.setText("");
    }
    /**Update Information in Ability Text Area*/
    public static void updateTextAreaAbility(String ability){
        if (GameBoardGUI.textAreaAbilities.getText().isEmpty()) {
            GameBoardGUI.textAreaAbilities.append(ability);
        } else {
            GameBoardGUI.textAreaAbilities.append(newLine()+"    "+ability);
        }
        GameBoardGUI.textAreaAbilities.setBounds(20,105,350,110);
    }
    /**Update Image to be shown*/
    public static void updateIconLabel(int characterIndex){
        String url= "";
        switch (characterIndex){
            case 0:
                url+="King";
                break;
            case 1:
                url+="Father_of_Kings";
                break;
            case 2:
                url+="Prince";
                break;
            case 3:
                url+="Loyal";
                break;
            case 4:
                url+="Prince";
                break;
            case 5:
                url+="Wife_of_Traitor";
                break;
            case 6:
                url+="Future_King";
                break;
            case 7:
                url+="Traitor";
                break;
        }
        url+=".png";
        imageIcon=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(url)).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        labelIcon = new JLabel(imageIcon);
        labelIcon.setBounds(390,105,100,100);
        topPanel.add(labelIcon);
    }
    /**Update Information in Victory Label*/
    public static void updateTextAreaVictoryCondition(String victoryCondition){
        if (GameBoardGUI.textAreaVictoryCondition.getText().isEmpty()) {
            GameBoardGUI.textAreaVictoryCondition.append(victoryCondition);
        } else {
            GameBoardGUI.textAreaVictoryCondition.append(newLine()+"    "+victoryCondition);
        }
        GameBoardGUI.textAreaVictoryCondition.setBounds(20,215,280,105);
    }
    /**Update Information in My Player Index Label*/
    public static void updateLabelMyPlayerIndex(String myPlayerIndex){
        GameBoardGUI.labelMyPlayerIndex.setText("Player: "+myPlayerIndex);
        System.out.println("Player: "+myPlayerIndex);
    }
    /**Update Information in Game Status Label*/
    public static void updateLabelGameStatus(String gameStatus){
        GameBoardGUI.labelGameStatus.setText(gameStatus);
        GameBoardGUI.labelGameStatus.setBounds((LeftScreenWIDTH-StringGameStartedWIDTH)/2,0,StringGameStartedWIDTH,LabelGameStatusHEIGHT);
    }
    /**Update Information in Current Player Label*/
    public static void updateLabelCurrentPlayer(String currentPlayer){
        GameBoardGUI.labelCurrentPlayer.setText("Current Player: "+currentPlayer);
    }
    /**Update Information in Current Round Label*/
    public static void updateLabelCurrentRound(String round){
        GameBoardGUI.labelCurrentRound.setText("Current Round: "+round);
    }
    /**Update Information in Victory Label*/
    public static void updateLabelVictory(String victory){

        GameBoardGUI.labelVictory.setText(victory);
        System.out.println(labelVictory.getText());
        GameBoardGUI.textAreaAbilities.setVisible(false);
        GameBoardGUI.textAreaVictoryCondition.setVisible(false);
        GameBoardGUI.comboBoxAbilityTowardsPlayerIndex.setVisible(false);
        GameBoardGUI.buttonKill.setVisible(false);
        GameBoardGUI.buttonFind.setVisible(false);
        GameBoardGUI.buttonEndTurn.setEnabled(false);
        GameBoardGUI.textFieldMessage.setEnabled(false);

    }
    /**Get Input from Text Field and Update Text Area*/
    private void updateTextFieldMessage(KeyEvent event){
        /**Sets the Maximum length of each Message*/
        int maxMessageLength = 36;

        /**Manipulates Message typed in the Text Field, Updates text in Text Area, and Sends message to the server in the designed format*/
        if(event.getKeyCode()==10){                                                                                                                                                            //if enter key is pressed
            if(!textFieldMessage.getText().isEmpty()) {                                                                                                                                        //if Text Field is not empty
                if(textAreaMessage.getText().isEmpty()){                                                                                                                                       //if Text Area for Message Output is empty
                    if(publicMessage) {
                        textAreaMessage.setText(
                                "Player "+Client.getMyPlayerIndex()+ " to all: " +
                                        textFieldMessage.getText()
                        );
                        Client.getDataOutputStream().println("(PublicMessage)"+textFieldMessage.getText());
                    } else{
                        textAreaMessage.setText(
                                "Player "+Client.getMyPlayerIndex()+ " to " + sendingPlayerIndex+": "+
                                        textFieldMessage.getText()
                        );
                        if(sendingPlayerIndex!=Client.getMyPlayerIndex()&&!textFieldMessage.getText().isEmpty()) {
                            Client.getDataOutputStream().println("(PrivateMessage)" + sendingPlayerIndex + textFieldMessage.getText());
                        }
                    }
                }else {                                                                                                                                                                         //if Text Area for Message Output is not empty
                    if(publicMessage) {
                        textAreaMessage.setText(
                                textAreaMessage.getText() + newLine() + "Player "+Client.getMyPlayerIndex()+ " to all: " + textFieldMessage.getText()
                        );
                        Client.getDataOutputStream().println("(PublicMessage)"+textFieldMessage.getText());
                    } else{
                        textAreaMessage.setText(
                                textAreaMessage.getText() + newLine() + "Player "+Client.getMyPlayerIndex()+ " to " + sendingPlayerIndex+": "+ textFieldMessage.getText()
                        );
                        if(sendingPlayerIndex!=Client.getMyPlayerIndex()&&!textFieldMessage.getText().isEmpty()) {
                            Client.getDataOutputStream().println("(PrivateMessage)" + sendingPlayerIndex + textFieldMessage.getText());
                        }
                    }

                }
//                if(sendingPlayerIndex!=Client.getMyPlayerIndex()&&!textFieldMessage.getText().isEmpty()){
//
//                }
            }
            textFieldMessageReset();                                                                                                                                                            //Resets Texts in Text Field
        }
        /**Limits the length of each Message*/
        if(textFieldMessage.getText().length()>=maxMessageLength){                                  //if the text length is greater or equal to the max value
            textFieldMessage.setText(textFieldMessage.getText().substring(0,maxMessageLength-1));   //cut the message to the maximum length in the setting
        }
    }

    /**Overloaded Methods that Calculates Packed Size of the Object*/
    private static int setWidth(JLabel label){          //Calculates Packed width of the label
        return label.getPreferredSize().width;
    }
    private static int setWidth(JButton button){        //Calculates Packed width of the button
        return button.getPreferredSize().width;
    }
    private static int setWidth(JComboBox comboBox){    //Calculates Packed width of the comboBox
        return comboBox.getPreferredSize().width;
    }
    private static int setHeight(JLabel label){         //Calculates Packed height of the label
        return label.getPreferredSize().height;
    }
    private static int setHeight(JButton button){       //Calculates Packed height of the button
        return button.getPreferredSize().height;
    }
    private static int setHeight(JComboBox comboBox){   //Calculates Packed height of the comboBox
        return comboBox.getPreferredSize().height;
    }
}