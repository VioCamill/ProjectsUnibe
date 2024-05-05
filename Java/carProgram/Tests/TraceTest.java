import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TraceTest {
    Trace trace = new Trace();
    boolean[][] board = blankBoard();
    Car car = new Car();

    int[] moveLeft = { 0,0,0,0};
    int[] moveRight = { 1,0,0,0};
    int[] moveForward4 = { 2,4,0,0};
    int[] moveBackward3 = { 3,3,0,0};

    int[] drive33_33 = { 33,33,0,0};




 @BeforeEach
    public void clear() { this.board = blankBoard();}





   private boolean[][] blankBoard () {
        boolean[][] board = new boolean[BoardMaker.SIZE][BoardMaker.SIZE];
        return board;
    }


    //Testing as a graphic
    private void printBoard (boolean[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j]) {
                    System.out.print("\u25A0 "); // black square
                } else {
                    System.out.print("\u25A1 "); // white square
                }
    }System.out.println();
        }}

@Test
    public void forwardTest () {
       this.board = blankBoard();
       Car car = new Car();
        printBoard(this.board);
       int[] add4 = {2,4,0,0};
       car.addMove(add4);
   board = trace.retrace(car);
        printBoard(this.board);
   }
   @Test
    public void severalMoves () {
        Car car = new Car();
        printBoard(this.board);
        car.addMove(moveForward4);
        car.addMove(moveLeft);
        car.addMove(moveForward4);
       car.addMove(moveRight);
       car.addMove(moveBackward3);
        board = trace.retrace(car);
        printBoard(this.board);

    }

    @Test
    public void driveXY () {
        Car car = new Car();
        printBoard(this.board);
        car.addMove(drive33_33);
        board = trace.retrace(car);
        printBoard(this.board);

    }




@Test
        public void basicBoardTest () {
                printBoard(this.board);
            }



            }





