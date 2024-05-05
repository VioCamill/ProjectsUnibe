package Squares;

import ludo.Game;
import ludo.Piece;
import ludo.Player;
import ludo.square.GoalRunSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoalRunTest {
    private Game ludo;
    private GoalRunSquare goalRunSquare;
    private Player player;
    Piece piece;


    @BeforeEach
    public void initGoalRunSquares(){
        ludo = mock(Game.class);
        player = mock(Player.class);
        piece = mock(Piece.class);
        when(piece.getPlayer()).thenReturn(player);
        goalRunSquare = new GoalRunSquare(ludo, 35, player);
    }

    @Test
    public void testIsGoalRunSquare(){
        assertTrue(goalRunSquare.isGoalRunSquare(piece));
    }

}
