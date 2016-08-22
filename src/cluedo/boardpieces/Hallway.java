package cluedo.boardpieces;

import javax.swing.ImageIcon;

/**
 * A Hallway represents a square on the board that Player's are free to move into,
 * and are how they traverse across the board to different Rooms.
 */
public class Hallway implements BoardPiece {
	
	// The image icon representing a hallway
	ImageIcon hallwayTile = new ImageIcon("floorTile.png");
	
	/**
	 * A constructor for a hallway
	 */
	public Hallway(){
		
	}
	
	@Override
	public ImageIcon getImageIcon() {
		return hallwayTile;
	}
	
	/**
	 * A toString method for a hallway
	 * @return - The String representing a Hallway
	 */
	public String toString(){
		return "-";
	}
	
}
