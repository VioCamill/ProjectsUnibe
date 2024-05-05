package snakes.squares;

import org.junit.jupiter.api.Test;
import snakes.Game;
import snakes.IDie;
import snakes.Player;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the RollAgainSquare class.
 *
 */




class RollAgainSquareTest {
    @Test
    public void testMoveAndLandOnly() {
        snakes.Game game = mock(Game.class);
        snakes.squares.Square testSquare;
        snakes.squares.Square start, stop;

        when(game.isValidPosition(anyInt())).thenReturn(true); //tell the mocked game class what to do if isValidPosition() is called
        testSquare = new RollAgainSquare(game, 1); //create square on which we want to test moveAndLand()
        start = mock(snakes.squares.Square.class); //mock for findSquare()
        stop = mock(snakes.squares.Square.class); //mock for landHereOrGoHome()

        when(game.findSquare(1, 2)).thenReturn(start);
        when(start.landHereOrGoHome()).thenReturn(stop);

        Square destination = testSquare.moveAndLand(2);
        assertEquals(stop, destination); //actual test for testSquare
    }

    /**
     * Runs serveral random games with two roll again squares.
     */
    @Test
    public void gameTest() {
        for (int i = 0; i < 10; i++) {
            Queue<Player> players = new LinkedList<>();
            players.add(new Player("Jon"));
            players.add(new Player("Ygritte"));
            Game game = new Game(15, players, 6);

            game.setSquare(5, new RollAgainSquare(game, 5));

            game.setSquare(10, new RollAgainSquare(game, 10));
            snakes.Die die = new snakes.Die(6);
            game.play(die);

        }


    }

    /**
     *
     * Test, that after a land on the rollAgainSquare, the same player is the current player and gets to roll agian
     */
    @Test
    public void reRoll() {
        snakes.Game game = mock(Game.class);
        snakes.squares.Square testSquare;
        snakes.squares.Square rollAgainSquare = mock(RollAgainSquare.class);
        Queue<Player> players = new LinkedList<>();
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        players.add(p1);
        players.add(p2);
        game = new Game(15, players, 6);
        testSquare = new RollAgainSquare( game, 2);
        assert game.currentPlayer().equals(p1);
        game.movePlayer(1); //does also rotate the que
        assert game.currentPlayer().equals(p2); // asserts, that player p2 gets to roll again

    }

    @Test
    public void numbersOfPlayers() {
        Queue<Player> players = new LinkedList<>();
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        Player p3 = new Player("P3");
        players.add(p1);
        players.add(p2);

        snakes.Game game = new Game(15, players, 6);
        assert game.numbersOfPlayers()==2;
        players.add(p3);
        game = new Game(15,players,6);
        assert game.numbersOfPlayers()==3;


        }






    }





















