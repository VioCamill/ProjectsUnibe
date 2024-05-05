package ludo.render;

import ludo.square.GoalSquare;
import ludo.square.HomeSquare;
import ludo.square.Square;
import ludo.square.StartSquare;

/**
 * refactored BoardRenderer into two classes, to make futur changes more simple and to avoid a godclass
 *
 *
 * Handles the StringBuffer which represents the board.
 *
 *
 */


public class BoardToStringBuffer {




    //creates a basic StringBuffer of the board
     StringBuffer createBasicBoard () {
        StringBuffer basicBoard = new StringBuffer();
        //adds all the #
        for (int i = 0; i<=120; i++) {
            if (i%11==0&&i>0) basicBoard.append("\n");
            basicBoard.append("#");
        }

        basicBoard.append("\n");

        // creates the spaces in the playerpouch
        basicBoard.replace(13,15,"  ");
        basicBoard.replace(25,27,"  ");
        basicBoard.replace(20,22,"  ");
        basicBoard.replace(32,34,"  ");
        basicBoard.replace(104,106,"  ");
        basicBoard.replace(116,118,"  ");
        basicBoard.replace(97,99,"  ");
        basicBoard.replace(109,111,"  ");

        return basicBoard;
    }

    // calls the square.toString() methode and updates the corresponding place in the StringBuffer
    protected void updateMoveSquare(Square square, StringBuffer stringBuffer) {

        int i = square.getPosition();
        switch (i) {
            case 0,1,2,3,4: i += 48;
                break;
            case 5,6,7,8: i = 40-12*(i-5);
                break;
            case 9: i = 5;
                break;
            case 10,11,12,13,14: i = 6 + 12*(i-10);
                break;
            case 15,16,17,18: i += 40;
                break;
            case 19: i = 70;
                break;
            case 20,21,22,23,24: i = 102-i;
                break;
            case 25,26,27,28: i = 90 - 12*(25-i);
                break;
            case 29: i = 125;
                break;
            case 30,31,32,33,34: i = 124 - 12*(i-30);
                break;
            case 35,36,37,38: i = 75 + (35-i);
                break;
            case 39: i = 60;
                break;

        }
        stringBuffer.replace(i,i+1, square.toString());

    }

    protected void updateStartSquare (StartSquare square, StringBuffer stringBuffer) {
         int index = square.getPosition();
         switch (index) {
        case 0: index = 48;
        break;
        case 10: index = 6;
        break;
        case 20: index = 82;
        break;
        case 30: index = 94;
        }
        stringBuffer.replace(index,index+1, square.toString());

    }


    protected void updateHomeSquare (HomeSquare square, StringBuffer stringBuffer) {
         int i = square.getPosition();
        switch (i) {
            case 0,1: i = 24-12*i;
                break;
            case 2: i = 34;
                break;
            case 3: i = 22;
                break;
            case 4: i = 118;
                break;
            case 5: i = 106;
                break;
            case 6: i = 108;
                break;
            case 7: i = 96;
        }
        stringBuffer.replace(i,i+1, square.toString());
    }
    protected void updateGoalSquare (GoalSquare square, StringBuffer stringBuffer) {
        int i = square.getPosition();
        switch (i) {
            case 0: i = 61;
                break;
            case 1: i = 62;
                break;
            case 2: i = 17;
                break;
            case 3: i = 29;
                break;
            case 4: i = 69;
                break;
            case 5: i = 68;
                break;
            case 6: i = 113;
                break;
            case 7: i = 101;
        }
        stringBuffer.replace(i,i+1, square.toString());
    }



}
