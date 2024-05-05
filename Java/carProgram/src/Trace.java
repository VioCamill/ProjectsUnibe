import java.util.ArrayList;

/**
 * Traces the path of the car.
 *
 * Takes the path from the car and returns a boolean[][] where all the places visited are true.
 * Also generates a special screen, if the user crashes the car.
 */


public class Trace {


    /**
     * Takes the arraylist and turns it into coordinates which are in order with the matrix array
     * Origin 0/0 is array[99][0]
     * @param path ArrayList<int[]>
     * @return ArrayList of integer arrays.
     */
    private void translateCoordinate (ArrayList<int[]> path) {
        assert path.size()>0;
    for (int i = 0; i<path.size(); i++) {
        path.get(i)[1] = 99 - path.get(i)[1]; //turns the y cooardinate into the right input for the board

        }
    }

    /**
     * Traces all the squares between the moves.
     * @param board
     * @param positionBefore int[] where 0 is the x and y the y postion
     * @param positionAfter int[] where 0 is the x and y the y postion
     */

    private void traceAll  (boolean[][] board, int[] positionBefore, int[] positionAfter)
    throws ArrayIndexOutOfBoundsException {

    board[positionBefore[0]][positionBefore[1]] = true;
    int steps = 0;
    if (positionBefore[0]>positionAfter[0]) {
        while (positionBefore[0]>positionAfter[0]) {
            board[positionBefore[0]][positionBefore[1]] = true;
            positionBefore[0]--;
        }}

    if (positionBefore[0]<positionAfter[0]) {
        while (positionBefore[0]<positionAfter[0]) {

                board[positionBefore[0]][positionBefore[1]] = true;
            positionBefore[0]++;
            }}


    if (positionBefore[1]>positionAfter[1]) {
        steps = 0;
            while (positionBefore[1]-steps>positionAfter[1]) {
                board[positionBefore[0]][positionBefore[1]-steps] = true;
                steps++;
            }}

    if (positionBefore[1]<positionAfter[1]) {
        steps = 0;
                while (positionBefore[1]+steps<positionAfter[1]) {
                    board[positionBefore[0]][positionBefore[1]+steps] = true;
                    steps++;
                }}
    assert positionAfter[0]<100;
    assert positionAfter[1]<100;
    }


    /**
     * Take the car and it's path and marks all places visited true
     *
     *
     * @param c
     * @return board, with true value where the car has been
     */
    public boolean[][] retrace (Car c) {
        boolean[][] board =  new boolean[BoardMaker.SIZE][BoardMaker.SIZE];
        ArrayList<int[]> path = c.clonePath();
        translateCoordinate(path);
        assert path.size()>0;

        //Marks only the spots where the car has been. Needed for teleport
        for (int p = 0; p<path.size(); p++) {
            int[] k = path.get(p);
            trace(board, k);
        }
        //iterates true the path
        if (path.size()>1) {
            for (int p = 1; p<path.size(); p++) {
                int[] positionBefore = path.get(p-1);
                int[] positionAfter = path.get(p);
                traceAll(board, positionBefore, positionAfter);
        }
        }
        //starting point should always be true;
        assert board[49][49] = true;
    return board;

    }

    /**
     *
     * @param board
     * @param position
     * @throws ArrayIndexOutOfBoundsException
     */

    private void trace (boolean[][] board, int[] position) throws ArrayIndexOutOfBoundsException {
        board[position[0]][position[1]] = true;
    }

    /**
     * Is used with the out of bound exception
     *
     * @return boolean board with a frawning face
     */

    public boolean[][] crash () {
        boolean[][] board = new boolean[BoardMaker.SIZE][BoardMaker.SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = true;
                }
            }
        // Paints a smileyface on the crash screen
        board[30][20]= false;
        board[30][21]= false;
        board[30][22]= false;
        board[31][20]= false;
        board[31][21]= false;
        board[31][22]= false;
        board[70][20]= false;
        board[70][21]= false;
        board[70][22]= false;
        board[69][20]= false;
        board[69][21]= false;
        board[69][22]= false;
        board[48][32]= false;
        board[49][32]= false;
        board[50][32]= false;
        board[51][32]= false;
        board[47][33]= false;
        board[52][33]= false;
        board[46][34]= false;
        board[53][34]= false;
        board[45][35]= false;
        board[54][35]= false;
        board[44][36]= false;
        board[55][36]= false;
        board[43][37]= false;
        board[56][37]= false;
        board[42][38]= false;
        board[57][38]= false;
        board[41][39]= false;
        board[58][39]= false;

    return board;
    }



}
