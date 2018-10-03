package com.company;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**Manage Core Functions of Server*/
public class Server {
    /**server variables*/
    private static ServerSocket serverSocket;                                                                                           //Socket for the Server
    private static Socket clientSocket;                                                                                                 //Socket for each Client
    private static final int maxClientsCount = 8;                                                                                       //Maximum Number of Players in a single Server
    private static final ServerDataTransferManagement[] dataTransferManagements = new ServerDataTransferManagement[maxClientsCount];    //Array of Thread for Client
    private static int clientNumber=0;                                                                                                  //Number of Clients accepted by the Server

    /**starts new server in received ip and port*/
    public static void initializeServer(String ip, int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));     //Opens new Server
            SwingUtilities.invokeLater(() -> new ConnectedGUI().setVisible(true));  //Starting GUI for Connection
        } catch (UnknownHostException e){
            System.err.println(e);
            SwingUtilities.invokeLater(() -> new WarningGUI("Could not Open Server on ip:"+Main.ipAddress).setVisible(true));  //Starting GUI for warning
        }
        acceptsClient();                                                                //Accepts Clients for Server
    }

    /**Continuously accepts client request*/
    public static void acceptsClient() throws IOException {
        while (true) {                                                                                                              //continuously search for request
            clientSocket = serverSocket.accept();                                                                                   //stores information about the client request
            if(clientNumber<maxClientsCount){                                                                                       //if number of client accepted by the server is less than the maximum number to accept
                dataTransferManagements[clientNumber] = new ServerDataTransferManagement(clientSocket,dataTransferManagements);     //Create new Thread for the Client
                dataTransferManagements[clientNumber].start();                                                                      //Start the thread for the Client
                clientNumber++;                                                                                                     //increase the number of Clients accepted by the Server
            } else {                                                                                                                //if number of client accepted by the server is not less than the maximum number to accept
                PrintStream dataOutputStream = new PrintStream(clientSocket.getOutputStream());                                     //Prepare to send data to the Client
                dataOutputStream.println("Server is full. Please Connect to other server.");                                        //Tell Client that Server is full
                dataOutputStream.close();                                                                                           //Close the data transmission
                clientSocket.close();                                                                                               //Close the connection with Client
            }
        }
    }
}

/**Manage Thread of Each Client Connection*/
class ServerDataTransferManagement extends Thread {     // how server manage data



    /**Objects for Each Client*/
    private ServerDataTransferManagement[] dataTransferManagements;
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private PrintStream dataOutputStream;

    /**Variables for Each Client*/
    private static int connectedClientNumber =0;
    private int maxClientsCount;
    private static int receivingFromPlayerIndex = -1;


    /**Initialize variables for each Thread*/
    public ServerDataTransferManagement(Socket clientSocket, ServerDataTransferManagement[] dataTransferManagements) {
        this.clientSocket = clientSocket;
        this.dataTransferManagements = dataTransferManagements;
        maxClientsCount = dataTransferManagements.length;
    }


