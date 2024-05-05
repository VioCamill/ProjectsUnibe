package ludo.render;

import ludo.Player;
import ludo.square.GoalSquare;
import ludo.square.HomeSquare;
import ludo.square.Square;

import java.util.List;


/**
 * Takes a BoardState and prints out the corresponding board in the console.
 *
 *  precondition: is called with a valid game state
 *
 *  postcondition: the current board is printed out in the console
 *
 *
 */
public class BoardRenderer {


    private List<Square> moveSquares;
    private List<GoalSquare> goalSquares;
    private List<HomeSquare> homeSquares;

    private StringBuffer boardAsStringBuffer = new StringBuffer();

    private BoardToStringBuffer boardToStringBuffer = new BoardToStringBuffer();


    /**
     * creates a basic board, with the unchangeable characters
     *
     */

    public BoardRenderer() {
    boardAsStringBuffer = boardToStringBuffer.createBasicBoard();
    }

    /**
     * Prints out a board with the current game state of the game.
     *
     * @param game
     */

    public void displayBoard(ludo.Game game) {
        moveSquares = game.getMoveSquares();
        goalSquares = game.getGoalSquares();
        homeSquares = game.getHomeSquares();
        updateBaord();
        assert (moveSquares.size() == 40);
        System.out.print(getBoardAsStringBuffer());
        assert (goalSquares.size()==homeSquares.size());
        assert (true == assert8PiecesOnTheBoard(boardAsStringBuffer));
        //Layout fix
        System.out.println("");
    }

    public StringBuffer getBoardAsStringBuffer() {
        return this.boardAsStringBuffer;
    }

    //changes the StringBuffer to the current game state. Iterates thru all the different square array
    private void updateBaord(){
        for (int i = 0; i<moveSquares.size(); i++){
            boardToStringBuffer.updateMoveSquare(this.moveSquares.get(i),this.boardAsStringBuffer);
        }
        for (int i =0; i<goalSquares.size(); i++){
            if (i<homeSquares.size()) boardToStringBuffer.updateHomeSquare(this.homeSquares.get(i),this.boardAsStringBuffer);
            boardToStringBuffer.updateGoalSquare(this.goalSquares.get(i),this.boardAsStringBuffer);
        }
        assert true == assert8PiecesOnTheBoard(boardAsStringBuffer);

    }

    //maybe needed in the futur
    private void setBoardAsStringBuffer (StringBuffer stringBuffer)    {
        this.boardAsStringBuffer = stringBuffer;
    }


    //prints out a message diplaying the winning player.
    public String winnerMessage (Player p)  {
        String winningPlayer = p.getName();
        String winningMessage = "<<< " + p.getName() + " is the winner >>>  " + "\n";
        System.out.println(winningMessage);
        return p.getName();
        }

    private boolean assert8PiecesOnTheBoard (StringBuffer board) {
        //createBasicBoard was checked and mapped with the help of the debugger.
        StringBuffer check = boardToStringBuffer.createBasicBoard();
        //counts the pieces on the board
        int count = 0;
        // whenever a substring doesnt match with the basic board a pieces is detected and count increased.
        // 131 is length of the StringBuffer with content
        for (int i = 0; i<=131; i++) {
            if (board.charAt(i)=='A') count++;
            if (board.charAt(i)=='a') count++;
            if (board.charAt(i)=='B') count++;
            if (board.charAt(i)=='b') count++;
            if (board.charAt(i)=='C') count++;
            if (board.charAt(i)=='c') count++;
            if (board.charAt(i)=='D') count++;
            if (board.charAt(i)=='d') count++;
        }

        return count == 8;
    }

}








