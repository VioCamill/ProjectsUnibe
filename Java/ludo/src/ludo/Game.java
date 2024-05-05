package ludo;

import ludo.render.BoardRenderer;
import ludo.square.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Initializes everything a working game needs, including a board, pieces and squares
 */
public class Game {

	//List with all moveSquares (Squares where all players can move)
	private ArrayList<Square> moveSquares;
	//List with all goalSquares
	private ArrayList<GoalSquare> goalSquares;
	//size of goalSquares
	private int sizeOfGoalSquares = 8;
	// List with all HomeSquares
	private ArrayList<HomeSquare> homeSquares;
	//size of homeSquares
	private int sizeOfHomeSquares = 8;

	//the list of players
	private Queue<Player> players;
	//number of players
	private int numberOfPlayers;
	//size of the board
	private int size;
	// distance from startPosition to first HomeRunSquare for each player
	private final int distanceToGoalRunSquares = 34;
	private Player winner;

	private BoardRenderer boardRenderer = new BoardRenderer();

	private boolean canRollAgain;

	private final boolean botGame;

	private CommandParser commandParser = new CommandParser();

	//If you want to manual roll the die
	boolean rollTheDie = false;



	private boolean invariant() {
		return players.size() < 5 && players.size() > 0;
	}


	/**
	 * Initialize the game with the given number of players.
	 *
	 * @param players list with all the players
	 */
	public Game(LinkedList<Player> players) {
		assert players.size() >= 2 && players.size() <= 4;
		this.players = players;
		this.numberOfPlayers = players.size();
		this.size = 40;
		this.winner = null;
		this.canRollAgain = true;
		this.botGame = false;
		initGame();
		assert invariant();
	}


	/**
	 * initializes a game with a fix playerList
	 */
	public Game() {
		LinkedList<Player> players = new LinkedList<>();
		players.add(new Player("Alice"));
		players.add(new Player("Bruce"));
		players.add(new Player("Charlie"));
		players.add(new Player("Dilan"));
		assert players.size() >= 2 && players.size() <= 4;
		this.players = players;
		this.numberOfPlayers = players.size();
		this.size = 40;
		this.winner = null;
		this.canRollAgain = true;
		this.botGame = true;
		initGame();
		assert invariant();
	}


	/**
	 * Plays the game
	 */
	public void play()  {
		Movement movement = new Movement(this); //Instantiates Objects
		Die die = new Die(6);
		// main game loop
		while (this.notOver()) {
			assert this.winner == null;
			if (rollTheDie) commandParser.doYouWantToRollTheDie(currentPlayer());
			int result = die.roll(); //roll the Dice
			System.out.println(currentPlayer().getName()+"'s turn and rolled a "+ result); //print current player

			movement.move(currentPlayer(), result); //move the player's piece and prints out a status methode
			boardRenderer.displayBoard(this); //displays the board in the console
			if (this.checkVictory(currentPlayer())) { //checks if there is a winner
				boardRenderer.winnerMessage(this.winner);
				assert isWinner(currentPlayer());

				//checks if the player has rolled a six and has not his last piece infront the goal
			} if (result == 6 && canRollAgain) {
				System.out.println(currentPlayer().getName() + " has rolled a SIX, you can roll again!");
			} else { //otherwise the player queue gets rotated
				canRollAgain = true;
				rotatePlayerQueue();
			}
		}
	}

	/**
	 * Method that checks if anyone has wone
	 *
	 * @return true if someone has won, false if no one has won
	 */
	public boolean checkVictory(Player player) {
		assert players.contains(player);
		Piece[] pieces = player.getPieces();
		if(pieces[0].onGoalSquare() && pieces[1].onGoalSquare()) {
			this.winner = player;
			return true;
		}
		else
			return false;
	}


	/**
	 * returns the currently active player
	 */
	public Player currentPlayer() {
		return players.peek();
	}

	/**
	 * Rotates player queue
	 */
	public void rotatePlayerQueue() {
		Player player = players.remove();
		players.add(player);
	}


	/**
	 * checks whether a player is the winner
	 * @param player
	 * @return true if player is the winner, else it returns false
	 */
	public boolean isWinner(Player player) {
		return this.winner == player;
	}


	/**
	 * Checks if game is over or not
	 *
	 * @return boolean true, if not over, else false
	 */
	public boolean notOver() {
		return winner == null;
	}


	/**
	 * Manages all the initializations of different game features
	 */
	private void initGame() {
		initPieces();
		initSquares();
		initStartSquares();
		initHomeSquares();
		initGoalRunSquares();
		initGoalSquares();
	}

