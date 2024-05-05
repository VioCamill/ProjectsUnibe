package ludo;

import ludo.square.GoalSquare;
import ludo.square.StartSquare;

import java.util.ArrayList;

/**
 * Represents a Player in the Game
 */
public class Player {


    private Piece[] pieces = new Piece[2]; //array of pieces belonging to the player

    private String name; //name of the player
    private StartSquare startSquare; //start square of the player
    private ArrayList<GoalSquare> goalSquares; //goal squares of the player

    private boolean invariant() {
        return (this.name != null);
    }

    /**
     * Constructor for Player
     * @param name name of the player
     */
    public Player(String name) {
        this.name = name;
        this.goalSquares = new ArrayList<>();
        assert invariant();

    }

    /**
     * Adds a piece to the player
     * @param piece1 piece to be added
     * @param piece2 piece to be added
     */
    public void addPiece(Piece piece1, Piece piece2) {
        assert (piece1.getClass() == Piece.class && piece2.getClass() == Piece.class && pieces[0] == null && pieces[1] == null);
        this.pieces[0] = piece1;
        this.pieces[1] = piece2;
        assert (pieces.length == 2);
    }

    /**
     * @return the pieces of the player
     */
    public Piece[] getPieces() {
        return this.pieces;
    }

    /**
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a goal square to the player
     * @param goalSquare goal square to be added to the player
     */
    public void addGoalSquare(GoalSquare goalSquare) {
        this.goalSquares.add(goalSquare);
    }

    /**
     * @return the goal squares of the player
     */
    public ArrayList<GoalSquare> getGoalSquares() {
        return this.goalSquares;
    }

    /**
     * @return the start square of the player
     */
    public StartSquare getStartSquare() {
        return this.startSquare;
    }

    /**
     * Sets the start square of the player
     * @param startSquare start square of the player
     */
    public void setStartSquare(StartSquare startSquare){
        this.startSquare = startSquare;
    }
}
