import java.util.ArrayList;
import java.util.Scanner;

public class Player implements BoardPiece {

	private Game game;
	private Location location;
	private Character character;
	private ArrayList<Card> cards;
	private boolean used;
	private boolean dead;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
		dead = false;
	}

	public Location getLocation() {
		return location;
	}

	public boolean getUsed() {
		return used;
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
		printCards();
		int roll = rollDice();
		System.out.println("You rolled a " + roll + "!");
		makeMovementDecisions(roll);
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
	public void makeMovementDecisions(int roll) {
		Scanner input = new Scanner(System.in);
		 
		// Is the player starting their turn in a room?
		// If so, move them to the first free door and start their turn from the door
		if(this.game.isInRoom(this) ){ 
			Room.Name roomName = this.game.inRoom(this);
			if(this.game.hasFreeDoor(roomName) ){
				ArrayList<Location> doorLocations = this.game.getDoorLocations(roomName);
				System.out.println("You are required to leave this room at the start of your turn.");
				System.out.println("You will not be able to re enter this room until on this turn");
				System.out.println("Please select a the door you wish to leave the room from.");
				System.out.println("Doors are numbered starting from 1, top to bottom, left to right");
				System.out.println("Which Door would you like to exit the room from? (Enter a number)");
				String doorNumber = input.next();
				
			}
			
			//for(Location loc : doorLocations){
				//if(this.game.firstFreeLocation(loc) != null){;
					//this.location = loc;
					//this.game.getBoard().updateBoard(this.game.humanPlayers);
					//break;
				//}
			//}
		}
		
		// The user is ready to make their moves
		System.out.println("It is time to move "+ this.character.name +" on the board.");
		System.out.println("You will enter either up, down, left or right for each of your " + roll + " moves.");
		int movesRemaining = roll;
		boolean enteredRoom = false;

		while (movesRemaining != 0 && !enteredRoom) {
			game.printBoard();
			System.out.println("You have " + movesRemaining + " moves remaining " + this.character.name + ".\n");
			System.out.println("Where would you like to move? ");

			String userInput = input.next();
			userInput = userInput.toUpperCase();
			Game.Direction direction = null;

			// Check they have entered a valid direction
			boolean validMove = false;
			if (userInput.equals(Game.Direction.UP.toString())) {
				direction = Game.Direction.UP;
				validMove = this.game.checkValidMove(this, direction);
			} else if (userInput.equals(Game.Direction.DOWN.toString())) {
				direction = Game.Direction.DOWN;
				validMove = this.game.checkValidMove(this, direction);
			} else if (userInput.equals(Game.Direction.LEFT.toString())) {
				direction = Game.Direction.LEFT;
				validMove = this.game.checkValidMove(this, direction);
			} else if (userInput.equals(Game.Direction.RIGHT.toString())) {
				direction = Game.Direction.RIGHT;
				validMove = this.game.checkValidMove(this, direction);
			} else {
				System.out.println("That was not a valid expression, please enter up, down, left or right.");
				continue;
			}

			// If the move was invalid, continue to the next iteration of the
			// while loop
			// Otherwise, apply the move and decrement movesRemaining
			if (validMove) {
				if (direction != null) {
					this.game.applyMove(this, direction);
					if(this.game.isInRoom(this) ){enteredRoom = true;} // If this player is now in a room
				}
				movesRemaining--;
			}
		}
		game.printBoard();
		
		if(enteredRoom){
			//makeSuggestionDecisions(this );
		}
		else{
			System.out.println(this.character.name + " your turn is over.\n" 
					+ "Next Player enter 1 when you are ready to start your turn");

			String start = "";
			while (!start.equals("1")) {
				start = input.next();
			}
		}
	}
	

	/**
	 * A simple method to update the Location for this player
	 */
	public void updateLocation(Location location) {
		this.location = location;
	}
	
	public Character getCharacter(){
		return character;
	}
	public boolean equals(Player p){
		if(this.character.equals(p.character) ){return true;}
		return false;
	}

	public void removeFromGame() {
		this.game.getBoard().getBoard()[this.character.startLoc.getY()][this.character.startLoc.getX()] = this;
		dead = true;
	}
	
	public boolean getDead() {
		return dead;
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
