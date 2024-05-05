package ludo;


import ludo.square.GoalRunSquare;
import ludo.square.GoalSquare;
import ludo.square.Square;

import java.lang.reflect.Array;


/**
 * Class that handles every movement related to a piece
 *
 */




public class Movement {

    //Game Object
    private Game ludo;

    //Parser Object, used to parse player input
    private CommandParser parser;


    /**
     * Movement invariant
     *
     * @return
     */
    public boolean invariant() {
        return ludo != null && parser != null;
    }

    /**
     * Constructor
     *
     * @param game a game object
     */
    public Movement(Game game) {
        this.ludo = game;
        this.parser = new CommandParser();
        assert invariant();
    }


    /**
     * Control methode, organising the class and calling the right methods
     *
     * @param player current player
     * @param roll the number of steps the player can move
     * @return String with the status of the movement (only for testing purposes)
     */
    public String move(Player player,  int roll) {
        assert player != null && roll != 0;

        Piece[] pieces = player.getPieces();

        //Checks if a piece can be placed on the board
        if (canEnterGame(player, pieces, roll)) {
            enterToGame(pieces);
            return "PieceEnterGame";
        }

        //checks if a piece can enter a goal square
        if (canEnterGoal(pieces[0], roll, player) || canEnterGoal(pieces[1], roll, player)) {
            if (canEnterGoal(pieces[0], roll, player)) {
                moveOnGoalSquare( roll, pieces[0], "Piece "+pieces[0].getName()+" has entered a goal square");
                return "Piece1EnterGoal";
            } else {
                moveOnGoalSquare(roll, pieces[1], "Piece "+pieces[1].getName()+" has entered a goal square");
                return "Piece2EnterGoal";
            }
        }

        //if no piece can enter the game or enter a goal, they can be moved on the board
        if (!pieces[0].stillOnHomeSquare() || !pieces[1].stillOnHomeSquare()) {

            switch (whichMoves(pieces, roll)) {

                case 0:
                    moveAndPrint(roll, pieces[0], "Piece "+pieces[0].getName()+" has moved");
                    return "Piece1Moved";
                case 1:
                    moveAndPrint(roll, pieces[1], "Piece "+pieces[1].getName()+" has moved");
                    return "Piece2Moved";
                case 2:
                    var choice = getPlayerDecision(pieces);
                    moveAndPrint(roll, pieces[choice], "Piece " + pieces[choice].getName() + " has been moved");
                    return "PlayerMoved";

                case -1:
                    if (checkIfCanRollAgain(pieces)) {
                        ludo.setCanRollAgain(false);
                        return "CantRollAgain";
                    }
                    System.out.println("No piece can be moved");
                    return "NoneMoved";
            }
        } else {
            System.out.println("No piece can be moved");
            return "AllAtHome";
        }
        return "Error";
    }

    /**
     * checks if a piece can enter the game
     * @param roll eyes on dice
     * @param piece current piece that is played
     * @param printStatement String that is printed if the piece can enter the game
     */
    public void moveAndPrint(int roll, Piece piece , String printStatement) {
        assert roll > 0 && piece != null;
        moveAndLand(roll, piece);
        System.out.println(printStatement);
    }

    /**
     * gets the current and the next square and calls movePiece method to move the piece, if the move is valid
     *
     * @param moves rolled eyes on dice
     * @param piece current piece that is played
     */
    public void moveAndLand(int moves, Piece piece) {
        assert moves > 0 && piece != null;
        Square current = piece.getSquare();
        Square next = ludo.findSquare(current.getPosition(), moves);
        if (this.isValidMove(current, next)) {
            movePiece(current, next, piece);
            assert !current.isOccupied() && next.isOccupied();

        }
    }

    /**
     * moves a piece to a goal square it can reach
     *
     * @param moves rolled eyes on dice
     * @param piece current piece that is played
     * @param printStatement String that is printed if the piece can enter the game
     */
    public void moveOnGoalSquare(int moves, Piece piece, String printStatement) {
        assert piece.getSquare().isGoalRunSquare(piece);
        GoalRunSquare goalRunSquare = (GoalRunSquare) piece.getSquare();

        if (goalRunSquare.getDistance1() == moves && !getGoalSquare(piece, 0).isOccupied()) {
            movePiece(goalRunSquare, getGoalSquare(piece, 0), piece);
            System.out.println(printStatement);
        } else {
            movePiece(goalRunSquare, getGoalSquare(piece, 1), piece);
            System.out.println(printStatement);
        }


    }

    /**
     * moves a piece to the next square, and if this is occupied by another player, it sends their piece back home
     * @param current square from the piece
     * @param next square to which piece wants to move
     * @param piece current piece that is played
     */
    public void movePiece(Square current, Square next, Piece piece){
        assert current != null && next != null && piece != null;
        current.leave(piece);
        if (next.isOccupied()) {
            this.placePieceOnStart(next);
        }
        next.enter(piece);
        assert !current.isOccupied() && next.isOccupied();
    }
    /**
     * places a piece on the start square of the player
     *
     * @param square square from which the piece is sent back home
     */
    private void placePieceOnStart(Square square) {
        assert square.isOccupied();
        square.sendBackHome(square.occupiedBy());
        assert !square.isOccupied();
    }


