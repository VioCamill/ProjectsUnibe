package snakes.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snakes.Game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class WormholeEntranceSquareTest {
    List<Square> exits;
    private WormholeEntranceSquare wormholeEntrance;
    private Game game;

    @BeforeEach
    public void newWormholeEntranceSquare() {
        Game game = mock(Game.class);
        this.game = game;
        when(game.isValidPosition(anyInt())).thenReturn(true);
        this.wormholeEntrance = new WormholeEntranceSquare(game, 2);
        exits = new ArrayList<>();
    }

    @Test
    public void testLandHereOrGoHome() {
        newWormholeEntranceSquare();
        Square exitSquare = mock(Square.class);
        when(exitSquare.landHereOrGoHome()).thenReturn(exitSquare);
        exits.add(exitSquare);
        when(game.wormholeExits()).thenReturn(exits);
        assertEquals(exitSquare, wormholeEntrance.landHereOrGoHome(),
                "land on the exitSquare if one exists");
    }

    @org.junit.Test
    public void testLandHereOrGoHomeWithException() {
        newWormholeEntranceSquare();
        when(game.wormholeExits()).thenReturn(exits); //exits is an empty List
        assertEquals(wormholeEntrance, wormholeEntrance.landHereOrGoHome());
    }

    @Test
    public void testSquareLabel() {
        newWormholeEntranceSquare();
        assertEquals("[2 (Entrance)]", wormholeEntrance.toString());
    }
}

