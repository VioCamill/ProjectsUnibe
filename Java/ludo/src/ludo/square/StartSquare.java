package ludo.square;

import ludo.Game;
import ludo.Player;

/**
 * every StartSquare is connected with one Player
 */
public class StartSquare extends StandardSquare {

    private Player player;


    public StartSquare(Game ludo, int position, Player player) {
        super(ludo, position);
        this.player = player;
        this.position = position;
    }

    @Override
    public boolean isStartSquare() {
        return true;
    }

    public String toString(){
        return this.isOccupied()? this.occupiedBy().getName() : "*";
    }
}
