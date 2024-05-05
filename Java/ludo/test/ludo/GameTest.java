package ludo;
import ludo.square.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

	private Game game;
	private Piece[] piecesA, piecesB;
	Player playerA, playerB;
	Queue<Player> playerList;

	@Test
	public void randomGames(){
		for(int i = 0; i < 100; i++){
			Game game = new Game();
			game.play();
		}
	}

	public void initGame(){
		game = new Game();
		playerList = game.getPlayers();
		playerA = playerList.peek(); //player with pieces A,a
		piecesA = playerA.getPieces();
		game.rotatePlayerQueue();
		playerB = playerList.peek(); //player with pieces B,b
		piecesB = playerB.getPieces();
	}

	@Test
	public void testInitPlayer(){
		initGame();
		assertTrue(game.getPlayers().size() == 4);
		for(Player p : playerList){
			assertTrue(p.getPieces().length == 2);
		}
	}
	@Test
	public void testHomeSquareInit(){
		initGame();
		for(Player p: playerList){
			Piece[] pieces = p.getPieces();
			HomeSquare homeSquare1 = pieces[0].getHomeSquare();
			HomeSquare homeSquare2 = pieces[1].getHomeSquare();
			assertEquals(pieces[0], homeSquare1.occupiedBy());
			assertEquals(pieces[1], homeSquare2.occupiedBy());
		}
	}
	@Test
	public void testStartPosition(){
		initGame();
		StartSquare startSquareA = (StartSquare) game.findStartSquare(playerA);
		assertEquals(game.getSquare(0), startSquareA, "startPosition of PlayerA is 0");
		StartSquare startSquareB = (StartSquare) game.findStartSquare(playerB);
		assertEquals(game.getSquare(10), startSquareB, "startPosition of PlayerB is 10");
	}

	@Test
	public void testGoalRunSquareInit(){
		initGame();
		for(int i = 35; i < 40; i++) {
			game.setPiece(piecesA[0], i); // on position 35 - 39 there are GoalRunSquares for PlayerA
			Square square = game.getSquare(i);
			assertTrue(square.isGoalRunSquare(piecesA[0]));
		}
	}
	@Test
	public void testInitGoalSquare(){
		initGame();
		Square square = game.getSquare(5);
		assertTrue(square.isGoalRunSquare(piecesB[0]));
	}

	@Test
	public void testVictory(){
		initWinner();
		assertTrue(game.checkVictory(playerA));
		assertEquals(playerA, game.getWinner());
	}
	private void initWinner(){
		initGame();
		game.setPieceToGoal(piecesA[0],1);
		game.setPieceToGoal(piecesA[1], 2);
	}
	@Test
	public void testGameNotOver(){
		initPieces();
		assertTrue(game.notOver());
	}

	@Test
	public void testDistanceToGoalSquare(){
		initPieces();
		GoalRunSquare goalRunSquare = (GoalRunSquare) piecesB[0].getSquare();
		assertEquals(5, goalRunSquare.getDistance1(), "Distance to the first GoalSquare is 5");
		assertEquals(6, goalRunSquare.getDistance2(), "Distance to the second GoalSquare is 6");
	}

	private void initPieces(){
		initGame();
		game.setPiece(piecesB[0], 5); //on position 5 is a GoalRunSquare from playerB
		game.setPieceToGoal(piecesA[1], 2);
	}

}