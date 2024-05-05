package snakes.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snakes.Game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WormholeExitSquareTest {

    private Square wormholeExit;

    @BeforeEach

    public void newWormholeExit() {
        Game game = mock(Game.class);
        when(game.isValidPosition(anyInt())).thenReturn(true);
        wormholeExit = new WormholeExitSquare(game, 3); // set a position where this square should be tested
    }

    @Test
    public void testIsWormholeExit() {
        newWormholeExit();
        assertTrue(wormholeExit.isWormholeExit());
    }

    @Test
    public void testSquareLabel() {
        newWormholeExit();
        assertEquals("[3 (Exit)]", this.wormholeExit.toString());
    }

}
