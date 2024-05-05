package Squares;

import ludo.Game;
import ludo.Piece;
import ludo.Player;
import ludo.square.GoalSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoalSquareTest {

    private Player player, otherPlayer;
    private Piece piece, otherPiece; //otherPiece to check if only the right piece can enter the goalSquare
    private Game ludo;
    private GoalSquare goalSquare; //goalSquare for player

    @BeforeEach
    public void initGoalSquare() {
        ludo = mock(Game.class);
        player = mock(Player.class);
        otherPlayer = mock(Player.class);
        piece = mock(Piece.class);
        when(piece.getPlayer()).thenReturn(player);
        otherPiece = mock(Piece.class);
        when(otherPiece.getPlayer()).thenReturn(otherPlayer);
        goalSquare = new GoalSquare(ludo, 0, player);
    }

    @Test
    public void testEnterPiece() {
        goalSquare.enter(piece);
        assertEquals(piece, goalSquare.occupiedBy(), "piece is able to enter goalSquare");
    }

    @Test
    public void testEnterOtherPiece() {
        assertThrows(AssertionError.class, () -> goalSquare.enter(otherPiece),
                "otherPiece is not from the right player. It is not possible for it to enter this goalSquare.");
    }
}
