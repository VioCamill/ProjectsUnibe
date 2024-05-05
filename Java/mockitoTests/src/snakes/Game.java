package snakes;

import snakes.squares.Square;

import java.util.*;

/**
 * Representation of a single instance of a Snakes & Ladders game.
 *
 * A game is responsible for keeping track of the squares and the players and
 * offers methods to play the game, query the board, and change board squares.
 */
public class Game {
	private List<snakes.squares.Square> squares;
	private final int SIZE;
	/**
	 *
	 */
	private Deque<Player> players;

	/**
	 * Winner of the game, if any, null otherwise.
	 */
	private Player winner;

	/**
	 * If set to true, skips the next player's turn in the main game loop.
	 */
	private boolean skipNextPlayer;
	private boolean invariant() {
		return squares.size() > 3
				&& SIZE == squares.size()
				&& players.size() > 1;
	}

	/**
	 * Added numbersOfPlayers methode to get the numbers of the players for the roll again square.
	 *
	 * @return number of players as int.
	 */
	public int numbersOfPlayers () {
		return this.players.size();

	}


	/**
	 * Initialize the game with the given size and players.
	 *
	 * The players start in the order they appear in the parameter.
	 * @param size amount of squares on the board
	 * @param players game participants
	 * @param dieSides number of sides the die will contain
	 */
	public Game(int size, Queue<Player> players, int dieSides) {
		this.SIZE = size;
		this.addSquares(size);
		this.addPlayers(players);
		assert invariant();
	}

	public boolean isValidPosition(int position) {
		return position >= 1 && position <= SIZE;
	}

	/**
	 * Initialize and play a sample game.
	 * This method is the only method not being covered by a test,
	 * as it is essentially a test (or better a use case) on its own.
	 */
	public static void main() {
		Queue<Player> players = new LinkedList<>();
		players.add(new Player("Jack"));
		players.add(new Player("Jill"));
		Game game = new Game(15, players, 6);
		game.setSquareToLadder(2, 4);
		game.setSquareToLadder(6, 2);
		game.setSquareToSnake(11, -6);
		snakes.Die die = new snakes.Die(6);
		game.play(die);
	}

	/**
	 * Runs the main loop of the game until there is a winner.
	 *
	 * Starts the game by letting the first player move. Prints actions
	 * and log statements to stdout and announces the winner once the
	 * game is over.
	 */
	public void play(IDie die) {
		System.out.println("Initial state: " + this);
		while (this.notOver()) {
			int roll = die.roll();
			System.out.println(this.currentPlayer() + " rolls " + roll + ":  " + this);
			this.movePlayer(roll);
		}
		System.out.println("Final state:   " + this);
		try {
			System.out.println(this.winner() + " wins!");
		} catch (snakes.GameNotOverException e) {
			System.out.println("No winner.");
		}
	}


	public boolean notOver() {
		return winner == null;
	}

	public boolean isOver() {
		return !this.notOver();
	}

	/**
	 * Player that is currently executing his move.
	 */
	public Player currentPlayer() {
		return players.peek();
	}

	/**
	 * Move the current player by the given amount of squares and
	 * let the player land on the destination.
	 * @param roll amount of squares to move
	 */
	public void movePlayer(int roll) {
		// Check whether players need to be skipped.
		skipIfNecessary();

		Player currentPlayer = rotatePlayerQueue();
		currentPlayer.moveForward(roll);
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
		assert invariant();
	}

	/**
	 * Skip a player's turn if indicated and reset flag.
	 */
	private void skipIfNecessary() {
		if (this.skipNextPlayer) {
			players.remove();
			players.add(currentPlayer());
			this.skipNextPlayer = false;
		}
	}

	public Player rotatePlayerQueue() {
		Player player = players.remove();
		players.add(player);

		return player;
	}

	/**
	 * Replace the square at the given position.
	 * @param position position of the square
	 * @param newSquare square that replaces the current one
	 */
	public void setSquare(int position, snakes.squares.Square newSquare) {
		// Do not change the type of the first or last square!
		assert !this.getSquare(position).isLastSquare()
				&& !this.getSquare(position).isFirstSquare();
		this.initSquare(position, newSquare);
		assert invariant();
	}