    @Override
    public void run() {
        /**Find Index of this thread in Thread Array*/
        for (int i = 0; i < maxClientsCount; i++) {                                                                     //For each index
            if(dataTransferManagements[i] == this){                                                                     //Check if the Thread in index is the current Thread
                receivingFromPlayerIndex =i;                                                                            //Update the current Thread Index
            }
        }
        ServerDataTransferManagement[] dataTransferManagements = this.dataTransferManagements;                          //Update Thread Array for each run


        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());                                       //Create stream for input from client
            dataOutputStream = new PrintStream(clientSocket.getOutputStream());                                         //Create stream for output from client
            /**Check for Client Connection made*/
            while (true) {                                                                                              //Continuously Check
                if (dataInputStream.readLine().trim().equals("(Entered)")) {                                            //Check if connected Client is actually responding
                    connectedClientNumber++;                                                                            //Increase the number of connected client

                    /**Initial Outputs containing player information for each Client*/
                    dataOutputStream.println("(Name)" + PlayableCharacters.getCharacter(Game.characterIndexForPlayers.get(receivingFromPlayerIndex)).getName());
                    dataOutputStream.println("(Position)" + PlayableCharacters.getCharacter(Game.characterIndexForPlayers.get(receivingFromPlayerIndex)).getPosition());
                    dataOutputStream.println("(Ability)" + PlayableCharacters.getCharacter(Game.characterIndexForPlayers.get(receivingFromPlayerIndex)).getAbility());
                    dataOutputStream.println("(VictoryCondition)" + PlayableCharacters.getCharacter(Game.characterIndexForPlayers.get(receivingFromPlayerIndex)).getVictoryCondition());
                    dataOutputStream.println("(ServerOutput)" + "you are Player " + receivingFromPlayerIndex);
                    dataOutputStream.println("(PlayerIndex)" + receivingFromPlayerIndex);
                    dataOutputStream.println("(CharacterIndex)"+Game.characterIndexForPlayers.get(receivingFromPlayerIndex));
                    dataOutputStream.println("(Round)"+Game.round);

                    /**Notification for all clients as each Client enters*/
                    for (int i = 0; i < maxClientsCount; i++) {
                        /**A Client is accepted to the server*/
                        if (dataTransferManagements[i] != null && i != receivingFromPlayerIndex) {
                            dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"*** Player " + receivingFromPlayerIndex
                                    + " entered the Game ***");
                        }
                        /**All player has entered the server*/
                        if (connectedClientNumber == maxClientsCount) {
                            dataTransferManagements[i].dataOutputStream.println("(GameStart)" + "Game Started");
                            dataTransferManagements[i].dataOutputStream.println("(CurrentPlayer)" + Game.currentPlayerIndex);
                        }
                    }
                    break;
                }
            }
            /**Check for data Client sent*/
            while (true) {
                String line = dataInputStream.readLine();               //received data from Client

                /**Identify the Client Index in the Array*/
                for (int i = 0; i < maxClientsCount; i++) {
                    if(dataTransferManagements[i] == this){
                        receivingFromPlayerIndex =i;

                    }
                }
                /**Check if Client is Requesting Public Message to all players*/
                if(line.startsWith("(PublicMessage)")) {
                    String message = line.substring(new String("(PublicMessage)").length());
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (dataTransferManagements[i] != null && dataTransferManagements[i]!= this) {
                            dataTransferManagements[i].dataOutputStream.println("(PublicMessage)"+"Player "+ receivingFromPlayerIndex +" to all: "+message);
                        }
                    }
                }
                /**Check if Client is Requesting Private Message to an player*/
                else if (line.startsWith("(PrivateMessage)")){
                    int playerIndexToSend=Integer.valueOf(line.substring(new String("(PrivateMessage)").length(),new String("(PrivateMessage)").length()+1));
                    String message = line.substring(new String("(PrivateMessage)").length()+1);
                    dataTransferManagements[playerIndexToSend].dataOutputStream.println("(PrivateMessage)"+"Player "+ receivingFromPlayerIndex +" to "+playerIndexToSend+": "+message);

                }
                /**Check if Client is Requesting Server to return some type of data*/
                else if(line.startsWith("(Server)")){
                    line=line.substring(new String("(Server)").length());
                    /**Check if Client is Requesting Server to Kill certain Player and is the killing allowed for the Client*/
                    if(line.startsWith("(ToKill)")){
                        int playerIndexToKill = Integer.valueOf(line.substring(new String("(ToKill)").length(),new String("(ToKill)").length()+1));
                        System.out.println(receivingFromPlayerIndex +" is requesting To Kill towards Player"+playerIndexToKill);
                        /**Check who sent the request and announce the result if the request is accepted*/
                        switch (Game.characterIndexForPlayers.get(receivingFromPlayerIndex)){
                            case 3:
                                if(Game.characterIndexForPlayers.get(playerIndexToKill)==4){
                                    PlayableCharacters.getCharacter(4).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(4).getName()+" is alive:"+PlayableCharacters.getCharacter(4).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                } else if(Game.characterIndexForPlayers.get(playerIndexToKill)==7){
                                    PlayableCharacters.getCharacter(7).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(7).getName()+" is alive:"+PlayableCharacters.getCharacter(7).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                        if(PlayableCharacters.getCharacter(7).getPosition().equals(Position.positions[8])){
                                            dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"The King is dead");
                                        }
                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                    if(PlayableCharacters.getCharacter(7).getPosition().equals(Position.positions[8])) {
                                        dataTransferManagements[(int) (Math.random() * 8)].dataOutputStream.println("(ServerOutput)" + receivingFromPlayerIndex + " killed the King");
                                    }
                                }
                                break;
                            case 5:
                                if(Game.characterIndexForPlayers.get(playerIndexToKill)==2){
                                    PlayableCharacters.getCharacter(2).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(2).getName()+" is alive:"+PlayableCharacters.getCharacter(2).alive);
                                } else if (Game.characterIndexForPlayers.get(playerIndexToKill)==3){
                                    PlayableCharacters.getCharacter(3).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(3).getName()+" is alive:"+PlayableCharacters.getCharacter(3).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                }

                                break;
                            case 7:
                                if(Game.characterIndexForPlayers.get(playerIndexToKill)==0){
                                    PlayableCharacters.getCharacter(0).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(0).getName()+" is alive:"+PlayableCharacters.getCharacter(0).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"The King is dead");

                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                    dataTransferManagements[(int) (Math.random()*8)].dataOutputStream.println("(ServerOutput)"+ receivingFromPlayerIndex +" killed the King");
                                    PlayableCharacters.getCharacter(7).setPosition(8);
                                } else if (Game.characterIndexForPlayers.get(playerIndexToKill)==1){
                                    PlayableCharacters.getCharacter(1).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(1).getName()+" is alive:"+PlayableCharacters.getCharacter(1).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                } else if (Game.characterIndexForPlayers.get(playerIndexToKill)==6){
                                    PlayableCharacters.getCharacter(6).setAlive(false);
                                    System.out.println(PlayableCharacters.getCharacter(6).getName()+" is alive:"+PlayableCharacters.getCharacter(6).alive);
                                    for(int i=0;i<maxClientsCount; i++) {
                                        dataTransferManagements[i].dataOutputStream.println("(ServerOutput)"+"Player"+playerIndexToKill+" is Dead");
                                    }
                                    dataTransferManagements[playerIndexToKill].dataOutputStream.println("(Dead)");
                                }
                                break;
                            default:
                                break;
                        }
                        /**Check if any player has gained victory*/
                        if(PlayableCharacters.checkForVictoriousPlayer()!=-1){
                            for (int i = 0; i < maxClientsCount; i++) {
                                /**Announce to all Clients that certain player has gained victory*/
                                if (dataTransferManagements[i] != null) {
                                    dataTransferManagements[i].dataOutputStream.println("(Victory)"+"Player "+PlayableCharacters.checkForVictoriousPlayer()+" won the Game");
                                }
                            }
                        }
                    }
                    /**Check if Client is Requesting Server to return some information about other Players*/
                    else if(line.startsWith("(ToFind)")){
                        line = line.substring(new String("(ToFind)").length());
                        /**Check is Client is asking Which player has certain Character*/
                        if(line.startsWith("(PlayerIndexOfCharacter)")){
                            System.out.println("Player "+ receivingFromPlayerIndex +"is requesting PlayerIndexOfCharacter");
                            int characterIndex = Integer.valueOf(line.substring(new String("(PlayerIndexOfCharacter)").length(),new String("(PlayerIndexOfCharacter)").length()+1));
                            int playerIndexOfCharacter =-1;
                            /**Returns the Information to the Client*/
                            for(int i =0; i<8; i++){
                                if(Game.characterIndexForPlayers.get(i)==characterIndex){
                                    playerIndexOfCharacter = i;
                                }
                            }
                            dataTransferManagements[receivingFromPlayerIndex].dataOutputStream.println("(ServerOutput)"+"Player "+playerIndexOfCharacter+" is "+PlayableCharacters.getCharacter(characterIndex).getName());
                        }
                        /**Check is Client is asking Position of certain Player*/
                        else if(line.startsWith("(PositionOfPlayer)")){
                            int playerIndex = Integer.valueOf(line.substring(new String("(PositionOfPlayer)").length()));
                            /**Returns the Information to the Client*/
                            String positionOfPlayer = PlayableCharacters.getCharacter(Game.characterIndexForPlayers.get(playerIndex)).getPosition();
                            dataTransferManagements[receivingFromPlayerIndex].dataOutputStream.println("(ServerOutput)"+positionOfPlayer+" is the position of Player "+playerIndex);
                        }

                    }
                }
                /**Check if Client is Requesting Ending its turn*/
                else if(line.startsWith("(EndTurn)")){
                    /**Check if all player has finished their turn during the round*/
                    if(Game.currentPlayerIndex!=7){
                        /**Update Current Player Information for all Client*/
                        Game.currentPlayerIndex++;

                        for (int i = 0; i < maxClientsCount; i++) {
                            if (dataTransferManagements[i] != null) {
                                dataTransferManagements[i].dataOutputStream.println("(CurrentPlayer)"+Game.currentPlayerIndex);


                            }
                        }

                    } else {
                        /**Update Current Player and Current Round Information for all Client*/
                        Game.currentPlayerIndex=0;
                        Game.round++;

                        for (int i = 0; i < maxClientsCount; i++) {
                            if (dataTransferManagements[i] != null) {
                                dataTransferManagements[i].dataOutputStream.println("(CurrentPlayer)"+Game.currentPlayerIndex);

                                dataTransferManagements[i].dataOutputStream.println("(Round)"+Game.round);

                            }
                        }

                    }
                    /**Check if any player has gained victory*/
                    if(PlayableCharacters.checkForVictoriousPlayer()!=-1){
                        /**Announce to all Clients that certain player has gained victory*/
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (dataTransferManagements[i] != null ) {
                                dataTransferManagements[i].dataOutputStream.println("(Victory)"+"Player "+PlayableCharacters.checkForVictoriousPlayer()+" won the Game");
                            }
                        }
                    }
                }


            }

        } catch (IOException e) {}              //Catch IO Exception
    }

}