	/**
	 * Creates the moveSquares according to the size of the board and add them to the moveSquare list
	 */
	private void initSquares() {
		moveSquares = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Square square = new StandardSquare(this, i);
			moveSquares.add(square);
		}
	}

	/**
	 * initializes StartSquares for each Player
	 */
	private void initStartSquares() {
		//moveSquares and players have to be initialized before
		assert moveSquares.size() == size && players.size() == numberOfPlayers;
		int index = 0;
		for (Player p : this.players) {
			StartSquare replacement = new StartSquare(this, index * 10, p);
			p.setStartSquare(replacement);
			moveSquares.set(index*10, replacement);
			assert p.getStartSquare() == moveSquares.get(index*10); //check if each player has the right StartSquare
			index++;
		}
	}

	/**
	 * initializes GoalRunSquares for each player
	 */
	private void initGoalRunSquares() {
		//moveSquares and players have to be initialized before
		assert moveSquares.size() == size && players.size() == numberOfPlayers;
		for (Player p : players) {
			// every player has 5 GoalRunSquares
			for (int i = 1; i <= 5; i++) {
				int distance = (p.getStartSquare().getPosition() + distanceToGoalRunSquares + i) % size;
				GoalRunSquare goalRunSquare = new GoalRunSquare(this, distance, p);
				moveSquares.set(distance, goalRunSquare);
				goalRunSquare.setDistance1(6 -i);
				goalRunSquare.setDistance2(7-i);
				//every GoalRunSquare is connected with one player
				assert goalRunSquare.getPlayer() == p;
			}
		}
	}

	/**
	 * initializes GoalSquares for each player
	 */
	private void initGoalSquares() {
		//players have to be initialized before
		assert players.size() == numberOfPlayers;
		goalSquares = new ArrayList<>();
		for (Player p : players) {
			for (int i = 0; i < 2; i++) {
				GoalSquare goalSquare = new GoalSquare(this, goalSquares.size(), p);
				goalSquares.add(goalSquare);
				p.addGoalSquare(goalSquare);
			}
			assert p.getGoalSquares().size() == 2; //checks if every player has two GoalSquares
		}
		assert goalSquares.size() == sizeOfGoalSquares;
	}

	/**
	 * initializes HomeSquares for each player and enters pieces on them
	 */
	private void initHomeSquares() {
		// players have to be initialized before
		assert players.size() == numberOfPlayers;
		homeSquares = new ArrayList<>();
		for (Player p : players) {
			Piece[] pieces = p.getPieces();
			assert pieces.length == 2; // check if each player has two pieces
			for (int i = 0; i < 2; i++) {
				HomeSquare homeSquare = new HomeSquare(this, homeSquares.size(), pieces[i]);
				pieces[i].setHomeSquare(homeSquare);
				this.homeSquares.add(homeSquare);
				assert pieces[i].getHomeSquare() == homeSquare;
			}
		}
		assert homeSquares.size() == sizeOfHomeSquares;
	}
	/**
	 * initializes all Pieces and allocate a Player to each of them
	 */
	private void initPieces() {
		assert players.size() == numberOfPlayers;
		char[] pieceNames = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd'};
		Player player;
		for (int i = 0; i < 7; i = i + 2) {
			player = players.remove();
			player.addPiece(new Piece(pieceNames[i], player), new Piece(pieceNames[i + 1], player));
			players.add(player);
		}
		assert (players.size() == numberOfPlayers);
	}

	/**
	 * finds next valid Square
	 * @param position actual position
	 * @param moves distance to next Square
	 * @return Square on position (position + moves)
	 */
	public Square findSquare(int position, int moves) {
		assert isValidPosition(position) && 0 < moves && moves < 7;
		int nextPos = position + moves;
		if (nextPos >= size) {
			nextPos = nextPos % size; //next valid position is modulo the size of the board
		}
		assert isValidPosition(nextPos);
		return this.getSquare(nextPos);
	}

	/**
	 * finds StartSquare for a player
	 * @param player
	 * @return StartSquare from player
	 */
	public Square findStartSquare(Player player) {
		//player is a valid Player
		assert players.contains(player);
		return player.getStartSquare();
	}

	/**
	 * gets Square from moveSquares
	 * @param position
	 * @return Square from this position in moveSquares
	 */
	public Square getSquare(int position) {
		assert isValidPosition(position);
		return this.moveSquares.get(position);
	}

	private boolean isValidPosition(int position) {
		return 0 <= position && position < this.size;
	}

	public ArrayList<Square> getMoveSquares() {
		return this.moveSquares;
	}
	public ArrayList<HomeSquare> getHomeSquares(){
		return this.homeSquares;
	}
	public ArrayList<GoalSquare> getGoalSquares(){
		return this.goalSquares;
	}

	public int getSize() {
		return this.size;
	}

	public void setCanRollAgain(boolean value) {
		this.canRollAgain = value;
	}

	public boolean getCanRollAgain() {
		return this.canRollAgain;
	}

	public boolean isBotGame() {
		return this.botGame;
	}


	/**
	 * sets a piece onto a Square
	 * @param piece
	 * @param position
	 */
	public void setPiece(Piece piece, int position) {
		Square square = getSquare(position);
		piece.getSquare().leave(piece);
		square.enter(piece);
	}

	public Queue<Player> getPlayers() {
		return players;
	}

	/**
	 * sets piece on one of its GoalSquare
	 * @param piece
	 * @param i decides on which GoalSquare piece is set
	 */
	public void setPieceToGoal(Piece piece, int i) {
		assert i == 1 || i == 2;
		piece.getSquare().leave(piece);
		Player player = piece.getPlayer();
		ArrayList<GoalSquare> goalSquares = player.getGoalSquares();
		goalSquares.get(i - 1).enter(piece);
	}

	public Player getWinner() {
		return this.winner;
	}

	//With this methode, the driver can change if the user should be asked every time a die is supposed to be rolled
	public void rollTheDieManual(boolean choice){
		this.rollTheDie = choice;
	}

}
