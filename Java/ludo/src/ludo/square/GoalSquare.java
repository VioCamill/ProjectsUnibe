package ludo.square;

import ludo.Game;
import ludo.Piece;
import ludo.Player;

/**
 * is connected with a player
 * only pieces from this player can enter the GoalSquare
 */

public class GoalSquare extends StandardSquare {
    Player player;

    public GoalSquare(Game ludo, int position, Player player) {
        super(ludo, position);
        this.player = player;
    }

    /**
     * precondition: only pieces from this player can enter the square
     *
     * @param piece to enter the Square
     */
    @Override
    public void enter(Piece piece) {
        assert piece.getPlayer() == this.player;
        super.enter(piece);
        piece.setIsOnGoalSquare();
    }


    @Override
    public boolean isGoalSquare() {
        return true;
    }

    public String toString() {
        return this.isOccupied() ? this.occupiedBy().getName() : "$";
    }
}
