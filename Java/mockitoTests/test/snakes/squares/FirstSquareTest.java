package snakes.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snakes.Game;
import snakes.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FirstSquareTest {
    private FirstSquare firstSquare;
    private List<Player> players;

    @BeforeEach
    public void newFirstSquare() {
        snakes.Game game = mock(Game.class);
        when(game.isValidPosition(anyInt())).thenReturn(true);
        firstSquare = new FirstSquare(game, 1);
        ArrayList<Player> players = new ArrayList<>();
        this.players = players;
        firstSquare.setList(players);
    }

    @Test
    public void testLandHereOrGoHome() {
        newFirstSquare();
        snakes.squares.Square destination;
        destination = firstSquare.landHereOrGoHome();
        assertEquals(firstSquare, destination, "Landing on this square is always possible");

    }

    @Test
    public void testIsOccupied() {
        newFirstSquare();
        Player player1 = mock(Player.class);
        players.add(player1);
        assertTrue(firstSquare.isOccupied());
    }

    @Test
    public void testEnter() {
        newFirstSquare();
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        firstSquare.enter(player1);
        assertTrue(players.contains(player1), "player1 is on the firstSquare");
        firstSquare.enter(player2);
        assertTrue(players.contains(player2), "player2 is on the firstSquare");
    }

    @Test
    public void testLeave() {
        newFirstSquare();
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        players.add(player1);
        players.add(player2);
        firstSquare.leave(player1);
        assertFalse(this.players.contains(player1), "player1 has left firstSquare");
    }
    @Test
    public void isFirstSquare() {
        assertTrue(firstSquare.isFirstSquare());
    }

}
