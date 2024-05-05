import java.util.ArrayList;

/**
 * calls all the methods that are needed to return a board with the trace
 * from the car
 */
public class BoardMaker {
    protected final static int SIZE = 100;
    protected boolean[][] board;
    private boolean invariant() {
        return (board.length == SIZE && board[0].length == SIZE);
    }

    /**
     * Parse the given car program and evaluate it. Render the trail as
     * described in the problem description and return a SIZExSIZE board
     * corresponding to the evaluated path.
     *
     * @param carProgram input program according to specification. may also contain invalid text!
     * @return SIZExSIZE boolean board, where true values denote "red trail".
     */
    public boolean[][] makeBoardFrom(String carProgram) throws ParserException {
        Trace trace = new Trace();
        boolean[][] board = initialBoard();
        Car car = new Car();
        try {
            CommandParser commandParser = new CommandParser();
            ArrayList<int[]> carMoves = commandParser.makeCarCommandFrom(carProgram);
            car.addPath(carMoves);
            board = trace.retrace(car);
        } catch (ArrayIndexOutOfBoundsException a) {
            board = trace.crash();
        }
        return board;
    }

    /**
     * Create a new board and return it.
     *
     * @return board, must be of size SIZExSIZE.
     * Adds Car to somewhat the middle
     */
    public boolean[][] initialBoard() {
        assert (0 > 1);
        this.board = new boolean[SIZE][SIZE];
        this.board[49][49] = true;
        assert invariant();
        return this.board;
    }
}
