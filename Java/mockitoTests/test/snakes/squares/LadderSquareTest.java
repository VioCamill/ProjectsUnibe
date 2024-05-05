package snakes.squares;

import org.junit.jupiter.api.Test;
import snakes.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** Test class for LadderSquare
 *
 * @author Tim Ambühl
 *
 */
public class LadderSquareTest {

    @Test
    public void LandHereOrGoHomeTest_unoccupied() {
        Game game = mock(Game.class);
        Square destinationSquare = mock(StandardSquare.class);

        when(destinationSquare.landHereOrGoHome()).thenReturn(destinationSquare);
        when(game.isValidPosition(anyInt())).thenReturn(true);
        when(game.getSquare(anyInt())).thenReturn(destinationSquare);

        Square testSquare = new LadderSquare(2, game, 1);

        Square goalSquare = testSquare.landHereOrGoHome();
        assertEquals(destinationSquare, goalSquare);

    }

    @Test
    public void LandHereOrGoHomeTest_occupied() {
        Game game = mock(Game.class);
        Square destinationSquare = mock(StandardSquare.class);
        Square firstSquare = mock(FirstSquare.class);

        when(destinationSquare.landHereOrGoHome()).thenReturn(firstSquare);
        when(game.isValidPosition(anyInt())).thenReturn(true);
        when(game.getSquare(anyInt())).thenReturn(destinationSquare);

        Square testSquare = new LadderSquare(2, game, 1);

        Square goalSquare = testSquare.landHereOrGoHome();
        assertEquals(firstSquare, goalSquare);
    }
}
