import java.util.ArrayList;
import java.util.Scanner;

public class Player implements BoardPiece {

	private Game game;
	private Location location;
	private Character character;
	private ArrayList<Card> cards;
	private boolean used;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
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
	
	public void setGame(Game game){
		this.game = game;
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
	
	public void dealCard(Card card){
		this.cards.add(card);
	}
	
	public void printCards(){
		System.out.println("----------------------------");
		System.out.println(this.character.name + "'s cards are: \n");
		for(Card c : cards){
			System.out.println(c.toString() + "\n");
		}
		System.out.println("----------------------------");
	}
	
	/**
	 * A simple method that will generate two values between 1 and 6, inclusive
	 * 	and return their sum.
	 */
	public int rollDice(){
		int roll = (int) (Math.random() * 6) + 1;
		int roll2 = (int) (Math.random() * 6) + 1;
		return roll + roll2;
	}
	
	/**
	 * Small method to initiate a player's turn
	 * Will print the cards they have and show the board's current state
	 * 
	 */
	public void startTurn(){
		System.out.println("These are your cards " + this.character.name + "\n");
		printCards();
		int roll = rollDice();
		System.out.println("You rolled a " + roll + "!"); 
		makeMovementDecisions(roll);
	}
	
	/**
	 * A method to handle how a player move's during their turn
	 * A player does this by entering in each direction they wish to move one by one,
	 * 	until they either run out of moves or move into a room.
	 * 
	 * If a player moves into a room, they forfeit any remaining moves they have left.
	 * 
	 */
	public void makeMovementDecisions(int roll){
		Scanner input = new Scanner(System.in);
		System.out.println("It is time to move your character on the board.");
		System.out.println("You will enter either up, down, left or right for each of your " + roll + " moves.");
		int movesMade = 0;
		int movesRemaining = roll;
		boolean enteredRoom = false;
		while(movesRemaining != 0 && !enteredRoom){
			System.out.println("You have " + movesRemaining + " moves remaining.\n");
			System.out.println("Where would you like to move? ");
			String direction = input.next();
			direction.toLowerCase();
			
			// Check they have entered a valid direction
			boolean validMove = false;
			if(direction.equals("up") ){
				validMove = this.game.checkValidMove(this.location, direction);
			}
			else if(direction.equals("down") ){
				validMove = this.game.checkValidMove(this.location, direction);
			}
			else if(direction.equals("left") ){
				validMove = this.game.checkValidMove(this.location, direction);
			}
			else if(direction.equals("right") ){
				validMove = this.game.checkValidMove(this.location, direction);
			}
			else{
				System.out.println("That was not a valid expression, please enter up, down, left or right.");
				continue;
			}
			
			if(!validMove){continue;}
		}
		
	}
}
