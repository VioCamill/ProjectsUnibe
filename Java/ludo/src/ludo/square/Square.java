package ludo.square;
import ludo.Game;
import ludo.Piece;

/**
 * Square functionality common to all types of squares.
 */
public abstract class Square {
    protected int position;
    protected Game ludo;

    /**
     * @return position of Square
     */
    public int getPosition() {
        return this.position;
    }
    /**
     *
     * @return true if this Square is a StartSquare
     */
    public abstract boolean isStartSquare();

    /**
     *
     * @return true if this Square is a GoalSquare
     */
    public abstract boolean isGoalSquare();

    public abstract boolean isGoalRunSquare(Piece piece);

    public abstract boolean isHomeSquare();

    /**
     *
     * @return true if this Square is occupied
     */
    public abstract boolean isOccupied();

    /**
     *
     * @return the label of the Piece, that is actual on the Square
     */
    public abstract Piece occupiedBy();

    /**
     * method to send back home another piece
     */
    public abstract void sendBackHome(Piece piece);


    /**
     * enters a piece to this Square
     * @param piece to enter the Square
     */
    public abstract void enter(Piece piece);

    /**
     * removes a piece from this Square
     * @param piece to leave the Square
     */
    public abstract void leave(Piece piece);
}
