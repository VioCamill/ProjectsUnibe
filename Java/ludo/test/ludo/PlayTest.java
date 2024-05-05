package ludo;

import ludo.render.BoardRenderer;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * In this class we test some specific scenarios of the game
 *
 */



public class PlayTest {


    public Game initGame() {
        LinkedList<Player> players = new LinkedList<>();

        players.add(new Player("Frodo Baggins"));
        players.add(new Player("Samwise Gamgee"));
        players.add(new Player("Merriadoc Brandybock"));
        players.add(new Player("Peregrin Tuc"));
        Game game = new Game(players);

        return game;
    }

    /**
     * Negative case test, test if rollAgain works correctly when the player can't roll again
     *
     */
    @Test
    public void playTest_testRollAgain_false() {
        Game game = initGame();
        BoardRenderer renderer = new BoardRenderer();

        Movement movement = new Movement(game);
        Player player1 = game.currentPlayer();
        Piece piece1 = player1.getPieces()[0];
        Piece piece2 = player1.getPieces()[1];

        movement.setPieceToSquare(piece1, player1.getGoalSquares().get(1));
        piece1.setIsOnGoalSquare();
        movement.setPieceToSquare(piece2, game.getSquare(35)); //35 is the first goalRunSquare for p1

        movement.move(player1, 4); //move onto Goal Run Square
        movement.move(player1, 6); //move would overshoot, should get caught, canRollAgain should be false after this

        assertFalse(game.getCanRollAgain());

    }


    /**
     * Positiv case test, test if rollAgain works correctly when the player can roll again
     *
     */
    @Test
    public void playTest_testRollAgain_true() {
        Game game = initGame();

        Movement movement = new Movement(game);
        Player player1 = game.currentPlayer();
        Piece piece1 = player1.getPieces()[0];
        Piece piece2 = player1.getPieces()[1];

        movement.setPieceToSquare(piece1, player1.getGoalSquares().get(1));
        piece1.setIsOnGoalSquare();
        movement.setPieceToSquare(piece2, game.getSquare(33));

        movement.move(player1, 6); //move six, canRoll again still true

        assertTrue(game.getCanRollAgain());
        movement.move(player1, 6); //move six again, canRoll again should be false now

        assertFalse(game.getCanRollAgain());

    }


    /**
     * Test if a piece can overshoot the goal
     *
     */

    @Test
    public void playTest_overshootTest() {
        Game game = initGame();

        Movement movement = new Movement(game);
        Player player1 = game.currentPlayer();
        Piece piece1 = player1.getPieces()[0];
        Piece piece2 = player1.getPieces()[1];

        movement.setPieceToSquare(piece1, player1.getGoalSquares().get(1));
        piece1.setIsOnGoalSquare();
        movement.setPieceToSquare(piece2, game.getSquare(38));

        movement.move(player1, 6);

        assertTrue(piece2.getSquare() == game.getSquare(38));
    }
}
