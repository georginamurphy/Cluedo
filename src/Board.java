 /**
 * The board class represents the Cluedo Board.  Has a 2D Array of BoardPieces which will be the board
 * 
 */
public class Board {
	private Location[][] board;
	
	public Board(){
		this.board = new Location[25][25];
	}
}
