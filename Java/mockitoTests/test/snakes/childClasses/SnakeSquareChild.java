package snakes.childClasses;

import snakes.Game;

public class SnakeSquareChild extends snakes.squares.SnakeSquare {

    /**
     * Child class of SnakeSquare, for testing purposes
     *
     * @param transport length of the snake, must be within the bounds of the game and < 0
     * @param game      host game, must not be null
     * @param position  position of the ladder start in the game
     *
     * @author Tim Ambühl
     */
    public SnakeSquareChild(int transport, Game game, int position) {
        super(transport, game, position);
    }

    @Override
    public boolean isValidTransport(int transport) {
        return transport < 0 &&
                game.isValidPosition(position + transport);
    }
}
