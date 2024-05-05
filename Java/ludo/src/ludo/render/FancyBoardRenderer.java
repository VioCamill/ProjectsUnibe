package ludo.render;

/**
 * creates a String, that represents the board, it's a nicer board. Can be used in the futur.
 *StringBuffer needs to mapped to integrate it.
 *
 */
public class FancyBoardRenderer {

    /**
     * Crates a string representing the board with all the tiles, that do not change.
     *
     * @return a String, that will represent the board in the console
     */
    public static StringBuffer createInvariantFancyBoard() {
        //This code was writting before I decided to work with StringBuffers.
        String boardAsString = "";

        //creates a template for the boarder
        String row1 = "+";
        for (int i = 0; i < 11; i++) {
            row1 += "---+";
        }

        boardAsString = row1 + "\n";

        //creates template for row2
        String row2 = "|";
        String presentation = row1;
        for (int i = 0; i < 11; i++) {
            if (i < 4 || i > 6) row2 += " # |";
            else if (i != 6) row2 += "    ";
            else row2 += "   |";
        }


        boardAsString += row2 + "\n";


        String threeEmpty = "|           |"; //Helper string for three empty squares
        String row3 = threeEmpty;
        row3 += " # |   |   |   | # " + threeEmpty;
        boardAsString += row3 + "\n" + row3 + "\n";

        String row5 = row2.substring(0, 17) + "   | # |   " + row2.substring(0, 17);

        boardAsString += row5 + "\n";

        String row6 = threeEmpty.substring(0, threeEmpty.length() - 1)
                + threeEmpty.substring(1, 9) + "| # |" +
                threeEmpty.substring(1, threeEmpty.length() - 1) + "        |";

        boardAsString += row6 + "\n";

        String row7 = "+   " + row1.substring(0,row1.length()-9) + "+   +";
        boardAsString += row7 + "\n";

        String row8 = threeEmpty + row2.substring(1,17) + " # " + threeEmpty;

        boardAsString += row8 + "\n";
        boardAsString += row7 +"\n"+ row6 + "\n"+ row5 + "\n"+ row3 + "\n"+ row3;
        boardAsString += "\n"+ row2 + "\n"+ row1;

        StringBuffer basicBoard = new StringBuffer(boardAsString);

        return basicBoard;
    }







}










