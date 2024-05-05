package ludo.square;

import ludo.Game;
import ludo.Piece;
import ludo.Player;

/**
 * every HomeSquare is connected with a player
 * if a piece enters the game it is responsible to put the piece to the correct StartSquare from its player
 */

public class HomeSquare extends StandardSquare {

    private Player player;
    private StartSquare startSquare;

    public HomeSquare(Game ludo, int position, Piece piece) {
        super(ludo, position);
        this.enter(piece);
        this.player = piece.getPlayer();
        this.startSquare = player.getStartSquare();
    }


    @Override
    public boolean isHomeSquare() {
        return true;
    }

    public void enterToGame(Piece piece) {
        this.leave(piece);
        startSquare.enter(piece);
    }

    public String toString() {
        return this.isOccupied() ? this.occupiedBy().getName() : " ";
    }

    public StartSquare getStartSquare() {
        return this.startSquare;
    }
}
