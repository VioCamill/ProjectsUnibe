package Squares;

import ludo.Game;
import ludo.Piece;
import ludo.Player;
import ludo.square.HomeSquare;
import ludo.square.StartSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


public class HomeSquareTest {

    private Game ludo;
    private Piece piece;
    LinkedList<Player> playerList;
    private HomeSquare homeSquare;
    private StartSquare startSquare;
    @BeforeEach
    public void initHomeSquare(){
        ludo = new Game();
        playerList = (LinkedList<Player>) ludo.getPlayers();
        Player player1 = playerList.get(0);
        piece = player1.getPieces()[0];
        homeSquare = piece.getHomeSquare();
        startSquare = homeSquare.getStartSquare();
    }
    @Test
    public void testEnterToGame() {
        assertTrue (homeSquare.isOccupied());
        homeSquare.enterToGame(piece);
        assertFalse(homeSquare.isOccupied());
        assertEquals(piece, startSquare.occupiedBy());

    }

}
