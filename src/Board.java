import java.util.Scanner;

/**
 * The board class represents the Cluedo Board. Has a 2D Array of BoardPieces
 * which will be the board
 * 
 */
public class Board {
	private BoardPiece[][] board;

	public Board() {
		this.board = new BoardPiece[25][25];
		readBoard();
	}

	private void readBoard() {
		int col = 0;
		Scanner scan = new Scanner("startBoard.txt");

		while (scan.hasNext()) {
			String tokens[] = scan.nextLine().split(" ");
			for (int row = 0; row < 24; row++) {
				switch (tokens[row].trim()) {
				case "X":
					board[row][col] = null;
				case "K":
					board[row][col] = new Room(Room.Name.KITCHEN);
				case "b":
					board[row][col] = new Room(Room.Name.BALLROOM);
				case "B":
					board[row][col] = new Room(Room.Name.BILLIARD);
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
				case "s":
					board[row][col] = new Hallway();
				case "#":
					board[row][col] = new Hallway();
				case "@k":
					board[row][col] = new Room(Room.Name.KITCHEN, true);
				case "@d":
					board[row][col] = new Room(Room.Name.DININGROOM, true);
				case "@L":
					board[row][col] = new Room(Room.Name.LIBRARY, true);
				case "@l":
					board[row][col] = new Room(Room.Name.LOUNGE, true);
				case "@H":
					board[row][col] = new Room(Room.Name.HALL, true);
				case "@S":
					board[row][col] = new Room(Room.Name.STUDY, true);
				case "@C":
					board[row][col] = new Room(Room.Name.CONSEVERTORY, true);
				case "@b":
					board[row][col] = new Room(Room.Name.BALLROOM, true);
				case "@B":
					board[row][col] = new Room(Room.Name.BILLIARD, true);
				}
			}
			col++;
		}
	}
}
