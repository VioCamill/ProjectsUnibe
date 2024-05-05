package snakes.squares;

import org.junit.jupiter.api.Test;
import snakes.Game;
import snakes.Player;
import snakes.squares.Square;
import snakes.squares.StandardSquare;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 *  Tests for all the public StandardSquare Methods
 *
 * @author Tim Ambühl
 */

public class StandardSquareTest {
	@Test
	public void testMoveAndLandOnly() {
		snakes.Game game = mock(Game.class);
		snakes.squares.Square testSquare;
		snakes.squares.Square start, stop;

		when(game.isValidPosition(anyInt())).thenReturn(true); //tell the mocked game class what to do if isValidPosition() is called
		testSquare = new StandardSquare(game, 1); //create square on which we want to test moveAndLand()
		start = mock(snakes.squares.Square.class); //mock for findSquare()
		stop = mock(snakes.squares.Square.class); //mock for landHereOrGoHome()

		when(game.findSquare(1, 2)).thenReturn(start);
		when(start.landHereOrGoHome()).thenReturn(stop);

		Square destination = testSquare.moveAndLand(2);
		System.out.println(destination.toString());
		assertEquals(stop, destination); //actual test for testSquare
	}


	@Test
	public void landHereOrGoHomeTest_occupied() {

		snakes.Game game = mock(Game.class);
		snakes.squares.Square testSquare;
		snakes.squares.Square destination;
		snakes.squares.Square firstSquare = mock(FirstSquare.class);
		Player testPlayer = new Player("testy"); //creates a testPlayer

		when(game.isValidPosition(anyInt())).thenReturn(true);
		when(game.firstSquare()).thenReturn(firstSquare);

		testSquare = new StandardSquare(game, 2);
		testSquare.enter(testPlayer);



		destination = testSquare.landHereOrGoHome();
		assertEquals(firstSquare, destination);


	}

	@Test
	public void landHereOrGoHomeTest_unoccupied() {
		snakes.Game game = mock(Game.class);
		snakes.squares.Square testSquare;
		snakes.squares.Square destination;

		when(game.isValidPosition(anyInt())).thenReturn(true);

		testSquare = new StandardSquare(game, 2);

		destination = testSquare.landHereOrGoHome();
		assertEquals(testSquare, destination);
	}



	@Test
	public void isOccupied_enter_leave_Test() {
		snakes.Game game = mock(Game.class);
		snakes.Player player = mock(Player.class);
		snakes.squares.Square testSquare;
		when(game.isValidPosition(anyInt())).thenReturn(true);

		testSquare = new StandardSquare( game, 1);
		testSquare.enter(player);

		assertTrue(testSquare.isOccupied());

		testSquare.leave(player);
		assertFalse(testSquare.isOccupied());
	}
}
