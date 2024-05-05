package snakes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snakes.squares.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {


    @Test
    public void testPosition() {
        Random rand = new Random();

        for (int i = 0; i<100; i++) {
            Queue<Player> players = new LinkedList<>();
            Player james = new Player("James");
            Player bond = new Player("Bond");
            players.add(james);
            players.add(bond);
            Game game = new Game(15, players, 6);
            int randomRoll1 = rand.nextInt(6)+1;
            int randomRoll2 = rand.nextInt(6)+1;
            if (randomRoll2==randomRoll1) randomRoll2 = (randomRoll1 + 1)%5 + 1;
            assertEquals(1, james.position());
            game.movePlayer(randomRoll1);
            assertEquals(randomRoll1 + 1, james.position());
            game.movePlayer(randomRoll2);
            assertEquals(randomRoll2+1, bond.position());
        }
        }
}






