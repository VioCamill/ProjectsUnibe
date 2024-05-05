package classTest;

import ludo.Game;
import ludo.Movement;
import ludo.Piece;
import ludo.Player;
import ludo.square.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovementTest {


    /**
     * Only move piece 1, because Piece 2 still in the house
     *
     */
    @Test
    public void whichMovesTest_MovePiece1_1() {
        Player testPlayerOwn = new Player("Han Solo");
        Player testPlayerRival = new Player("Boba Fett");
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);
        Piece[] pieces = {testPiece1, testPiece2};

        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);

        //needed for valid position to run without error
        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);
        when(current.occupiedBy()).thenReturn(testPiece1);
        when(testPiece1.getPlayer()).thenReturn(testPlayerOwn);
        when(next.occupiedBy()).thenReturn(testPiece2);
        when(testPiece1.getPlayer()).thenReturn(testPlayerRival);
        when(next.isOccupied()).thenReturn(true);
        when(current.getPosition()).thenReturn(1);

        //validPositon = true
        when(testPiece1.getSquare()).thenReturn(current);
        when(testPiece2.getSquare()).thenReturn(current);
        when(game.findSquare(1, 6)).thenReturn(next);
        //still on HomeSquare
        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(true); //TCP
        //on Goal Square
        when(testPiece1.onGoalSquare()).thenReturn(false);
        when(testPiece2.onGoalSquare()).thenReturn(false);

        assertEquals(0, testMovement.whichMoves(pieces, 6));

    }

    /**
     * Only move piece 1, because Piece 2 is already in goal
     *
     */
    @Test
    public void whichMovesTest_MovePiece1_2() {
        Player testPlayerOwn = new Player("Han Solo");
        Player testPlayerRival = new Player("Boba Fett");
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);
        Piece[] pieces = {testPiece1, testPiece2};

        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);

        //needed for valid position to run without error
        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);
        when(current.occupiedBy()).thenReturn(testPiece1);
        when(testPiece1.getPlayer()).thenReturn(testPlayerOwn);
        when(next.occupiedBy()).thenReturn(testPiece2);
        when(testPiece1.getPlayer()).thenReturn(testPlayerRival);
        when(next.isOccupied()).thenReturn(true);
        when(current.getPosition()).thenReturn(1);

        //validPositon = true
        when(testPiece1.getSquare()).thenReturn(current);
        when(testPiece2.getSquare()).thenReturn(current);
        when(game.findSquare(1, 6)).thenReturn(next);
        //still on HomeSquare
        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(false);
        //on Goal Square
        when(testPiece1.onGoalSquare()).thenReturn(false);
        when(testPiece2.onGoalSquare()).thenReturn(true); //TCP

        assertEquals(0, testMovement.whichMoves(pieces, 6));
    }

    /**
     *
     * @assert should return 2, cause both can move
     */
    @Test
    public void whichMoveTest_MoveBothPiece() {
        Player testPlayerOwn = new Player("Han Solo");
        Player testPlayerRival = new Player("Boba Fett");
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);
        Piece[] pieces = {testPiece1, testPiece2};

        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);

        //needed for valid position to run without error
        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);
        when(current.occupiedBy()).thenReturn(testPiece1);
        when(testPiece1.getPlayer()).thenReturn(testPlayerOwn);
        when(next.occupiedBy()).thenReturn(testPiece2);
        when(testPiece1.getPlayer()).thenReturn(testPlayerRival);
        when(next.isOccupied()).thenReturn(true);
        when(current.getPosition()).thenReturn(1);

        //validPositon = true
        when(testPiece1.getSquare()).thenReturn(current);
        when(testPiece2.getSquare()).thenReturn(current);
        when(game.findSquare(1, 6)).thenReturn(next);
        //still on HomeSquare
        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(false);
        //on Goal Square
        when(testPiece1.onGoalSquare()).thenReturn(false);
        when(testPiece2.onGoalSquare()).thenReturn(false);

        assertEquals(2, testMovement.whichMoves(pieces, 6)); //TCP
    }


    /**
     *
     * @assert should return -1, because none can move
     */
    @Test
    public void whichMoveTest_MoveNoPiece() {
        Player testPlayerOwn = new Player("Han Solo");
        Player testPlayerRival = new Player("Boba Fett");
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);
        Piece[] pieces = {testPiece1, testPiece2};

        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);

        //needed for valid position to run without error
        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);
        when(current.occupiedBy()).thenReturn(testPiece1);
        when(testPiece1.getPlayer()).thenReturn(testPlayerOwn);
        when(next.occupiedBy()).thenReturn(testPiece2);
        when(testPiece1.getPlayer()).thenReturn(testPlayerRival);
        when(next.isOccupied()).thenReturn(true);
        when(current.getPosition()).thenReturn(1);

        //validPositon = true
        when(testPiece1.getSquare()).thenReturn(current);
        when(testPiece2.getSquare()).thenReturn(current);
        when(game.findSquare(1, 6)).thenReturn(next);
        //still on HomeSquare
        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(true); //TCP
        //on Goal Square
        when(testPiece1.onGoalSquare()).thenReturn(true); //TCP
        when(testPiece2.onGoalSquare()).thenReturn(false);

        assertEquals(-1, testMovement.whichMoves(pieces, 6));
    }


    @Test
    public void isValidMoveTest_false() { //next occupied by same player
        Player testPlayer = new Player("Han Solo");
        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);

        when(current.occupiedBy()).thenReturn(testPiece1);
        when(next.occupiedBy()).thenReturn(testPiece2);

        when(testPiece1.getPlayer()).thenReturn(testPlayer);
        when(testPiece2.getPlayer()).thenReturn(testPlayer);

        when(next.isOccupied()).thenReturn(true);

        assertFalse(testMovement.isValidMove(current, next));
    }

    @Test
    public void isValidMoveTest_true() {
        Player testPlayerOwn = new Player("Han Solo");
        Player testPlayerRival = new Player("Boba Fett");
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);
        Game game = mock(Game.class);
        Movement testMovement = new Movement(game);

        Square current = mock(StandardSquare.class);
        Square next = mock(StandardSquare.class);

        when(current.occupiedBy()).thenReturn(testPiece1);
        when(testPiece1.getPlayer()).thenReturn(testPlayerOwn);

        when(next.occupiedBy()).thenReturn(testPiece2);
        when(testPiece1.getPlayer()).thenReturn(testPlayerRival);

        when(next.isOccupied()).thenReturn(true);

        assertTrue(testMovement.isValidMove(current, next));
    }


    /**
     * rolled is equal to distance1, return should be true
     *
     * @assert distance1 == rolled
     */
    @Test
    public void canEnterGoalTest_trueDistance1() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);
        Piece testPiece = mock(Piece.class);
        Player testPlayer = mock(Player.class);
        GoalRunSquare testGoalRunSquare = mock(GoalRunSquare.class);

        ArrayList<GoalSquare> goalSquares = new ArrayList<>();
        GoalSquare testGoalSquare = mock(GoalSquare.class);
        goalSquares.add(0, new GoalSquare(game, 1, testPlayer));
        goalSquares.add(1, testGoalSquare);

        when(testPiece.getSquare()).thenReturn(testGoalRunSquare);
        when(testGoalRunSquare.isGoalRunSquare(testPiece)).thenReturn(true);

        when(testGoalRunSquare.getDistance1()).thenReturn(5); //TCP
        when(testGoalRunSquare.getDistance2()).thenReturn(6);

        when(testPlayer.getGoalSquares()).thenReturn(goalSquares);

        when(testGoalSquare.isOccupied()).thenReturn(false);


        assertTrue(testMove.canEnterGoal(testPiece, 5, testPlayer));
    }


    /**
     * rolled is equal to distance2, return should be true
     *
     * @assert distance2 == rolled
     */
    @Test
    public void canEnterGoalTest_trueDistance2() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);
        Piece testPiece = mock(Piece.class);
        Player testPlayer = mock(Player.class);
        GoalRunSquare testGoalRunSquare = mock(GoalRunSquare.class);

        ArrayList<GoalSquare> goalSquares = new ArrayList<>();
        GoalSquare testGoalSquare = mock(GoalSquare.class);
        goalSquares.add(0, new GoalSquare(game, 1, testPlayer));
        goalSquares.add(1, testGoalSquare);

        when(testPiece.getSquare()).thenReturn(testGoalRunSquare);
        when(testGoalRunSquare.isGoalRunSquare(testPiece)).thenReturn(true);

        when(testGoalRunSquare.getDistance1()).thenReturn(5);
        when(testGoalRunSquare.getDistance2()).thenReturn(6); //TCP

        when(testPlayer.getGoalSquares()).thenReturn(goalSquares);

        when(testGoalSquare.isOccupied()).thenReturn(false);


        assertTrue(testMove.canEnterGoal(testPiece, 6, testPlayer));
    }


    /**
     * rolled is different to distance1 & 2, return should be false
     *
     * @assert rolled distance is different to the distances on the goalRunSquare
     */
    @Test
    public void canEnterGoalTest_falseWrongDistance() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);
        Piece testPiece = mock(Piece.class);
        Player testPlayer = mock(Player.class);
        GoalRunSquare testGoalRunSquare = mock(GoalRunSquare.class);

        ArrayList<GoalSquare> goalSquares = new ArrayList<>();
        GoalSquare testGoalSquare = mock(GoalSquare.class);
        goalSquares.add(0, new GoalSquare(game, 1, testPlayer));
        goalSquares.add(1, testGoalSquare);

        when(testPiece.getSquare()).thenReturn(testGoalRunSquare);
        when(testGoalRunSquare.isGoalRunSquare(testPiece)).thenReturn(true);

        when(testGoalRunSquare.getDistance1()).thenReturn(5);
        when(testGoalRunSquare.getDistance2()).thenReturn(6);

        when(testPlayer.getGoalSquares()).thenReturn(goalSquares);

        when(testGoalSquare.isOccupied()).thenReturn(false);


        assertFalse(testMove.canEnterGoal(testPiece, 4, testPlayer)); //TCP
    }


    /**
     * Target GoalSquare is occupied, should return false
     *
     * @assert target GoalSquare is occupied
     */
    @Test
    public void canEnterGoalTest_falseIsOccupied() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);
        Piece testPiece = mock(Piece.class);
        Player testPlayer = mock(Player.class);
        GoalRunSquare testGoalRunSquare = mock(GoalRunSquare.class);

        ArrayList<GoalSquare> goalSquares = new ArrayList<>();
        GoalSquare testGoalSquare = mock(GoalSquare.class);
        goalSquares.add(0, new GoalSquare(game, 1, testPlayer));
        goalSquares.add(1, testGoalSquare);

        when(testPiece.getSquare()).thenReturn(testGoalRunSquare);
        when(testGoalRunSquare.isGoalRunSquare(testPiece)).thenReturn(true);

        when(testGoalRunSquare.getDistance1()).thenReturn(5);
        when(testGoalRunSquare.getDistance2()).thenReturn(6);
        when(testPlayer.getGoalSquares()).thenReturn(goalSquares);
        when(testGoalSquare.isOccupied()).thenReturn(true); //TCP

        assertFalse(testMove.canEnterGoal(testPiece, 6, testPlayer));

    }


    /**
     * piece1 is still on homeSquare, it should be returned
     *
     * @assert if piece1 is returned
     */
    @Test
    public void whichPieceEnters_Piece1() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Piece[] pieces = {testPiece1, testPiece2};

        when(testPiece1.stillOnHomeSquare()).thenReturn(true); //TCP

        assertEquals(testPiece1, testMove.whichPieceEnters(pieces));
    }

    /**
     * piece1 has already left the homeSquare, it should return piece2
     *
     * @assert if piece2 is returned
     */
    @Test
    public void whichPieceEnters_Piece2() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Piece[] pieces = {testPiece1, testPiece2};

        when(testPiece1.stillOnHomeSquare()).thenReturn(false); //TCP

        assertEquals(testPiece2, testMove.whichPieceEnters(pieces));
    }

    /**
     * test if a piece can enter the game, should return true, as piece1 is still on homeSquare and startSquare is
     * unoccupied
     */
    @Test
    public void canEnterGameTest_PieceTrue() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        StartSquare testStartSquare = mock(StartSquare.class);

        Player testPlayer = mock(Player.class);
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Piece[] pieces = {testPiece1, testPiece2};

        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(true); //TCP

        when(game.findStartSquare(testPlayer)).thenReturn(testStartSquare);
        when(testStartSquare.isOccupied()).thenReturn(false);

        assertTrue(testMove.canEnterGame(testPlayer, pieces, 6));
    }


    /**
     *test should return false, as no piece is on its homeSquare
     */
    @Test
    public void canEnterGameTest_PieceFalse() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        StartSquare testStartSquare = mock(StartSquare.class);

        Player testPlayer = mock(Player.class);
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Piece[] pieces = {testPiece1, testPiece2};

        when(testPiece1.stillOnHomeSquare()).thenReturn(false);
        when(testPiece2.stillOnHomeSquare()).thenReturn(false);

        when(game.findStartSquare(testPlayer)).thenReturn(testStartSquare);
        when(testStartSquare.isOccupied()).thenReturn(false);

        assertFalse(testMove.canEnterGame(testPlayer, pieces, 6));
    }


    /**
     *should return false, as the startSquare is Occupied
     */
    @Test
    public void canEnterGameTest_StartIsOccupied() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        StartSquare testStartSquare = mock(StartSquare.class);

        Player testPlayer = mock(Player.class);
        Piece testPiece1 = mock(Piece.class);
        Piece testPiece2 = mock(Piece.class);

        Piece[] pieces = {testPiece1, testPiece2};

        when(testPiece1.stillOnHomeSquare()).thenReturn(true);
        when(testPiece2.stillOnHomeSquare()).thenReturn(false);

        when(game.findStartSquare(testPlayer)).thenReturn(testStartSquare);
        when(testStartSquare.isOccupied()).thenReturn(true);

        assertFalse(testMove.canEnterGame(testPlayer, pieces, 6));
    }

    /**
     * Test if a piece leaves and enters the current and the next correctly respectively
     */
    @Test
    public void movePieceTest() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);

        Square current = new StandardSquare(game, 1);
        Square next = new StandardSquare(game, 2);

        Piece testPiece = mock(Piece.class);
        current.enter(testPiece);

        testMove.movePiece(current, next, testPiece);

        assertEquals(testPiece, next.occupiedBy());
        assertFalse(current.isOccupied());

    }

    /**
     * Test the functionality of moveOnGoalSquareTest
     */
    @Test
    public void moveOnGoalSquareTest() {
        Game game = mock(Game.class);
        Movement testMove = new Movement(game);
        Piece testPiece = mock(Piece.class);
        Player testPlayer = mock(Player.class);
        GoalRunSquare goalRunSquare = mock(GoalRunSquare.class);
        GoalSquare goalSquare1 = new GoalSquare(game, 0, testPlayer);
        GoalSquare goalSquare2 = new GoalSquare(game, 1, testPlayer);
        ArrayList<GoalSquare> goalSquaresList = new ArrayList<>();
        goalSquaresList.add(0, goalSquare1);
        goalSquaresList.add(1, goalSquare2);

        when(testPiece.getSquare()).thenReturn(goalRunSquare);
        when(goalRunSquare.isGoalRunSquare(testPiece)).thenReturn(true);

        when(goalRunSquare.getDistance1()).thenReturn(5);

        when(testPiece.getPlayer()).thenReturn(testPlayer);
        when(testPlayer.getGoalSquares()).thenReturn(goalSquaresList);


        testMove.moveOnGoalSquare(5, testPiece, "1");

        assertEquals(goalSquare1.occupiedBy(), testPiece);
        assertFalse(goalRunSquare.isOccupied());

    }


    /**
     * a piece that would overshoot cannot move
     */
    @Test
    public void pieceCanMoveTest_isGoalRunSquareFalse() {
        Game game = mock(Game.class);
        Movement movement = new Movement(game);

        Player player = mock(Player.class);
        Piece piece = mock(Piece.class);


        when(piece.getPlayer()).thenReturn(player);
        when(piece.getPlayer()).thenReturn(player);
        GoalRunSquare goalRunSquare = mock(GoalRunSquare.class);

        when(piece.getSquare()).thenReturn(goalRunSquare);
        when(goalRunSquare.isGoalRunSquare(piece)).thenReturn(true);

        when(goalRunSquare.getDistance1()).thenReturn(4);
        when(goalRunSquare.getDistance2()).thenReturn(5);


        assertFalse(movement.pieceCanMove(piece, 6));

    }


}
