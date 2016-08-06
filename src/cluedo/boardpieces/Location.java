package cluedo.boardpieces;
/**
 * The location class is used to help store coordinates of Pieces on the Board.
 */
public class Location {
	// The x coordinate for the position on the board
	public int x;
	
	// The y coordinate for the position on the board
	public int y;
	
	
	/**
	 * Constructor for a location object
	 * 
	 * @param x - x coordinate on the board
	 * @param y - y coordinate on the board
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter method for the x coordinate
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter method for the y coordinate
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Decrements the y coordinate, representing an upwards shift on the board.
	 */
	public void moveUp(){
		y--;
	}
	
	/**
	 * Increments the y coordinate, representing an upwards shift on the board.
	 */
	public void moveDown(){
		y++;
	}
	
	/**
	 * Decrements the x coordinate, representing an leftwards shift on the board.
	 */
	public void moveLeft(){
		x--;
	}
	
	/**
	 * Increments the x coordinate, representing an rightwards shift on the board.
	 */
	public void moveRight(){
		x++;
	}
	
	/**
	 * An equals method that compares two locations
	 * Two location objects are considered equal if they share the same x and y coordinates
	 * 
	 * @param location
	 * @return - True of equal, false otherwise
	 */
	public boolean equals(Location location){
		if(this.x == location.x){
			if(this.y == location.y){
				return true;
			}
		}
		return false;
	}
}
