package ludo;

import java.util.Scanner;

/**
 * Takes the input and returns a 1 if the lowercase pieces should be moved or a 2 if the uppercase is chosen.
 *
 *
 */

public class CommandParser {


    /**
     *
     * Is used to determin a players choice
     *
     * precondition is only called, when needed
     *
     * postcondition always returns a 1 oder 2
     *
     * @return either 1 oder 2
     */


    public int playerDecision (){
        int input = 0;
        Scanner scan = new Scanner(System.in);

        try {
            input = scan.nextInt();
            assert(input==1||input==2);
            if (!(input==1||input==2)) throw new Exception();

        } catch (Exception e) {
            System.out.println("This is not a valid move, please chose 1 or 2");
            // recursive calls the methode, to always return a valid move
            input = playerDecision();
        }

        assert (input == 1||input ==2);

        return input;

    }

    public int playerDecision (boolean isBotGame){
        Die die = new Die(2);
        if (isBotGame) {
            int result = die.roll();
            assert (result ==1||result ==2);
            return die.roll();
        }
        int result = playerDecision();
        return result;

    }

    //asks the name of the player.
    public String askPlayerName(int i){
        System.out.println("Hello player " + i +" what's your name?");

        boolean done = false;
        Scanner scan = new Scanner(System.in);
        String name = scan.next();
        while (nameIsNotOk(name)) {
            if (name.length()<3) {
                System.out.println(name + " is too short.");
            }
            else {
                System.out.println(name + " is too long.");
            }
            name = scan.next();
        }
        System.out.println(name + " is a beautiful name.");
        assert (name.length()>3&&name.length()<21);
        return name;
    }

// to avoid overloaded or short names.
    private boolean nameIsNotOk(String s){
        return s.length()<3||s.length()>20;
    }

    public boolean doYouWantToRollTheDie (Player p) {

        System.out.println(p.getName() + " are your ready to roll the die? Press any key");
        Scanner scan = new Scanner(System.in);
        while (!(scan.hasNext())) {}
        return true;

    }


}
