import java.util.ArrayList;

/**
 * should know: direction it is facing
 * actual position, put them in int[3] array such that int[0] = x position, int[1] = y position and int[2]
 * = direction its facing.
 * keep track of position it was, put them in boolean[][] trace, this can be get by BoardMaker at the
 * end of the Method makeBoardFrom
 *
 * Contract with BoardMaker: Car knows position and previous position. sets all points true in its lokal boolean[][]
 * so it makes the map of its own moves
 * BoardMaker is responsible, that it only changes position of car, if the new position is still in its boarder
 * so BoardMaker has to deal with outOfBoundExceptions, not Car
 *
 * Contract with Direction: Car can only have this four directions
 *
 * @author Tim Ambühl
 */

public class Car {



    /**
     * holds the position and direction of the car
     *
     * Holds x,y and direction of the car.
     *
     * Direction is stored as int value. TODO which number represents which direction
     *
     * north = 0
     * west = 1
     * south  = 2
     * east = 3
     */

    private ArrayList<int[]> path = new ArrayList<int[]>();


//creates an object car and place it in the middle of the board. car holds the path

    public Car() {
        assert path.size()==0;
        int []position = new int[3];
        position[0] = 49;
        position[1] = 49;
        position[2] = 0;
        this.path.add(position);
        invariant();
        assert path.size()==1;

    }


    //Class Invariant , added -1
    protected void invariant() {
        int[] pos = this.path.get(this.path.size()-1);
        assert(pos[0] <= 99 && pos[0] >= 0 &&
                pos[1] <= 99 && pos[1] >= 0
                && pos[2] <= 3 && pos[2] >= 0);
    }



    //returns copy of current car position
    public int[] pos() {
        ArrayList<int[]> path = this.path;
        int[] copy = new int[3];
        for (int i = 0; i<3; i++) {
            copy[i] = this.path.get(this.path.size()-1)[i];
        }

        return copy;
    }

    //returns current direction
    public int getDirection() {
        return (this.path.get(this.path.size()- 1)[2]);

    }

    /**
     * Clones the path of the car, to prevent aliasing.
     * @return ArrayList of int arrays
     */

    public ArrayList<int[]> clonePath() {
        ArrayList<int[]> temp = new ArrayList<int[]>();

        for (int i = 0; i<this.path.size(); i++) {
            int[] copy = new int[3];
            for (int k = 0; k<3; k++) {
                copy[k] = this.path.get(i)[k];
            }
            temp.add(copy);
        }
       return temp;
    }


    /**
     * moving the Car on the Board according to the commands given by the Input Array
     *
     *command[0] :
     *
     * command code action
     *
     * left = 0
     * right = 1
     * forward = 2
     * backwards = 3
     * drive  = 4
     *
     * command[1] :
     *
     *distance for forward and backward
     *
     * command[2] & command[3] :
     *
     * coordinates for drive x,y
     *
     * @param commands command Array, holding the move Commands
     */

    public void addPath (ArrayList<int[]> list) {
        for (int i = 0; i< list.size(); i++ ) {
            addMove(list.get(i));
        }


    }



    public void addMove (int[] commands) {
        invariant();

        switch (commands[0]) {

            case 0:
                this.path.add(turnLeft());
                break;

            case 1:
                this.path.add(turnRight());
                break;

            case 2:
                this.path.add(moveForward(commands[1]));
                break;

            case 3:
                this.path.add(moveBackward(commands[1]));
                break;

            case 4:
                this.path.add(driveXY(commands[2], commands[3]));
                break;

        }


    }


    //turns the car to the left
    public int[] turnLeft() {
        int temp[] = path.get(path.size()-1);
        int[] move = new int[3];
        move[0] = temp[0];
        move[1] = temp[1];
        move[2] = (temp[2]+3)%4;

        assert(move[2] >= 0 && move[2] <= 4);
        return move;

    }

    //turns the car to the right
    public  int[] turnRight() {
        int temp[] = path.get(path.size()-1);
        int[] move = new int[3];
        move[0] = temp[0];
        move[1] = temp[1];
        move[2] = (temp[2]+1)%4;

        assert(move[2] >= 0 && move[2] <= 4);

        return move;

    }

    //moves the car forward by i in the current direction
    //removed assert(i <= 4 && i >= 0); because it can be negative, because moveBackward uses this methode
    public int[] moveForward (int i) {
        int direction;
        direction = getDirection();
        int[] position;
        position = pos();

        switch (direction) {
            case 0: //north
                position[1] += i;

                assert(position[0] >= 0 && position[0] <= 99);
                return position;

            case 1: //east
                position[0] += i;

                assert(position[1] >= 0 && position[1] <= 99);
                return position;

            case 2: //south
                position[1] -= i;
                assert(position[0] >= 0 && position[0] <= 99);

                return position;

            case 3: //west
                position[0] -= i;

                assert(position[1] >= 0 && position[1] <= 99);
                return position;
        }
        return position;
    }


    //reverse function of move Forward, just moves backwards
    public int[] moveBackward (int i) {
        return (moveForward(-i));

    }



    /** Drives the car to the position x y
     *
     * @param x  coordinates
     * @param y  coordinates
     * @return int Array with position x, y and direction
     */
    public int[] driveXY (int x, int y) {
        assert(x <= 99 && x >= 0 && y <= 99 && y >= 0);
        int[] move = pos();
        move[0] = x;
        move[1] = y;

        assert(move[0] >= 0 && move[0] <= 99 && move[1] >= 0 && move[1] <= 99);
        return move;


    }


}
