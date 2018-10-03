package com.company;


/**Stores Available Characters and Status of the Characters*/
public class PlayableCharacters{
    /**List of Characters Available*/
    final static Character[] characters = {
            new Duncan(),
            new Banquo(),
            new Malcom(),
            new Macduff(),
            new Donalbain(),
            new LadyMacbeth(),
            new Fleance(),
            new Macbeth()
    };
    /**Returns Character in the index*/
    public static Character getCharacter(int characterIndex){
        return characters[characterIndex];
    }

    /**Returns index of Victorious Player if there is any*/
    public static int checkForVictoriousPlayer(){
        for(int victoriousPlayerIndex =0; victoriousPlayerIndex<8; victoriousPlayerIndex++){                   //for all players
            if(characters[Game.characterIndexForPlayers.get(victoriousPlayerIndex)].isVictorious()){           //if the character of the player is victorious
                return victoriousPlayerIndex;                                                                  //returns the Victorious Player's Index
            }
        }
        return -1;                                                                                             //returns Non-Available Player Index
    }
}

/**Stores Positions for Characters*/
interface Position{
    String[] positions = {          //Array of Positions that the character could have
            "King",
            "Father of Kings",
            "Prince",
            "Loyal",
            "Prince",
            "Wife of Traitor",
            "Promised King",
            "Traitor",
            "Traitor & King"
    };

}
/**Stores Names for Characters*/
interface Name{
    String[] names = {              //Array of Names that the character could have
            "Duncan",
            "Banquo",
            "Malcom",
            "Macduff",
            "Donalbain",
            "Lady Macbeth",
            "Fleance",
            "Macbeth"
    };

}
/**Stores VictoryConditions for Characters*/
interface VictoryCondition{
    String[] victoryConditions = {  //Array of Victory Conditions that the character could have
            "Victory Condition:\n(VictoryCondition)Survival of Donalbain\n(VictoryCondition)Survival of Malcome\n(VictoryCondition)Survival of Self for 6 turn",
            "Victory Condition:\n(VictoryCondition)Survival of self\n(VictoryCondition)Survival of Fleance\n(VictoryCondition)Death of Macbeth",
            "Victory Condition:\n(VictoryCondition)Survival of self\n(VictoryCondition)Death of Duncan\n(VictoryCondition)Death of Macbeth",
            "Victory Condition:\n(VictoryCondition)Survival of Duncan\n(VictoryCondition)Survival of Malcom\n(VictoryCondition)Death of Macbeth\n(VictoryCondition)Death of Donalbain",
            "Victory Condition:\n(VictoryCondition)Survival of self\n(VictoryCondition)Survival of Duncan\n(VictoryCondition)Death of Malcom",
            "Victory Condition:\n(VictoryCondition)Death of Duncan\n(VictoryCondition)Survival of Macbeth\n(VictoryCondition)Death of Malcom or Macduff\n(VictoryCondition)Death of Banquo",
            "Victory Condition:\n(VictoryCondition)Survival of self\n(VictoryCondition)Death of Macbeth\n(VictoryCondition)Death of Malcom\n(VictoryCondition)Death of Donalbain\n(VictoryCondition)Death of Banquo",
            "Victory Condition:\n(VictoryCondition)Survival of self\n(VictoryCondition)Death of Duncan\n(VictoryCondition)Death of Malcom or Macduff\n(VictoryCondition)Death of Banquo"
    };

}
/**Stores Abilities for Characters*/
interface Ability{
    String[] abilities = {           //Array of Abilities that the character could have
            "Abilities:\n(Ability)All people know the survival of King\n(Ability)     Killer of King is identified for a random player",
            "Abilities:\n(Ability)Player of Fleance is known",
            "Abilities:\n(Ability)Could know Position of one selected Player",
            "Abilities:\n(Ability)Player of Malcom is known\n(Ability)Could kill Donalbain\n(Ability)Could kill Macbeth",
            "Abilities:\n(Ability)Could know Position of one selected Player",
            "Abilities:\n(Ability)Could identify the King for all\n" + "     Position is identified for all\n(Ability)Could Kill Malcom\n(Ability)Could Kill Macduff",
            "Abilities:\n(Ability)Player of Banquo is known",
            "Abilities:\n(Ability)Could kill the King\n(Ability)     Murder of King -> Additional Position King\n(Ability)Could kill Banquo\n(Ability)Could kill Fleance\n(Ability)All people know the survival of King\n(Ability)     Killer of King is identified for a random player"
    };

}
/**Stores Basic variables and functions that every character should have*/
abstract class Character implements Position, Name, VictoryCondition, Ability{
    /**Variables for each Character*/
    boolean alive;              //Identifier for checking whether the character is alive
    String position;            //Identifier for checking character's position
    String name;                //Identifier for checking character's name
    String victoryCondition;    //Stores the victory Condition of each player
    String ability;             //Stores the abilities for each player
    boolean victorious;         //Identifier for checking whether the character is victorious

