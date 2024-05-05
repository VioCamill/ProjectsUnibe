package snakes.squares;

import snakes.Game;
import org.junit.jupiter.api.Test;
import snakes.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** Simple test for testing SkipSquare. As SkipSquare doesn't actually do much, the real testing of the Game Function "Skip a player"
 * happens in the class Game.java and not here.
 *
 * @author Tim Ambühl
 */
public class SkipSquareTest {



    @Test
    public void landHereOrGoHomeTest() {
        Game game = mock(Game.class);
        Player testPlayer = mock(Player.class);
        Square firstSquare = mock(FirstSquare.class);


        when(game.isValidPosition(anyInt())).thenReturn(true);
        when(game.firstSquare()).thenReturn(firstSquare);

        Square testSquare = new SkipSquare(game, 1);
        testSquare.enter(testPlayer);

        Square destionation = testSquare.landHereOrGoHome();
        assertEquals(destionation, firstSquare);

        testSquare.leave(testPlayer);
        destionation = testSquare.landHereOrGoHome();
        assertEquals(destionation, testSquare);



    }
}
