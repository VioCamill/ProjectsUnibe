package snakes;

import org.junit.jupiter.api.Test;
import snakes.squares.RollAgainSquare;
import snakes.squares.SkipSquare;
import snakes.squares.WormholeEntranceSquare;
import snakes.squares.WormholeExitSquare;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {

    //Runs 100 random games
    @Test
    public void randomGames () {
        for (int i = 0; i < 1000; i++) {
            Game.main();
        }
    }

    @Test
    public void testGame() {
        Queue<Player> players = new LinkedList<>();
        players.add(new Player("Jon"));
        players.add(new Player("Ygritte"));
        Game game = new Game(15, players, 6);
        game.setSquareToLadder(2, 4);
        game.setSquareToLadder(6, 2);
        game.setSquareToSnake(11, -6);
        game.setSquare(14, new SkipSquare(game, 14));
        game.setSquare(3, new WormholeEntranceSquare(game, 3));
        game.setSquare(13, new WormholeExitSquare(game, 13));
        game.setSquare(2, new WormholeExitSquare(game, 2));
        game.setSquare(10, new RollAgainSquare(game, 10));
        Die die = new snakes.Die(6);
        game.play(die);

    }

    @Test
        public void gameTestNoWormholeException() {
        Queue<Player> players = new LinkedList<>();
        players.add(new Player("Jon"));
        players.add(new Player("Ygritte"));
        Game game = new Game(15, players, 6);
        game.setSquareToLadder(2, 4);
        game.setSquareToLadder(6, 2);
        game.setSquareToSnake(11, -6);
        game.setSquare(14, new SkipSquare(game, 14));
        game.setSquare(3, new WormholeEntranceSquare(game, 3));
        game.setSquare(10, new RollAgainSquare(game, 10));

        Die die = mock(Die.class);
        when(die.roll()).thenReturn(2, 14);
        game.play(die);
    }

    @Test
    public void GameNotOverExceptionTest() {
        Player player1 = mock(Player.class);
        when(player1.position()).thenReturn(1);
        Player player2 = mock(Player.class);
        when(player1.position()).thenReturn(1);
        Queue<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);
        Game game = new Game(10, players, 2);
        assertThrows(GameNotOverException.class, () -> game.winner());


    }


}