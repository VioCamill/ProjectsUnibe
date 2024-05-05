package snakes.squares;

import org.junit.jupiter.api.Test;
import snakes.Game;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LastSquareTest {
    private Square lastSquare;

    @Test
    public void testIsLastSquare() {
        Game game = mock(Game.class);
        when(game.isValidPosition(anyInt())).thenReturn(true);
        lastSquare = new LastSquare(game, 5); //position where the lastSquare is
        assertTrue(lastSquare.isLastSquare(), "this square is the lastSquare");
    }
}
