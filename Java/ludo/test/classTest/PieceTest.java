package classTest;

import ludo.Game;
import ludo.Piece;
import ludo.Player;
import ludo.square.Square;
import ludo.square.StandardSquare;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PieceTest {




    public Game initGame() {
        LinkedList<Player> players = new LinkedList<>();
        players.add(new Player("Price"));
        players.add(new Player("Soap"));
        players.add(new Player("Ghost"));
        players.add(new Player("Roach"));
        Game game = new Game(players);
        return game;
    }



    @Test
    public void pieceInitTest() {

        Game game = initGame();
        Player testPlayer = new Player("Thanos");
        Piece testPiece = new Piece('A', testPlayer);

        assertEquals(testPiece.getPlayer(), testPlayer);
        assertEquals(testPiece.getName(), "A");
        assertEquals(testPiece.getSquare(), null);
    }

}
