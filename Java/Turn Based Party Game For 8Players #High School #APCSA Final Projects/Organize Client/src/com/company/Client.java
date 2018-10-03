package com.company;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    /**Variables for Client*/
    private static Socket socket;
    private static PrintStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static boolean gameStarted = false;
    private static int myCharacterIndex =-1;
    private static int myPlayerIndex =-1;
    private static int currentPlayerIndex =0;
    private static int round =0;
    private static boolean firstRequest = true;
    private static boolean alive =true;


    /**getter and setter for client*/
    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static PrintStream getDataOutputStream() {
        return dataOutputStream;
    }

    public static void setGameStarted(boolean gameStarted) {
        Client.gameStarted = gameStarted;
    }

    public static int getMyCharacterIndex() {
        return myCharacterIndex;
    }

    public static void setMyCharacterIndex(int myCharacterIndex) {
        Client.myCharacterIndex = myCharacterIndex;
    }

    public static int getMyPlayerIndex() {
        return myPlayerIndex;
    }

    public static void setMyPlayerIndex(int myPlayerIndex) {
        Client.myPlayerIndex = myPlayerIndex;
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public static void setCurrentPlayerIndex(int currentPlayerIndex) {
        Client.currentPlayerIndex = currentPlayerIndex;
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int round) {
        Client.round = round;
    }

    public static boolean isAlive() {
        return alive;
    }

    public static void setAlive(boolean alive) {
        Client.alive = alive;
    }


    /**Tries to Connect With Server*/
    public static void connectToServer(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);                                      //Create connection to server
            SwingUtilities.invokeLater(() -> new GameBoardGUI().setVisible(true));  //Starting visible updating GUI
            dataOutputStream = new PrintStream(socket.getOutputStream());       //Create stream for data output
            dataInputStream = new DataInputStream(socket.getInputStream());     //Create stream for data input
            dataOutputStream.println("(Entered)");                              //Send Message to the server that the Client Entered
            new Thread(new ClientDataTransferManagement()).start();             //create thread to communicate with server

            initialClientDataOutPut();                                          //Send Initial Messages to the Server

        } catch (UnknownHostException e) {
            System.err.println("Don't know about ip " + ip);
            SwingUtilities.invokeLater(() -> new WarningGUI("Don't know about ip ").setVisible(true));  //Starting GUI for warning
        } catch (IOException e) {
            System.err.println("Couldn't get the connection to the ip " + ip+"; port: "+port);
            SwingUtilities.invokeLater(() -> new WarningGUI("Couldn't get the connection to the ip " + ip+"; port: "+port).setVisible(true));  //Starting GUI for warning
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**Send Initial Messages to the Server*/
    public static void initialClientDataOutPut() throws InterruptedException {



        if (socket != null && dataOutputStream != null && dataInputStream != null) {

                while (true) {
                    if(!gameStarted){
                        Thread.sleep(100);
                    } else {
                        if(getRound()==0&&getCurrentPlayerIndex()==0&&firstRequest){
                            switch (getMyCharacterIndex()){
                                case 1:
                                    dataOutputStream.println("(Server)"+"(ToFind)"+"(PlayerIndexOfCharacter)"+6);
                                    break;
                                case 3:
                                    dataOutputStream.println("(Server)"+"(ToFind)"+"(PlayerIndexOfCharacter)"+2);
                                    break;
                                case 6:
                                    dataOutputStream.println("(Server)"+"(ToFind)"+"(PlayerIndexOfCharacter)"+1);
                                    break;
                                default:
                                    break;
                            }
                            firstRequest=false;
                        }
                    }

                }
        }

    }

}