    /**Initialize variables that all character should be same at the beginning*/
    protected Character(){
        alive = true;           //make character alive
        victorious = false;     //make character not victorious
    }

    /**Getter and Setter for variables in each Character*/
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setPosition(int position) {
        this.position = positions[position];
    }
    public  String getPosition() {
        return position;
    }
    public  String getName(){
        return name;
    }
    public String getVictoryCondition(){
        return victoryCondition;
    }
    public String getAbility(){
        return ability;
    }
    public boolean isVictorious() {
        return victorious;
    }
}

/**Stores Information and victory condition about Character Duncan*/
class Duncan extends Character{
    public Duncan(){
        super();                                                //call basic settings that all characters share
        position = positions[0];                                //sets position for the character
        name = names[0];                                        //sets name for the character
        victoryCondition = victoryConditions[0];                //sets victory condition description for the character
        ability = abilities[0];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(Game.round==6&&alive&&PlayableCharacters.getCharacter(2).alive&&PlayableCharacters.getCharacter(4).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Banquo*/
class Banquo extends Character{
    public Banquo() {                                           //Constructor for the character
        super();                                                //call basic settings that all characters share
        position =positions[1];                                 //sets position for the character
        name = names[1];                                        //sets name for the character
        victoryCondition = victoryConditions[1];                //sets victory condition description for the character
        ability = abilities[1];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(alive&&PlayableCharacters.getCharacter(6).alive&&!PlayableCharacters.getCharacter(7).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Malcom*/
class Malcom extends Character{
    public Malcom() {                                           //Constructor for the character
        super();                                                //call basic settings that all characters share
        position =positions[2];                                 //sets position for the character
        name = names[2];                                        //sets name for the character
        victoryCondition = victoryConditions[2];                //sets victory condition description for the character
        ability = abilities[2];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(alive&&!PlayableCharacters.getCharacter(0).alive&&!PlayableCharacters.getCharacter(7).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Macduff*/
class Macduff extends Character{
    public Macduff() {                                          //Constructor for the character
        super();                                                //call basic settings that all characters share
        position = positions[3];                                //sets position for the character
        name = names[3];                                        //sets name for the character
        victoryCondition = victoryConditions[3];                //sets victory condition description for the character
        ability = abilities[3];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(PlayableCharacters.getCharacter(0).alive&&PlayableCharacters.getCharacter(2).alive&&!PlayableCharacters.getCharacter(4).alive&&!PlayableCharacters.getCharacter(7).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Donalbain*/
class Donalbain extends Character{
    public Donalbain() {                                        //Constructor for the character
        super();                                                //call basic settings that all characters share
        position = positions[4];                                //sets position for the character
        name = names[4];                                        //sets name for the character
        victoryCondition = victoryConditions[4];                //sets victory condition description for the character
        ability = abilities[4];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(alive&&PlayableCharacters.getCharacter(0).alive&&!PlayableCharacters.getCharacter(2).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Lady Macbeth*/
class LadyMacbeth extends Character{
    public LadyMacbeth(){                                       //Constructor for the character
        super();                                                //call basic settings that all characters share
        position = positions[5];                                //sets position for the character
        name = names[5];                                        //sets name for the character
        victoryCondition = victoryConditions[5];                //sets victory condition description for the character
        ability = abilities[5];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(PlayableCharacters.getCharacter(7).alive&&!PlayableCharacters.getCharacter(0).alive&&(!PlayableCharacters.getCharacter(2).alive||!PlayableCharacters.getCharacter(3).alive)&&!PlayableCharacters.getCharacter(1).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Fleance*/
class Fleance extends Character{
    public Fleance() {                                          //Constructor for the character
        super();                                                //call basic settings that all characters share
        position =positions[6];                                 //sets position for the character
        name = names[6];                                        //sets name for the character
        victoryCondition = victoryConditions[6];                //sets victory condition description for the character
        ability = abilities[6];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(alive&&!PlayableCharacters.getCharacter(1).alive&&!PlayableCharacters.getCharacter(2).alive&&!PlayableCharacters.getCharacter(4).alive&&!PlayableCharacters.getCharacter(7).alive){
            return true;
        }
        return false;
    }
}
/**Stores Information and victory condition about Character Macbeth*/
class Macbeth extends Character{
    public Macbeth() {                                          //Constructor for the character
        super();                                                //call basic settings that all characters share
        position = positions[7];                                //sets position for the character
        name = names[7];                                        //sets name for the character
        victoryCondition = victoryConditions[7];                //sets victory condition description for the character
        ability = abilities[7];                                 //sets ability description for the character
    }

    @Override
    public boolean isVictorious() {                             //Check if the character is victory
        if(alive&&!PlayableCharacters.getCharacter(0).alive&&(!PlayableCharacters.getCharacter(2).alive||!PlayableCharacters.getCharacter(3).alive)&&!PlayableCharacters.getCharacter(6).alive){
            return true;
        }
        return false;
    }
}
