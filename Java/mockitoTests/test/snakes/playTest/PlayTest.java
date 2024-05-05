package snakes.playTest;

import org.junit.jupiter.api.Test;

import snakes.IDie;
import snakes.Game;
import snakes.Player;
import snakes.squares.RollAgainSquare;
import snakes.squares.SkipSquare;

import java.util.LinkedList;
import java.util.Queue;


import static org.mockito.Mockito.*;

/**
 * Tests the Game#play methode with two different mockings of Die Class, once using Mockito, once using the self created
 * class MockDie
 *
 * @author Tim Ambühl
 */


public class PlayTest {

    private Player player1;
    private Player player2;
    private Player player3;

    Game game;
    Queue<Player> player = new LinkedList<Player>();


    public Game newGame(int number_of_players) {
        this.player1 = new Player("Jon");
        this.player2 = new Player("Ygritte");
        this.player3 = new Player("Sam");

        player.add(player1);
        player.add(player2);

        if (number_of_players == 3)
            player.add(player3);


        this.game = new Game(10, player, 6);
        game.setSquareToLadder(2, 5);
        game.setSquareToLadder(6, 2);
        game.setSquareToSnake(4, -3);
        game.setSquareToSnake(7, -2);
        game.setSquare(9, new SkipSquare(game, 9));
        game.setSquare(3, new RollAgainSquare(game, 3));

        return game;

    }


    /**
     * Testing all fields using Mockito
     */
    @Test
    public void playTestMockito() {
        IDie die = mock(IDie.class);
        newGame(2);

        when(die.roll()).thenReturn(1, 2, 1, 1, 5, 1, 1, 1);

        this.game.play(die);


    }


    /**
     * Testing every kind of special square using MockDie class
     */
    @Test
    public void playTestMockDie() {
        IDie die = new MockDie();
        newGame(2);

        this.game.play(die);
    }


}
