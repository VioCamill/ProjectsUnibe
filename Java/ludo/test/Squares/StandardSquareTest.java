package Squares;

import ludo.Game;
import ludo.Piece;
import ludo.Player;
import ludo.square.HomeSquare;
import ludo.square.StandardSquare;
import ludo.square.StartSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StandardSquareTest {

    private Game ludo;
    private StandardSquare square1, square2;
    private Player player;
    private HomeSquare homeSquare;
    private StartSquare startSquare;
    private Piece piece;

    @BeforeEach
    public void initSquares() {
        ludo = mock(Game.class);
        when(ludo.findSquare(5, 5)).thenReturn(square2);
        square1 = new StandardSquare(ludo, 5);
        square2 = new StandardSquare(ludo, 10);
        when(ludo.findSquare(5, 5)).thenReturn(square2);
        player = mock(Player.class);
        startSquare = new StartSquare(ludo, 0, player);
        piece = new Piece('A', player);
        when(player.getStartSquare()).thenReturn(startSquare);
        homeSquare = new HomeSquare(ludo, 0, piece);
        piece.setHomeSquare(homeSquare);
        homeSquare.enterToGame(piece);
    }

    @Test
    public void testIsStartSquare() {
        assertFalse(square1.isStartSquare());
    }

    @Test
    public void testIsGoalSquare() {
        assertFalse(square1.isGoalSquare());
    }

    @Test
    public void TestEnter() {
        square1.enter(piece);
        assertTrue(square1.isOccupied());
        assertEquals(piece, square1.occupiedBy());
    }

    @Test
    public void TestLeave() {
        square1.enter(piece);
        square1.leave(piece);
        assertFalse(square1.isOccupied());
    }
    @Test
    public void TestSendBackHome() {
        square2.enter(piece);
        square2.sendBackHome(piece);
        assertEquals(piece, homeSquare.occupiedBy());
    }
}

