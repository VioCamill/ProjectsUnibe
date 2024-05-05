package render;

import ludo.Game;
import ludo.Player;
import ludo.render.BoardRenderer;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**Tests BoardRenderer and BoardToStringBuffer
 *
 *
 */

public class BoardRendererTest {

    BoardRenderer boardRenderer = new BoardRenderer();

    private Game createFourPlayerGame() {
        Player player1 = new Player("Ariel");
        Player player2 = new Player("Beatrice");
        Player player3 = new Player("Carla");
        Player player4 = new Player("Dilan");
        LinkedList<Player> playerList = new LinkedList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        Game game = new Game(playerList);
        return game;
    }

    // needs to be visual checked. Question: how can i check console output?
    @Test
    public void basicBoardTest() {

        boardRenderer.displayBoard(createFourPlayerGame());
    }

    @Test
    public void winningMessageTest() throws InterruptedException {
        Player p1 = new Player("John Wick");
        boardRenderer.winnerMessage(p1);
    }



}

