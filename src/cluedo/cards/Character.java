package cluedo.cards;
import cluedo.boardpieces.Location;

/**
 * A class representing a Character in Cluedo, implements Card.
 * Character's hold a name, colour and starting location for each of the respective Characters in Cluedo.
 */
public class Character implements Card {
	// The name of the character
	public String name;
	
	// The colour of the character
	public Colour colour;
	
	// The starting location for the character
	public Location startLoc;

	/*
	 * A simple enum class that represents the colour of this character
	 */
	public enum Colour {
		WHITE, GREEN, BLUE, PURPLE, RED, YELLOW;
	}

	/**
	 * A constructor for the Character class.  
	 * 
	 * @param name - The name of the character
	 * @param colour - The colour of the character
	 * @param x - The x coordinate on the grid for the starting location of this character
	 * @param y - The y coordinate on the grid for the starting location of this character
	 */
	public Character(String name, Colour colour, int x, int y) {
		this.name = name;
		this.colour = colour;
		this.startLoc = new Location(x, y);
	}
	
	/**
	 * A constructor for character that takes only a colour as a parameter
	 * 
	 * @param colour - The colour for this character
	 */
	public Character(Colour colour) {
		this.colour = colour;
		switch(colour){
		case WHITE:
			name = "Mrs White";
			break;
		case GREEN:
			name = "Reverend Green";
			break;
		case BLUE:
			name = "Mrs Peacock";
			break;
		case PURPLE:
			name = "Proffessor Plum";
			break;
		case RED:
			name = "Miss Scarlett";
			break;
		case YELLOW:
			name = "Colonel Mustard";
			break;
		}
	}
	
	/**
	 * Getter method for this starting location for this character
	 * 
	 * @return - The starting location
	 */
	public Location getStartLoc() {
		return startLoc;
	}

	/**
	 * An equals method that compares two characters.
	 * If two characters share the same colour, they are considered equal.
	 * 
	 * @param character - The character we are comparing with
	 * @return
	 */
	public boolean equals(Character character) {
		if (this.colour == character.colour) {
			return true;
		}
		return false;
	}

	/**
	 * A toString method for a character
	 * @return - The String representing this character
	 */
	public String toString() {
		return this.name;
	}
}
