package snakes.squares;

import org.junit.jupiter.api.Test;

import snakes.Game;
import snakes.childClasses.SnakeSquareChild;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** Test class for the SnakeSquare
 *
 * Tests the remaining untested methode, the rest has already been tested in other classes, as this one extends LadderSquare
 * and LadderSquare extends StandardSquare
 *
 * @author Tim Ambühl
 *
 */

public class SnakeSquareTest {


    @Test
    public void isValidTransportTest_true() {
        Game game = mock(Game.class);
        when(game.isValidPosition(anyInt())).thenReturn(true);

        SnakeSquareChild testSquare = new SnakeSquareChild(-2, game, 1);

        boolean destination = testSquare.isValidTransport(2);

        assertFalse(destination);


    }

    @Test
    public void isValidTransportTest_false() {
        Game game = mock(Game.class);
        when(game.isValidPosition(anyInt())).thenReturn(true);

        SnakeSquareChild testSquare = new SnakeSquareChild(-2, game, 4);

        boolean destination = testSquare.isValidTransport(-2);

        assertFalse(!destination);

    }

}