/**Manage data input from Server*/
class ClientDataTransferManagement extends Thread{
    /**Manipulates GUI with data received*/
    public static void clientDataInput(){
        try {
            String inputFromServer;
            while ((inputFromServer = Client.getDataInputStream().readLine()) != null) {    // constantly wait for data input from server
                /**Check Server announced Game Start*/
                if (inputFromServer.startsWith("(GameStart)")) {
                    Client.setGameStarted(true);
                    inputFromServer = inputFromServer.substring(new String("(GameStart)").length());
                    GameBoardGUI.updateLabelGameStatus(inputFromServer);
                    GameBoardGUI.getTextFieldMessage().setEnabled(true);
                }
                /**Check if other player has sent public message*/
                else if (inputFromServer.startsWith("(PublicMessage)")){
                    inputFromServer = inputFromServer.substring(new String("(PublicMessage)").length());
                    if(GameBoardGUI.getTextAreaMessage().isEmpty()){
                        GameBoardGUI.setTextAreaMessage(inputFromServer);
                    } else{
                        GameBoardGUI.setTextAreaMessage(GameBoardGUI.getTextAreaMessage()+GameBoardGUI.newLine()+inputFromServer);
                    }

                }
                /**Check if other player has sent private message*/
                else if (inputFromServer.startsWith("(PrivateMessage)")){
                    inputFromServer = inputFromServer.substring(new String("(PrivateMessage)").length());
                    if(GameBoardGUI.getTextAreaMessage().isEmpty()){
                        GameBoardGUI.setTextAreaMessage(inputFromServer);
                    } else{
                        GameBoardGUI.setTextAreaMessage(GameBoardGUI.getTextAreaMessage()+GameBoardGUI.newLine()+inputFromServer);
                    }
                }
                /**Check if Server has sent information about the name*/
                else if (inputFromServer.startsWith("(Name)")){
                    inputFromServer = inputFromServer.substring(new String("(Name)").length());
                    GameBoardGUI.updateName(inputFromServer);
                }
                /**Check if Server has sent information about the position*/
                else if (inputFromServer.startsWith("(Position)")){
                    inputFromServer = inputFromServer.substring(new String("(Position)").length());
                    GameBoardGUI.updatePosition(inputFromServer);
                }
                /**Check if Server has sent information about the player index*/
                else if (inputFromServer.startsWith("(PlayerIndex)")){
                    Client.setMyPlayerIndex(Integer.valueOf(inputFromServer.substring(new String("(PlayerIndex)").length())));
                    inputFromServer = inputFromServer.substring(new String("(PlayerIndex)").length());
                    GameBoardGUI.setTextAreaServerOutput("");
                    GameBoardGUI.updateLabelMyPlayerIndex(inputFromServer);

                }
                /**Check if Server has sent information about player's victory condition*/
                else if (inputFromServer.startsWith("(VictoryCondition)")){
                    inputFromServer = inputFromServer.substring(new String("(VictoryCondition)").length());

                    GameBoardGUI.updateTextAreaVictoryCondition(inputFromServer);

                }
                /**Check if Server has sent information about player's ability*/
                else if (inputFromServer.startsWith("(Ability)")){
                    inputFromServer = inputFromServer.substring(new String("(Ability)").length());

                    GameBoardGUI.updateTextAreaAbility(inputFromServer);

                }
                /**Check if Server has sent information about current round*/
                else if (inputFromServer.startsWith("(Round)")) {
                    Client.setRound(Integer.valueOf(inputFromServer.substring(new String("(Round)").length())));
                    inputFromServer = inputFromServer.substring(new String("(Round)").length());
                    GameBoardGUI.updateLabelCurrentRound(inputFromServer);


                }
                /**Check if Server has sent information about current player*/
                else if(inputFromServer.startsWith("(CurrentPlayer)")){
                    Client.setCurrentPlayerIndex(Integer.valueOf(inputFromServer.substring(new String("(CurrentPlayer)").length())));
                    inputFromServer = inputFromServer.substring(new String("(CurrentPlayer)").length());
                    GameBoardGUI.getButtonEndTurn().setEnabled(false);
                    GameBoardGUI.getButtonFind().setEnabled(false);
                    GameBoardGUI.getButtonKill().setEnabled(false);
                    GameBoardGUI.updateLabelCurrentPlayer(inputFromServer);
                    if(Client.getCurrentPlayerIndex()==Client.getMyPlayerIndex()){
                        GameBoardGUI.getButtonEndTurn().setEnabled(true);
                        GameBoardGUI.getButtonFind().setEnabled(true);
                        GameBoardGUI.getButtonKill().setEnabled(true);
                        /**If player is dead automatically end his turn*/
                        if(!Client.isAlive()){
                            GameBoardGUI.getTextFieldMessage().setEnabled(false);
                            GameBoardGUI.getButtonEndTurn().setEnabled(false);
                            GameBoardGUI.getButtonFind().setEnabled(false);
                            GameBoardGUI.getButtonKill().setEnabled(false);
                            Client.getDataOutputStream().println("(EndTurn)");
                        }

                    }
                }
                /**Check if Server has sent information about Player's Character Index*/
                else if(inputFromServer.startsWith("(CharacterIndex)")){
                    int myCharacterIndex = Integer.valueOf(inputFromServer.substring(new String("(CharacterIndex)").length()));
                    Client.setMyCharacterIndex(myCharacterIndex);
                    GameBoardGUI.updateIconLabel(myCharacterIndex);
                    if(myCharacterIndex!=2&&myCharacterIndex!=4){
                        GameBoardGUI.getButtonFind().setVisible(false);
                    }
                    if(myCharacterIndex!=3&&myCharacterIndex!=5&&myCharacterIndex!=7){
                        GameBoardGUI.getButtonKill().setVisible(false);
                    }
                    if((myCharacterIndex!=2&&myCharacterIndex!=4)&&(myCharacterIndex!=3&&myCharacterIndex!=5&&myCharacterIndex!=7)){
                        GameBoardGUI.getComboBoxAbilityTowardsPlayerIndex().setVisible(false);
                    }
                }
                /**Check if Server has announced the player is dead*/
                else if(inputFromServer.startsWith("(Dead)")){
                    Client.setAlive(false);
                }
                /**Check if Server has sent information to be displayed in GUI*/
                else if(inputFromServer.startsWith("(ServerOutput)")){

                    inputFromServer = inputFromServer.substring(new String("(ServerOutPut)").length());
                    if(GameBoardGUI.getTextAreaServerOutput().isEmpty()){
                        GameBoardGUI.setTextAreaServerOutput(inputFromServer);

                    }else {
                        GameBoardGUI.setTextAreaServerOutput(GameBoardGUI.getTextAreaServerOutput() + GameBoardGUI.newLine() + inputFromServer);
                    }
                }
                /**Check if Server has announced victory of any player*/
                else if(inputFromServer.startsWith("(Victory)")){
                    inputFromServer = inputFromServer.substring(new String("(Victory)").length());
                    GameBoardGUI.updateLabelVictory(inputFromServer);

                }
            }

        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
    /**Continuously check for data input from Server*/
    @Override
    public void run() {
        clientDataInput();
    }
}
