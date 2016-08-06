package cluedo.boardpieces;
import java.util.ArrayList;
import java.util.Scanner;

import cluedo.cards.Card;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.game.Game;
import cluedo.game.Game.Direction;

/**
 * The player class represents a player in the Cluedo game.
 * Can either be a human player, or a non human player. 
 *  
 * Non-human players have no AI, but their pieces on the board can be manipulated by human players,
 * which is why they are still classified as 'players'.
 */
public class Player implements BoardPiece {

	// The overall game object for Cluedo
	private Game game;

	// This player's location on the board
	private Location location;

	// This player's character that they are playing as
	private Character character;

	// The cards in this player's hand
	private ArrayList<Card> cards;

	// True if the player is controlled by a human, false otherwise
	private boolean used;

	// True if the player has lost the game, false otherwise
	private boolean dead;

	// Name of the room the player was in during their previous turn
	// Will be null if they finished their turn in a hallway.
	private Room.Name roomLastTurn;

	/**
	 * Constructor for a player
	 * 
	 * @param character
	 *            - The character they are playing as
	 * @param used
	 *            - True if they are a human player, false otherwise
	 */
	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
		dead = false;
		roomLastTurn = null;
	}
	
	/**
	 * Getter method for this player's location
	 * 
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Getter method for if this player is human
	 * 
	 * @return
	 */
	public boolean getUsed() {
		return used;
	}
	
	/**
	 * Getter method for the character for this player
	 * @return
	 */
	public Character getCharacter() {
		return character;
	}
	
	/**
	 * A method which removes the player from the game because they have lost
	 */
	public void removeFromGame() {
		this.game.getBoard().getBoard()[this.character.startLoc.getY()][this.character.startLoc.getX()] = this;
		dead = true;
	}

	/**
	 * A getter method for the dead field
	 * @return - True if the player has lost, false otherwise
	 */
	public boolean getDead() {
		return dead;
	}

	/**
	 * Returns the room the player was in during their previous turn
	 * Will be null if they player did not finish in a room
	 * @return
	 */
	public Room.Name getRoomLastTurn() {
		return this.roomLastTurn;
	}

	/**
	 * Getter method for the player's hand of cards
	 * 
	 * @return
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * Method to check if a player's hand contains a specific card
	 * 
	 * @param card
	 *            - The card we are inspecting the player's hand for
	 * @return
	 */
	public boolean hasCard(Card card) {
		if (cards.contains(card))
			return true;
		else
			return false;
	}

	/**
	 * Setter method the game field
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Adds a card to this player's hand
	 * 
	 * @param card
	 *            - The card to add to the player's hand
	 */
	public void dealCard(Card card) {
		this.cards.add(card);
	}

	/**
	 * Prints the player's hand of cards
	 */
	public void printCards() {
		System.out.println("----------------------------");
		System.out.println(this.character.name + "'s cards are: ");
		for (Card c : cards) {
			System.out.println(c.toString());
		}
		System.out.println("----------------------------");
	}
	
	/**
	 * A simple method to update the Location for this player
	 */
	public void updateLocation(Location location) {
		this.location = location;
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
	 * Initiates the player's turn, starts a chain of method calls that will
	 * play out everything the player must do to complete their turn.
	 */
	public void startTurn() {
		System.out.println("_________________________________________________________ " + this.character.name
				+ " _________________________________________________________");
		Scanner input = new Scanner(System.in);
		boolean turnSkipped = false;
		// Is the player starting their turn in a room?
		// If so, move them to the first free door and start their turn from the
		// door
		// (They will only be able to move out of the door, into a hallway)
		if (this.game.isInRoom(this)) {
			turnSkipped = startTurnInRoom();
		}

		// The user is ready to make their moves
		System.out.println("\nIt is time to move " + this.character.name + " on the board.");
		printCards();
		System.out.println("\nPress 1 to roll the dice");
		waitForOne(input);

		int roll = rollDice();
		// int roll = 12;
		System.out.println("You rolled " + roll + "!");
		game.printBoard();
		makeMovementDecisions(turnSkipped, roll);
	}

	/**
	 * A method to handle how a player moves during their turn. A player moves
	 * by entering in each direction they wish to move one by one, until they
	 * either run out of moves or move into a room.
	 * 
	 * If a player moves into a room, they forfeit any remaining moves they have
	 * left.
	 */
	public void makeMovementDecisions(boolean turnSkipped, int roll) {
		Scanner input = new Scanner(System.in);
		boolean enteredRoom = false;
		
		while (roll != 0 && !enteredRoom & !turnSkipped) {
			this.game.initialiseDoorLocations();
			System.out.println("You have " + roll + " moves remaining ");
			System.out.println(this.character.name + " where would you like to move? (up, down, left, right or accuse)");

			int userInput = getInputDirection(input);
			Game.Direction direction = null;

			// Check they have entered a valid direction
			boolean validMove = false;
			if (userInput == 1) {
				direction = Game.Direction.UP;
				validMove = this.game.checkValidMove(this, direction);
				roll = applyValidMove(validMove, direction, roll);
			} else if (userInput == 2) {
				direction = Game.Direction.DOWN;
				validMove = this.game.checkValidMove(this, direction);
				roll = applyValidMove(validMove, direction, roll);
			} else if (userInput == 3) {
				direction = Game.Direction.LEFT;
				validMove = this.game.checkValidMove(this, direction);
				roll = applyValidMove(validMove, direction, roll);
			} else if (userInput == 4) {
				direction = Game.Direction.RIGHT;
				validMove = this.game.checkValidMove(this, direction);
				roll = applyValidMove(validMove, direction, roll);
			} else if (userInput == 5) {
				// make accusation
				game.makeAccusation(this, input);
				roll = 0;
				
			}
		}

		// If the user cannot leave a room because the only exit is blocked
		if (turnSkipped) {
			System.out.println("Unforunately, a player is blocking your only exit.");
			System.out.println("Your turn will now end.");
		}

		if(!game.getGameEnd() ){
			if (!enteredRoom) {
				this.roomLastTurn = null;
			}

			System.out.println(this.character.name + " your turn is over.\n"
					+ "Next Player enter 1 when you are ready to start your turn");

			waitForOne(input);
		}
	}

	/**
	 * Performs actions that are required for the player starting their turn in a room.
	 * For example, choosing what door they would like to leave from,
	 * checking if the room they are in is blocked off,
	 * moving the player's location after the choose a door to exit from,
	 * will return a boolean representing whether a player's turn is being skipped.
	 * @return
	 */
	@SuppressWarnings("resource")
	public boolean startTurnInRoom() {
		Room.Name roomName = this.game.inRoom(this);
		Scanner input = new Scanner(System.in);
		if (this.game.hasFreeDoor(roomName)) {
			ArrayList<Location> doorLocations = this.game.getDoorLocations(roomName);
			game.printBoard();
			System.out.println(
					this.character.name + ", you are required to leave this room at the start of your current turn.");
			System.out.println("You will not be able to re-enter this room until your next turn.");
			System.out.println("Please select the door you wish to leave the room from.");
			System.out.println("Doors for each room are numbered left to right, top to bottom, starting from 1.");
			System.out.println(
					"Which Door would you like to exit the room from? (Enter 1 - " + doorLocations.size() + ")");

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
			game.printBoard();
		} 
		else { // The room doesn't have a free door, end their turn
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param validMove - True if a move needs to be applied in the given direction,
	 * 					  false if the move was invalid and shouldn't be applied.
	 * @param direction - The direction a validMove will be applied in
	 * @param roll - An int representing how many moves a player has left to make
	 * 
	 * @return - How many moves the player has left after the application of the move.
	 * 			 If the move was invalid, will return the same value that it was passed.
	 *           If the move was valid, it will return the value passed, decremented by one.
	 */
	public int applyValidMove(boolean validMove, Game.Direction direction, int roll){
		String invalid = "";
		
		// If validMove is true, we must apply the move to the board in the given direction
		// Otherwise, set the invalid String to an appropriate message and print it to the player.
		// Will decrement roll by one if a valid move was made.
		if (validMove) {
			if (direction != null) {
				this.game.applyMove(this, direction);
				
				// If the player is now in a room, set the appropriate fields and call
				// the makeSuggestions method.  This initiates the next 'phase' of a 
				// players turn
				if (this.game.isInRoom(this)) {
					this.roomLastTurn = this.game.inRoom(this); 
					game.makeSuggestionDecisions(this);
					return 0; // The user made a suggestion or accusation, forfeiting their remaining moves
				}
			}
			roll--;
		} 
		else {
			invalid = "You made an invalid move, please try again.";
		}
		// Print the updated board, and the invalid String.
		// 
		game.printBoard();
		System.out.println(invalid);
		return roll;
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
			else if (userInput.equals("accuse"))
				return 5;
			else
				System.out.println("Please enter up, down, left or right");
		}
	}

	/**
	 * A simple method that waits for the user to enter 1
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
	 * A method which determines if two players are equal.
	 * A player is considered equal to another if they share the same character.
	 * 
	 * @param p - The player we are comparing this object to
	 * @return
	 */
	public boolean equals(Player p) {
		if (this.character.equals(p.character)) {
			return true;
		}
		return false;
	}
	
	/**
	 * A toString method for a player
	 */
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
