package classTest;


import ludo.Piece;
import ludo.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {



    public Piece[] setUpPieces(Player player) {
        Piece[] pieces = new Piece[2];

        pieces[0] = new Piece('A', player);
        pieces[1] = new Piece('a', player);

        return pieces;
    }


    @Test
    public void testPlayerCreation() {

        Player player1 = new Player("Obi Wan Kenobi");

        assertEquals(player1.getName(), "Obi Wan Kenobi");

    }


    @Test
    public void testAddPieces() {
        Player testPlayer = new Player("Jar Jar Binks");
        Piece[] pieces = setUpPieces(testPlayer);

        testPlayer.addPiece(pieces[0], pieces[1]);

        assertEquals(testPlayer.getPieces()[0], pieces[0]);
        assertEquals(testPlayer.getPieces()[1], pieces[1]);
    }




}
