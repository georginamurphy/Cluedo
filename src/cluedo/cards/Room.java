package cluedo.cards;

import javax.swing.ImageIcon;

/**
 * A class that represents a Room in the game.
 * Holds basic information about a room card
 */
public class Room implements Card {
	
	// The name for this Room Card
	public Name name;
	
	/**
	 * A enum that represent the names of the different Rooms in Cluedo
	 */
	public enum Name{ 
		KITCHEN,
		BALLROOM,
		CONSERVATORY,
		BILLIARD,
		LIBRARY,
		STUDY,
		HALL,
		LOUNGE,
		DININGROOM;
	}
	/**
	 * A constructor for a Room card that accepts a Room.Name enum
	 * @param name - The name for this room card
	 */
	public Room(Name name){
		this.name = name;
	}
	
	/**
	 * A method determine equality between two Room cards
	 * Two rooms are considered equal if they share the same Room.Name
	 * 
	 * @param room - The card we are comparing this Room object to.
	 * @return
	 */
	public boolean equals(Room room){
		if(this.name == room.name){return true;}
		return false;
	}
	
	/**
	 * Returns the ImageIcon associated with this Room
	 */
	public ImageIcon getImageIcon(){
		switch(name){
		case KITCHEN:
			return new ImageIcon("Kitchen.png");
		case BALLROOM:
			return new ImageIcon("Ballroom.png");
		case CONSERVATORY:
			return new ImageIcon("Conservatory.png");
		case BILLIARD:
			return new ImageIcon("BilliardRoom.png");
		case LIBRARY:
			return new ImageIcon("Library.png");
		case STUDY:
			return new ImageIcon("Study.png");
		case HALL:
			return new ImageIcon("Hall.png");
		case LOUNGE:
			return new ImageIcon("Lounge.png");
		case DININGROOM:
			return new ImageIcon("DiningRoom.png");
		default:
			return null; // shouldn't happen
		}
	}

	/**
	 * A toString method for a Room card
	 */
	public String toString(){
		return this.name.toString();
	}
}