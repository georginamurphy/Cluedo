package cluedo.boardpieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import cluedo.cards.Card;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.game.Game;

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
	
	// The user defined name for the player
	private String name;

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
	 * Constructor for a player that takes a user defined name
	 * @param name - user's defined name
	 * @param used - true if this is a human player
	 */
	public Player(String name, boolean used){
		this.name = name;
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
	
	public void setCharacter(Character charac){
		this.character = charac;
	}
	
	/**
	 * Getter method for the name of this player
	 * @return the name
	 */
	public String getName() {
		if(name == null){return this.character.toString();}
		return name;
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
	
	public void setName(String name){
		this.name = name;
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
	 * A simple method to update the Location for this player
	 */
	public void updateLocation(Location location) {
		this.location = location;
	}

	/**
	 * Performs actions that are required for the player starting their turn in a room.
	 * For example, choosing what door they would like to leave from,
	 * checking if the room they are in is blocked off,
	 * moving the player's location after the choose a door to exit from,
	 * will return a boolean representing whether a player's turn is being skipped.
	 * @return
	 */
	public boolean startTurnInRoom() {
		Room.Name roomName = this.game.inRoom(this);
		//Scanner input = new Scanner(System.in);
		if (this.game.hasFreeDoor(roomName)) {
			ArrayList<Location> doorLocations = this.game.getDoorLocations(roomName);

			//int doorNumber = this.game.getUserInput(input, 1, doorLocations.size());
			int doorNumber = game.controller.getDoorNumber(doorLocations.size() );
			boolean exitedRoom = false;
			Location desiredDoor = doorLocations.get(doorNumber - 1);
			desiredDoor = this.game.isFreeDoor(desiredDoor);
			while (!exitedRoom) {
				if (desiredDoor != null) { // If the door the user wants to
											// exit is not blocked
					this.location = desiredDoor;
					this.game.getBoard().updateBoard(this.game.controller.getAllPlayers() );
					exitedRoom = true;
					this.game.controller.drawBoard();
				} else { // The door the user wants to exit IS blocked
					doorNumber = game.controller.getDoorNumber(doorLocations.size() );
					desiredDoor = doorLocations.get(doorNumber - 1);
				}
			}
		} 
		else { // The room doesn't have a free door, end their turn
			return true;
		}
		return false;
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
	
	@Override
	public ImageIcon getImageIcon() {
		switch(character.colour){
			case WHITE:
				return new ImageIcon("white.png");
			case GREEN:
				return new ImageIcon("green.png");
			case BLUE:
				return new ImageIcon("blue.png");
			case PURPLE:
				return new ImageIcon("purple.png");
			case RED:
				return new ImageIcon("red.png");
			case YELLOW:
				return new ImageIcon("yellow.png");
			default:
				return new ImageIcon(); // Shouldn't happen
		}
		
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
