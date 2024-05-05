package ludo;


import java.util.LinkedList;

/**
 * Starting a game, holds the main methode
 *
 * Asking the users how the want to play the game
 *
 *
 * preconditions: no preconditions
 *
 * postconditions: ends when the user has player a manual game
 *
 */

public class Driver {

    /**
     * Plays a demo game and ask the user if they want to roll the die manually and chose the names manually.
     */
    public static void main(String[] args) throws InterruptedException {

        System.out.println("The demo game starts in five seconds, sit back, relax and enjoy the show");
        for (int i = 0; i<5; i++) {
            System.out.print(i+1 + " ");
            Thread.sleep(1000);
        }
        System.out.println("");
        Game game = new Game();
        game.play();

        CommandParser commandParser = new CommandParser();

        //you get to chose if you want to be asked to roll the die every time
        System.out.println("This was a Demo game, now it's time for a real game." +
                "\nPress 1 if you want to roll the die manually or 2 if you are lazy");
        int decision = commandParser.playerDecision();
        assert (decision == 1|| decision == 2);
        boolean rollTheDieManually = decision==1? true : false;

        System.out.println("\nChose 1 for preconstructed names or 2 for your own names");
        decision = commandParser.playerDecision();

        Game game2;
        //creates a list with all the playernames and starts a game
        if (decision==2) {
            LinkedList<Player> players = new LinkedList<>();
            for (int i = 0; i<4; i++) {
                Player player = new Player(commandParser.askPlayerName(i+1));
                players.add(player);
            }
            assert players.size()==4;
            game2 = new Game(players);
        }

        else {
            LinkedList<Player> players = new LinkedList<>();
            players.add(new Player("Amir"));
            players.add(new Player("Bart"));
            players.add(new Player("Chiara"));
            players.add(new Player("Doris"));
            game2 = new Game(players);
            }
        game2.rollTheDieManual(rollTheDieManually);
        game2.play();

        //ends the program
        System.out.println("Thank you for playing our game");
    }

}
