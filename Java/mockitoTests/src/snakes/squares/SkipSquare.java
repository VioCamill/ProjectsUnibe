package snakes.squares;

import snakes.Game;

/**
 * Upon landing on a skip square, the next player is skipped.
 * In a two player game, this means that that the player landing on the SkipSquare can
 * roll again immediately.
 */
public class SkipSquare extends StandardSquare {

	public SkipSquare(Game game, int position) {
		super(game, position);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Skips the next player's turn.
	 * If the square is already occupied, go home.
	 * @return the new square on which the current player is
	 */
	@Override
	public Square landHereOrGoHome() {
		if (this.isOccupied()) {
			return game.firstSquare();
		};

		game.skipNextPlayer();
		return this;
	}

	@Override
	protected String squareLabel() {
		return super.squareLabel() + " (Skip)";
	}
}
