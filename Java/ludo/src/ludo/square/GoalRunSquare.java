package ludo.square;

import ludo.Game;
import ludo.Piece;
import ludo.Player;

/**
 * is connected with a player
 * every player has 5 GoalRunSquares
 * they are responsible, that a piece from this player is correctly put on a GoalSquare
 *
 */

public class GoalRunSquare extends StandardSquare {

    int distance1, distance2; //distances to goalSquares
    Player player;

    public GoalRunSquare(Game ludo, int position, Player player) {
        super(ludo, position);
        this.player = player;
    }

    public void setDistance1(int i) {
        distance1 = i;
    }

    public void setDistance2(int i) {
        distance2 = i;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean isGoalRunSquare(Piece piece) {
        return this.player == piece.getPlayer();
    }

    public int getDistance1() {
        return distance1;
    }

    public int getDistance2() {
        return distance2;
    }
}
