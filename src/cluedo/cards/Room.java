package cluedo.cards;
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
	 * A toString method for a Room card
	 */
	public String toString(){
		return this.name.toString();
	}
}
