import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player implements BoardPiece {

	private Game game;
	private Location location;
	private Character character;
	private ArrayList<Card> cards;
	private boolean used;
	private boolean dead;
	private Room.Name roomLastTurn;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
		dead = false;
		roomLastTurn = null;
	}

	public Location getLocation() {
		return location;
	}

	public boolean getUsed() {
		return used;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public boolean hasCard(Card card) {
		if (cards.contains(card))
			return true;
		else
			return false;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void dealCard(Card card) {
		this.cards.add(card);
	}

	public void printCards() {
		System.out.println("----------------------------");
		System.out.println(this.character.name + "'s cards are: ");
		for (Card c : cards) {
			System.out.println(c.toString());
		}
		System.out.println("----------------------------");
	}

	/**
	 * A simple method that will generate two values between 1 and 6, inclusive
	 * and return their sum.
	 */
	public int rollDice() {
		int roll = (int) (Math.random() * 6) + 1;
		int roll2 = (int) (Math.random() * 6) + 1;
		return roll + roll2;
	}

	/**
	 * Small method to initiate a player's turn Will print the cards they have
	 * and show the board's current state
	 * 
	 */
	public void startTurn() {
		makeMovementDecisions();
	}

	/**
	 * A method to handle how a player move's during their turn A player does
	 * this by entering in each direction they wish to move one by one, until
	 * they either run out of moves or move into a room.
	 * 
	 * If a player moves into a room, they forfeit any remaining moves they have
	 * left.
	 * 
	 */
	public void makeMovementDecisions() {
		Scanner input = new Scanner(System.in);

		boolean turnSkipped = false;

		// Is the player starting their turn in a room?
		// If so, move them to the first free door and start their turn from the
		// door
		if (this.game.isInRoom(this)) {
			Room.Name roomName = this.game.inRoom(this);
			if (this.game.hasFreeDoor(roomName)) {
				ArrayList<Location> doorLocations = this.game.getDoorLocations(roomName);
				System.out.println("You are required to leave this room at the start of your turn.");
				System.out.println("You will not be able to re enter this room on this turn");
				System.out.println("Please select a the door you wish to leave the room from.");
				System.out.println("Doors are numbered starting from 1, top to bottom, left to right");
				System.out.println("Which Door would you like to exit the room from? (Enter a number)");

				int doorNumber = this.game.getUserInput(input, 1, doorLocations.size());
				boolean exitedRoom = false;

				Location desiredDoor = doorLocations.get(doorNumber - 1);
				desiredDoor = this.game.isFreeDoor(desiredDoor);
				while (!exitedRoom) {
					if (desiredDoor != null) { // If the door the user wants to
												// exit is not blocked
						this.location = desiredDoor;
						this.game.getBoard().updateBoard(this.game.humanPlayers);
						exitedRoom = true;
					} else { // The door the user wants to exit IS blocked
						System.out.println("That door is blocked by another player! Choose a different door.");
						doorNumber = this.game.getUserInput(input, 1, doorLocations.size());
						desiredDoor = doorLocations.get(doorNumber - 1);
					}
				}
			} else { // The room doesn't have a free door, end their turn
						// unlucky
				turnSkipped = true;
			}
		}

		// The user is ready to make their moves
		System.out.println("\n\nIt is time to move " + this.character.name + " on the board.");
		printCards();
		System.out.println("You will have four options for each of your moves.");
		System.out.println("up");
		System.out.println("down");
		System.out.println("left");
		System.out.println("right");
		System.out.println("\nEnter 1 to begin your turn by rolling the dice");
		waitForOne(input);

		int roll = rollDice();
		System.out.println("You rolled " + roll + "!");

		game.printBoard();

		int movesRemaining = roll;
		boolean enteredRoom = false;

		while (movesRemaining != 0 && !enteredRoom) {

			System.out.println("You have " + movesRemaining + " moves remaining ");
			System.out.println(this.character.name + " where would you like to move? (up, down, left or right)");

			while (movesRemaining != 0 && !enteredRoom & !turnSkipped) {
				game.printBoard();
				System.out.println("You have " + movesRemaining + " moves remaining " + this.character.name + ".\n");
				System.out.println("Where would you like to move? ");

				int userInput = getInputDirection(input);

				Game.Direction direction = null;

				// Check they have entered a valid direction
				boolean validMove = false;
				if (userInput == 1) {
					direction = Game.Direction.UP;
					validMove = this.game.checkValidMove(this, direction);
				} else if (userInput == 2) {
					direction = Game.Direction.DOWN;
					validMove = this.game.checkValidMove(this, direction);
				} else if (userInput == 3) {
					direction = Game.Direction.LEFT;
					validMove = this.game.checkValidMove(this, direction);
				} else if (userInput == 4) {
					direction = Game.Direction.RIGHT;
					validMove = this.game.checkValidMove(this, direction);
				}

				// If the move was invalid, continue to the next iteration of
				// the
				// while loop
				// Otherwise, apply the move and decrement movesRemaining
				if (validMove) {
					if (direction != null) {
						this.game.applyMove(this, direction);

						if (this.game.isInRoom(this)) {
							enteredRoom = true;
						} // If this player is now in a room

						if (this.game.isInRoom(this)) { // If the player is now
														// in a room
							enteredRoom = true;
							this.roomLastTurn = this.game.inRoom(this);
						}
					}
					movesRemaining--;
				} else {
					System.out.println("invalid move");
				}
				game.printBoard();
			}
			game.printBoard();
			// If the user cannot leave and is stuck in a room because it is
			// blocked
			if (turnSkipped) {
				System.out.println("Unforunately, a player is blocking your only exit.");
				System.out.println("Your turn will now end.");
			}

			if (enteredRoom) {
				game.makeSuggestionDecisions(this);
			} else {
				this.roomLastTurn = null;
				System.out.println(this.character.name + " your turn is over.\n"
						+ "Next Player enter 1 when you are ready to start your turn");

				waitForOne(input);
			}
		}

	}

	/**
	 * gets the users input from the console. expects a integer between the low
	 * and high bounds (inclusive)
	 * 
	 * @param input
	 *            - scanner
	 * @return the users valid input
	 */
	public int getInputDirection(Scanner input) {
		String userInput = "";

		while (true) {
			userInput = input.next();
			if (userInput.equals("up"))
				return 1;
			else if (userInput.equals("down"))
				return 2;
			else if (userInput.equals("left"))
				return 3;
			else if (userInput.equals("right"))
				return 4;
			else
				System.out.println("Please enter up, down, left or right");
		}
	}

	/**
	 * a simple method that waits for the user to enter 1
	 * 
	 * @param input
	 */
	public void waitForOne(Scanner input) {
		String start = "";
		while (!start.equals("1")) {
			start = input.next();
		}
	}

	/**
	 * A simple method to update the Location for this player
	 */
	public void updateLocation(Location location) {
		this.location = location;
	}

	public Character getCharacter() {
		return character;
	}

	public boolean equals(Player p) {
		if (this.character.equals(p.character)) {
			return true;
		}
		return false;
	}

	public void removeFromGame() {
		this.game.getBoard().getBoard()[this.character.startLoc.getY()][this.character.startLoc.getX()] = this;
		dead = true;
	}

	public boolean getDead() {
		return dead;
	}

	public Room.Name getRoomLastTurn() {
		return this.roomLastTurn;
	}

	public String toString() {
		switch (this.character.colour) {
		case WHITE:
			return "1";
		case GREEN:
			return "2";
		case BLUE:
			return "3";
		case PURPLE:
			return "4";
		case RED:
			return "5";
		case YELLOW:
			return "6";
		}
		return "";
	}
}