    /**
     * determines if a move is valid
     *
     * @param current square from the piece
     * @param next square to which piece wants to move
     *
     * @return true if next is empty or if it is occupied by another players piece
     */
    public boolean isValidMove(Square current, Square next){
        assert current != null && next != null;
        return !next.isOccupied() ||
                current.occupiedBy().getPlayer() != next.occupiedBy().getPlayer();
    }


    /**
     * determines if a piece can enter the game
     *
     * @param player player who is playing
     * @param pieces pieces of the player
     * @param roll eyes on dice
     *
     * @return true if the piece can enter the game, false if it can't
     */
    public boolean canEnterGame(Player player, Piece[] pieces, int roll) {
        return (pieces[0].stillOnHomeSquare() ||
                pieces[1].stillOnHomeSquare()) &&
                !(ludo.findStartSquare(player).isOccupied()) &&
                roll == 6;
    }


    /**
     * determines which piece can enter the game
     * @param pieces pieces of the player
     *
     * @return piece that can enter a goal square
     */
    public Piece whichPieceEnters(Piece[] pieces) {
        assert Array.getLength(pieces) == 2;
        if (pieces[0].stillOnHomeSquare()) {
            return pieces[0];
        } else {
            return pieces[1];
        }
    }


    /**
     * determines if a piece can enter a goal square
     *
     * @param piece piece to be checked
     * @return true, if it can enter, false if it can't
     */
    public boolean canEnterGoal(Piece piece, int roll, Player player) {
        assert piece != null;
        if (piece.getSquare().isGoalRunSquare(piece)) { //determines if the piece is on a goal run square

            GoalRunSquare goalRunSquare = (GoalRunSquare) piece.getSquare();

            if (goalRunSquare.getDistance1() == roll && !player.getGoalSquares().get(0).isOccupied()) {
                return true;
            } if (goalRunSquare.getDistance2() == roll && !player.getGoalSquares().get(1).isOccupied()) {
                return true;
            } else
                return false;
        } else
            return false;
    }


    /**
     * determines which piece can move
     *
     * @param pieces pieces of the player
     * @param roll eyes on dice
     * @return 0 if only piece 1 can move
     * @return 1 if only piece 2 can move
     * @return 2 if both can move
     * @return -1 if none can move
     */
    public int whichMoves(Piece[] pieces, int roll) {
        assert Array.getLength(pieces) == 2;
        boolean oneCan = false, twoCan = false;

        //checks if piece one can move
        if (pieceCanMove(pieces[0], roll)) {
            oneCan = true;
        }
        //checks if piece two can move
        if (pieceCanMove(pieces[1], roll)) {
            twoCan = true;
        }
        //returns 2 if both can move
        if (oneCan && twoCan)
            return 2;
        //returns 0, if only piece 1 can move
        if (oneCan)
            return 0;
        //returns 1, if only piece 2 can move
        if (twoCan)
            return 1;
        //returns -1 if both can't move
        else
            return -1;
    }


    /**
     * determines if a piece can move
     *
     * @param piece piece to be checked
     * @param roll eyes on dice
     * @return true if it can move, false if it can't
     */
    public boolean pieceCanMove(Piece piece,int roll) {
        if (piece.getSquare().isGoalRunSquare(piece)) {
            GoalRunSquare goalRunSquare = (GoalRunSquare) piece.getSquare();
            if (goalRunSquare.getDistance1() < roll - 1 || goalRunSquare.getDistance2() < roll - 2) {
                return false;
            }
        }

        return !piece.stillOnHomeSquare() &&
                !piece.onGoalSquare() &&
                isValidMove(piece.getSquare(), ludo.findSquare(piece.getSquare().getPosition(), roll));

    }


    /**
     * determines if a player can't roll again
     *
     * @param pieces pieces of the player
     * @return true if the player is unable to roll again, false if he is able to
     */
    public boolean checkIfCanRollAgain(Piece[] pieces) {
         return pieces[0].getSquare().isGoalRunSquare(pieces[0]) &&
                pieces[1].getSquare().isGoalSquare() ||
                pieces[1].getSquare().isGoalRunSquare(pieces[1]) &&
                        pieces[0].getSquare().isGoalSquare();
    }


    /**
     * enter a piece to the game and prints which piece has entered
     *
     * @param pieces pieces of the player
     */
    public void enterToGame(Piece[] pieces) {
        assert Array.getLength(pieces) == 2;
        Piece piece = whichPieceEnters(pieces);
        piece.getHomeSquare().enterToGame(piece);  //calls method, that places piece on starter square
        System.out.println("Piece "+ piece.getName() +" has entered the Game");
        assert !piece.stillOnHomeSquare();
    }


    public GoalSquare getGoalSquare(Piece piece, int index) {
        return piece.getPlayer().getGoalSquares().get(index);
    }

    public int getPlayerDecision(Piece[] pieces) {
        System.out.println("Which piece do you want to move? Enter 1 for "+ pieces[0].getName()+" and 2 for "+pieces[1].getName());
        return this.parser.playerDecision(ludo.isBotGame()) - 1;
    }

    public void setCommandParser(CommandParser cmdParser) {
        this.parser = cmdParser;
    }

    public void setGame(Game ludo){
        this.ludo = ludo;
    }

    /**
     * set piece to a give square, for testing only
     *
     * @param piece
     * @param goToSquare
     */

    public void setPieceToSquare(Piece piece, Square goToSquare) {
        assert piece != null && goToSquare != null;
        piece.getSquare().leave(piece);
        goToSquare.enter(piece);
    }

}
