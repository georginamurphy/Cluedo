import java.util.Scanner;

/**
 * The board class represents the Cluedo Board.  Has a 2D Array of BoardPieces which will be the board
 * 
 */
public class Board {
	private BoardPiece[][] board;
	
	public Board(){
		this.board = new BoardPiece[25][25];
		readBoard();
	}

	private void readBoard() {
		int row = 0;
		int col = 0;
		Scanner scan = new Scanner("startBoard.txt");
		while(scan.hasNext()){
			String token = scan.next().trim();
			switch(token){
			case " ":
				break;
			case "X":
				board[row][col] = null;
			case "K":
				board[row][col] = new Room(Room.Name.KITCHEN);
			case "b":
				board[row][col] = new Room(Room.Name.BALLROOM);
			case "B":
				board[row][col] = new Room(Room.Name.BILLIARD);
			case "#":
				board[row][col] = new Hallway();
			case "D":
				board[row][col] = new Room(Room.Name.DININGROOM);
			case "C":
				board[row][col] = new Room(Room.Name.CONSEVERTORY);
			case "S":
				board[row][col] = new Room(Room.Name.STUDY);
			case "L":
				board[row][col] = new Room(Room.Name.LIBRARY);
			case "l":
				board[row][col] = new Room(Room.Name.LOUNGE);
			case "H":
				board[row][col] = new Room(Room.Name.HALL);
			}
		}
	}
}
