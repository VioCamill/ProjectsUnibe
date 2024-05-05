package ludo.square;

import ludo.Game;
import ludo.Piece;

/**
 * Default square type
 *
 * It can have only one piece on it. If another piece enters the square, the actual
 * piece on it is sent back to its HomeSquare
 */
public class StandardSquare extends Square {
    private Piece piece;

    public StandardSquare(Game ludo, int position){
        this.position = position;
        this.ludo = ludo;
    }
    @Override
    public boolean isStartSquare() {
        return false;
    }
    @Override
    public boolean isGoalSquare() {
        return false;
    }

    @Override
    public boolean isGoalRunSquare(Piece piece) {
        return false;
    }

    @Override
    public boolean isHomeSquare() {
        return false;
    }

    @Override
    public boolean isOccupied() {
        return this.piece != null;
    }

    @Override
    public Piece occupiedBy() {
        return this.piece;
    }


    @Override
    public void sendBackHome(Piece pc) {
        HomeSquare homeSquare = pc.getHomeSquare();
        this.leave(pc);
        homeSquare.enter(pc);
        //Prints out a message, if a piece was sent home
        System.out.println("Piece " + pc.getName() + " was sent home");
    }

    /**
     * precondition : method only enters a square if it is empty
     * post condition : this piece is on this square
     * @param piece to enter the Square
     */
    @Override
    public void enter(Piece piece) {
        assert this.piece == null;
        this.piece = piece;
        piece.setCurrentSquare(this); // sets the current Square from piece
        assert this.piece == piece;

    }

    /**
     * precondition : there is actual a piece on this square
     * post condition : this square is empty
     * @param piece to leave the Square
     */
    @Override
    public void leave(Piece piece) {
        assert this.piece == piece;
        this.piece = null;
        piece.setCurrentSquare(null); //delete this as the current Square from piece
        assert this.piece == null;
    }


    public String toString(){
        return this.isOccupied() ? this.occupiedBy().getName() : "-";
    }
}
