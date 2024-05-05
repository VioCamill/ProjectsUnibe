import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * takes the input stream from @BoardMaker and checks if the commands are valid
 * if a command is not valid it will throw a ParserException
 * parses all valid commands in a format such that @BoardMaker can give them directly to @Car
 */

public class CommandParser {
    private String[] lineCommands;
    private ArrayList<String> validCommands;
    private ArrayList<String[]> orderedCmd;
    private ArrayList<int[]> carMoves;
    private String carProgram;


    public CommandParser() {
        this.validCommands = new ArrayList<>();
        this.orderedCmd = new ArrayList<>();
        this.carMoves = new ArrayList<>();

    }

    public ArrayList<int[]> makeCarCommandFrom(String carProgram) throws ParserException {
        this.carProgram = carProgram;
        this.lineCommands = this.splitInputStream(this.carProgram);
        this.validCommands = this.filterValidCommands(lineCommands);
        this.orderedCmd = this.orderCommands(this.validCommands);
        this.carMoves = this.translateCommands(this.orderedCmd);
        return this.carMoves;

    }


    /**
     * splits the incoming StringStream into lines
     * @return String Array with line commands
     */
    //I could not find the bug here, it still takes every keystroke that comes from the user
    public String[] splitInputStream(String s) {
        String[] lineCommands = s.split("\\R");
        return lineCommands;
    }

    /**
     * selects the valid commands from invalid ones
     *
     * @return ArrayList with only valid commands
     * @throws ParserException if the command is not valid
     */
    public ArrayList<String> filterValidCommands(String[] lineCmd) throws ParserException {
        for (int i = 0; i < lineCmd.length; i++) {
            if (this.isValid(lineCmd[i])) {
                assert (this.isValid(lineCmd[i]));
                this.validCommands.add(lineCmd[i]);
            } else {
                assert (!this.isValid(lineCmd[i]));
                throw new ParserException(lineCmd[i]);
            }
        }
        return this.validCommands;
    }

    /**
     * helper method to parse commands in a suitable form for car moves
     * @return ArrayList with commands separated by \s
     */
    public ArrayList<String[]> orderCommands(ArrayList<String> validCmd) {
        for (String cmd : validCmd) {
            assert (this.isValid(cmd));
            String[] split = cmd.split(" ");
            this.orderedCmd.add(split);
        }
        return this.orderedCmd;
    }
    /**
     * translates commands in a suitable form for car moves
     * @return ArrayList with parsed commands for the car
     */
    public ArrayList<int[]> translateCommands(ArrayList<String[]> orderedCmd) {
        for (String[] cmd : orderedCmd) {
            this.carMoves.add(this.translateMoves(cmd));
        }
        return this.carMoves;
    }

    /**
     * decides how the commands are translated for a car move
     * @return int Array with car actions for each move
     */
    public int[] translateMoves(String[] cmd) {
        int[] moveCommands = new int[4];

        int length = cmd.length;
        switch (length) {
            case 1:
                assert (cmd[0].equals("left") || cmd[0].equals("right"));
                // if the command is "left", set first move command to 0. if it is right set it to 1
                if (cmd[0].equals("left")) {
                    moveCommands[0] = 0;
                } else {
                    moveCommands[0] = 1;
                }
                break;
            case 2:
                assert (cmd[0].equals("forward") || cmd[0].equals("backward"));
                assert (this.isInteger(cmd[1]));
                // set second move command to amount of steps
                moveCommands[1] = Integer.parseInt(cmd[1]);
                // if the command is "forward" set first move command to 2, "backward" to 3
                if (cmd[0].equals("forward")) {
                    moveCommands[0] = 2;
                } else {
                    moveCommands[0] = 3;
                }
                break;
            case 3:
                assert (cmd[0].equals("drive"));
                assert (this.isInteger(cmd[1]));
                assert (this.isInteger(cmd[2]));
                // if the command is drive, set move command to 4
                moveCommands[0] = 4;
                // set third move command to amount of steps in x direction
                moveCommands[2] = Integer.parseInt(cmd[1]);
                // set fourth move command to amount of steps in y direction
                moveCommands[3] = Integer.parseInt(cmd[2]);
                break;
        }

        return moveCommands;
    }

    /**
     * checks if a command fits the predetermined pattern of a valid command
     *
     * @param s
     * @return true if s is a valid command
     */
    public boolean isValid(String s) {
        if ((this.matchesStraight(s) || this.matchesDirection(s) || this.matchesDrive(s))
                && !s.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * helper method for validCommands()
     * @param s to be checked if it matches "forward n" or "backward n"
     * @return true if the String matches
     */
    private boolean matchesStraight(String s) {
        return this.isValidPattern(s, "(forward\s|backward\s)\\d+");
    }

    /**
     * helper method for validCommand
     * @param s to be checked if it matches "drive x y"
     * @return true if the String matches
     */
    private boolean matchesDrive(String s) {
        return this.isValidPattern(s, "(drive\s)\\d+\s\\d+");
    }

    /**
     * helper method for validCommand
     * @param s to be checked if it matches "right" or "left"
     * @return true if the String matches
     */
    private boolean matchesDirection(String s) {
        return this.isValidPattern(s, "(left|right)");
    }

    /**
     * helper method for validCommands
     * @param s     to be checked if it matches with regex
     * @param regex that s should match
     * @return true if it matches
     */
    private boolean isValidPattern(String s, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * helper method for Assertion
     * @param s to be checked if it is an integer
     * @return true if the String is an Integer
     */
    private boolean isInteger(String s) {
        return this.isValidPattern(s, "\\d+");
    }
}