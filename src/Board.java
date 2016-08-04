import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The board class represents the Cluedo Board. Has a 2D Array of BoardPieces
 * which will be the board
 * 
 */
public class Board {
	private BoardPiece[][] board;

	public Board(ArrayList<Player> players) {
		this.board = new BoardPiece[25][25];
		readBoard();
		setCharacters(players);
		// System.out.println(this.toString());
	}

	/**
	 * Read the initial board from a .txt file and fills the BoardPiece 2D array
	 * Catches file not found exception
	 * 
	 * Key:
	 * 
	 * -: Hallway #: Out of Bounds K: Kitchen B: Billiard Room b: Ballroom D:
	 * Dining room C: Conservatory S: Study L: Library l: Lounge H: Hall
	 * 
	 * @(followed by a letter) - door to a room specified by a the letter
	 */

	private void readBoard() {
		int row = 0;
		try {
			Scanner scan = new Scanner(new File("startBoard"));

			while (scan.hasNext()) {
				String tokens[] = scan.nextLine().split(" ");
				for (int col = 0; col <= 24; col++) {
					switch (tokens[col].trim()) {
					case "#":
						board[row][col] = null;
						break;
					case "K":
						board[row][col] = new RoomTile(Room.Name.KITCHEN, false);
						break;
					case "b":
						board[row][col] = new RoomTile(Room.Name.BALLROOM, false);
						break;
					case "B":
						board[row][col] = new RoomTile(Room.Name.BILLIARD, false);
						break;
					case "D":
						board[row][col] = new RoomTile(Room.Name.DININGROOM, false);
						break;
					case "C":
						board[row][col] = new RoomTile(Room.Name.CONSERVATORY, false);
						break;
					case "S":
						board[row][col] = new RoomTile(Room.Name.STUDY, false);
						break;
					case "L":
						board[row][col] = new RoomTile(Room.Name.LIBRARY, false);
						break;
					case "l":
						board[row][col] = new RoomTile(Room.Name.LOUNGE, false);
						break;
					case "H":
						board[row][col] = new RoomTile(Room.Name.HALL, false);
						break;
					case "-":
						board[row][col] = new Hallway();
						break;
					case "@K":
						board[row][col] = new RoomTile(Room.Name.KITCHEN, true);
						break;
					case "@D":
						board[row][col] = new RoomTile(Room.Name.DININGROOM, true);
						break;
					case "@L":
						board[row][col] = new RoomTile(Room.Name.LIBRARY, true);
						break;
					case "@l":
						board[row][col] = new RoomTile(Room.Name.LOUNGE, true);
						break;
					case "@H":
						board[row][col] = new RoomTile(Room.Name.HALL, true);
						break;
					case "@S":
						board[row][col] = new RoomTile(Room.Name.STUDY, true);
						break;
					case "@C":
						board[row][col] = new RoomTile(Room.Name.CONSERVATORY, true);
						break;
					case "@b":
						board[row][col] = new RoomTile(Room.Name.BALLROOM, true);
						break;
					case "@B":
						board[row][col] = new RoomTile(Room.Name.BILLIARD, true);
						break;
					}
				}
				row++;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	/**
	 * puts all the characters into the board 2D array at their starting
	 * position
	 * 
	 * @param characters
	 */
	private void setCharacters(ArrayList<Player> players) {
		for (Player p : players) {
			board[p.getLocation().getY()][p.getLocation().getX()] = p;
		}
	}



	public BoardPiece[][] getBoard() {
		return this.board;
	}

	public void updateBoard(ArrayList<Player> players){
		readBoard();
		setCharacters(players);
		
	}

	public String toString() {
		String boardStr = "\n";

		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				if (board[row][col] == null)
					boardStr += "# ";
				else
					boardStr += board[row][col].toString() + " ";
			}
			boardStr += "\n";
		}

		boardStr += "\n 1: Mrs White        2: Reverend Green   3: Mrs Peacock"
				+ "\n 4: Professor Plum   5: Miss Scarlett    6: Colonel Mustard"
				+ "\n \n #: Out of Bounds    D: Dining Room      -: Hallway\n"
				+ " B: Billiard Room    K: Kitchen          l: Lounge\n"
				+ " b: Ball Room        H: Hall             C: Conservatory\n"
				+ " S: Study            L: Library\n";

		return boardStr;
	}
}
