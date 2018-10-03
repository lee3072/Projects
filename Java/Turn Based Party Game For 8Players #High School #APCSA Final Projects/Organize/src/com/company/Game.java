package com.company;

import java.util.ArrayList;

/**Create and Record basic variables for a game*/
public class Game {
    static int currentPlayerIndex;                                                  //Identifier for current player
    static int round;                                                               //Identifier for current round
    static ArrayList<Integer> characterIndexForPlayers;                             //Stores character Index for each player


    public Game(){
        currentPlayerIndex = 0;                                                     //resets the current player
        round =0;                                                                   //resets the current round
        characterIndexForPlayers = new ArrayList<>();                               //creates the storage for character indexes of each player
        for(int i=0; i<=7; i++){                                                    //for 8 players
            characterIndexForPlayers.add(i);                                        //add needed character index for the list
            characterIndexForPlayers.add(                                           //randomize the order of list
                    characterIndexForPlayers.remove(
                            (int) (Math.random()*characterIndexForPlayers.size())
                    )
            );
        }
    }
}