	/**
	 * @return the winning player if the game is over
	 * @throws snakes.GameNotOverException if called before the game is over
	 */
	public Player winner() throws snakes.GameNotOverException {
		if (notOver()) {
			throw new GameNotOverException();
		}
		return winner;
	}

	/**
	 * Obtain the unique starting square.
	 */
	public snakes.squares.Square firstSquare() {
		return squares.get(0);
	}

	/**
	 * Obtain the square at the given position.
	 * @param position position on the board, must be within the bounds of the board
	 * @return the square at the position
	 */
	public snakes.squares.Square getSquare(int position) {
		assert this.isValidPosition(position);
		return squares.get(position - 1);
	}

	/**
	 * @return String representation of the game board.
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (snakes.squares.Square square : squares) {
			stringBuilder.append(square.toString());
		}
		return stringBuilder.toString();
	}

	/**
	 * Populate the board with a FirstSquare, followed by size - 2 StandardSquare instances,
	 * followed by a LastSquare.
	 *
	 * @param size the number of squares to be added, must be >= 2
	 */
	private void addSquares(int size) {
		assert size >= 2;

		squares = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			snakes.squares.StandardSquare square = new snakes.squares.StandardSquare(this, i + 1);
			squares.add(square);
		}
		this.initSquare(1, new snakes.squares.FirstSquare(this, 1));
		this.initSquare(size, new snakes.squares.LastSquare(this, size));
	}

	/**
	 * Add the players to the game.
	 *
	 * Replaces any players that have joined already.
	 *
	 * @param participants The players to be added, must not be null
	 */
	private void addPlayers(Queue<Player> participants) {
		assert participants != null;

		players = new LinkedList<>();
		players.addAll(participants);
		for (Player player : players) {
			player.joinGame(this);
		}
	}

	/**
	 * Sets the square at the given position.
	 * @param position position on the board
	 * @param square new square
	 */
	private void initSquare(int position, snakes.squares.Square square) {
		assert this.isValidPosition(position) && square != null;
		squares.set(position - 1, square);
	}

	/**
	 * Inserts a ladder at the given position
	 * @param position position of the ladder on the board
	 * @param transport ladder length
	 */
	public void setSquareToLadder(int position, int transport) {
		this.setSquare(position, new snakes.squares.LadderSquare(transport, this, position));
	}

	/**
	 * Inserts a snake at the given position
	 * @param position position of the snake on the board
	 * @param transport snake length
	 */
	public void setSquareToSnake(int position, int transport) {
		this.setSquare(position, new snakes.squares.SnakeSquare(transport, this, position));
	}

	/**
	 * Look up the square that is `moves` squares away from `position`.
	 *
	 * If the move would go out of bounds, the remaining steps are taken backwards.
	 *
	 * @param position start position
	 * @param moves steps
	 * @return the target square
	 */
	public snakes.squares.Square findSquare(int position, int moves) {
		assert position + moves <= 2 * SIZE - 1; // can't go more than size-1 moves backwards past end
		int target = position + moves;
		if (target > SIZE) { // reverse direction if we go past the end
			target = SIZE - (target - SIZE);
		}
		return this.getSquare(target);
	}

	/**
	 * Obtain a list of all wormhole exit instances on the board.
	 * @return list of wormhole exits
	 */
	public List<snakes.squares.Square> wormholeExits() {
		List<snakes.squares.Square> exits = new LinkedList<>();
		for (Square square : squares) {
			if (square.isWormholeExit()) { exits.add(square); };
		}
		return exits;
	}

	/**
	 * Peek at the next player without changing the queue.
	 *
	 * Note that this is the player that comes next after the {@link Game#currentPlayer()}.
	 * @return the player that comes next
	 */
	public Player nextPlayer() {
		Player current = players.remove();
		Player next = players.peek();
		players.addFirst(current);
		return next;
	}

	/**
	 * Indicate that the next player's turn should be skipped.
	 */
	public void skipNextPlayer() {
		this.skipNextPlayer = true;
	}
}
