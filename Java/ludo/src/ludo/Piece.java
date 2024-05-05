package ludo;


import ludo.square.HomeSquare;
import ludo.square.Square;
import ludo.square.StartSquare;


/**
 * class that represents a piece
 *
 */

public class Piece {


    private char name; //name of the piece

    private Player player; //player that owns the piece

    private Square currentSquare; //current square the piece is on

    private HomeSquare homeSquare; //home square of the piece

    private boolean onGoalSquare; //boolean that represents if the piece is on a goal square

    /**
     * Constructor for Piece
     * @param name name of the piece
     * @param player player that owns the piece
     */

    public Piece(char name, Player player) {
        this.name = name;
        this.player = player;
        this.currentSquare = null;
        this.onGoalSquare = false;
    }

    /**
     * @return true if the piece is on a home square
     */

    public boolean stillOnHomeSquare() {
        return currentSquare == homeSquare;
    }

    /**
     * sets the current Square from piece
     *
     * @param square is actual position of this piece
     */
    public void setCurrentSquare(Square square) {
        this.currentSquare = square;
    }

    /**
     * sets the home square of the piece
     * @param homeSquare home square of the piece
     */
    public void setHomeSquare(HomeSquare homeSquare) {
        this.homeSquare = homeSquare;
        setCurrentSquare(homeSquare);
    }

    /**
     * @return the player that owns the piece
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @return the square the piece is on
     */
    public Square getSquare() {
        return this.currentSquare;
    }


    /**
     * @return the position of the square the piece is on
     */
    public int getPosition() {
        return this.currentSquare.getPosition();
    }

    /**
     * @return the name of the piece
     */
    public String getName() {
        return Character.toString(this.name);
    }

    /**
     * @return the home square of the piece
     */
    public HomeSquare getHomeSquare() {
        return this.homeSquare;
    }

    /**
     * @return true if on a goal square
     */
    public boolean onGoalSquare() {
        return this.onGoalSquare;
    }

    /**
     * sets the piece on a goal square
     */
    public void setIsOnGoalSquare() {
        this.onGoalSquare = true;
    }
}
