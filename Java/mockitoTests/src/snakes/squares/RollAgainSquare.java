package snakes.squares;

import snakes.Game;
import snakes.Player;


/**
 * Overides the enter methode from StandardSquare and moves the player again if the square is empty.
 *Rotates the cue, so the active player can roll again. Does check invariant if it's empty because it always has to be.
 * Beacause you have to reroll and leave the square.
 *
 */
public class RollAgainSquare extends StandardSquare {
    snakes.Die die = new snakes.Die(6);


    public RollAgainSquare(Game game, int position) {
        super(game, position);
    }

    @Override
    public void enter(Player player) {
            assert this.player == null;
            this.player = player;
            int numbersOfPlayers = this.game.numbersOfPlayers();
            for (int i = 1; i<numbersOfPlayers; i++) this.game.rotatePlayerQueue();

    }
    @Override
    public void leave(Player player) {

        this.player = null;
    }




    @Override
    public String squareLabel() {
        return String.format("%d (RollAgain)", position);
    }
}
