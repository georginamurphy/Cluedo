package cluedo.boardpieces;
import javax.swing.ImageIcon;

import cluedo.cards.Room;

/**
 * A RoomTile is a piece on the board that represents part of a room.
 * A RoomTile can either represent a door to the room, or a wall / floor.
 * 
 * Players may only pass through a RoomTile if it represents a door.
 */
public class RoomTile implements BoardPiece {
	ImageIcon roomTile = new ImageIcon("grey.png");
	
	// The name of the room this RoomTile belongs to
	private Room.Name name;
	
	// A boolean representing if this this RoomTile is a door or not
	private boolean door;

	/**
	 * A constructor for a RoomTile
	 * 
	 * @param name - Name of the Room this RoomTile belongs to
	 * @param door - True if the RoomTile is a door, false otherwise
	 */
	public RoomTile(Room.Name name, boolean door) {
		this.name = name;
		this.door = door;
	}

	/**
	 * Getter for this RoomTile's name
	 * @return - The name of the room this RoomTile belongs to
	 */
	public Room.Name getName(){
		return this.name;
	}
	
	/**
	 * Getter method for the field door
	 * @return - True if this RoomTile is a door, false otherwise
	 */
	public boolean isDoor(){
		return this.door;
	}
	
	@Override
	public ImageIcon getImageIcon() {
		return roomTile;
	}
	
	/**
	 * A toString method for a RoomTile
	 */
	public String toString() {
		if(this.door){return "@";}
		
		switch (this.name) {
		case KITCHEN:
			return "K";
		case BALLROOM:
			return "b";
		case BILLIARD:
			return "B"; 
		case DININGROOM:
			return "D";
		case CONSERVATORY:
			return "C";
		case STUDY:
			return "S";
		case LIBRARY:
			return "L";
		case LOUNGE:
			return "l";
		case HALL:
			return "H";
		}
		return ""; // Should never happen
	}

	
}
