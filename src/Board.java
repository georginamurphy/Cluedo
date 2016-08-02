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
	private static BoardPiece[][] board;

	public Board(ArrayList<Player> players) {
		this.board = new BoardPiece[25][25];
		readBoard();
		setCharacters(players);
		System.out.println(this.toString());
	}
	
	/**
	 * Read the initial board from a .txt file and fills the BoardPiece 2D array
	 * Catches file not found exception
	 * 
	 * Key:
	 * 
	 * # -Hallway
	 * X -Out of Bounds
	 * s -Start Position
	 * K -Kitchen
	 * B -Billiard Room
	 * b -Ballroom
	 * D -Dining room
	 * C -Consevertory
	 * S -Study
	 * L -Library
	 * l -Lounge
	 * H -Hall
	 * @(followed by a letter) - door to a room specified by a the letter
	 */

	private void readBoard() {
		int row = 0;
		try{
		Scanner scan = new Scanner(new File ("startBoard"));

		while (scan.hasNext()) {
			String tokens[] = scan.nextLine().split(" ");
			for (int col = 0; col <= 24; col++) {
				switch (tokens[col].trim()) {
				case "X":
					board[row][col] = null;
					break;
				case "K":
					board[row][col] = new Room(Room.Name.KITCHEN);
					break;
				case "b":
					board[row][col] = new Room(Room.Name.BALLROOM);
					break;
				case "B":
					board[row][col] = new Room(Room.Name.BILLIARD);
					break;
				case "D":
					board[row][col] = new Room(Room.Name.DININGROOM);
					break;
				case "C":
					board[row][col] = new Room(Room.Name.CONSEVERTORY);
					break;
				case "S":
					board[row][col] = new Room(Room.Name.STUDY);
					break;
				case "L":
					board[row][col] = new Room(Room.Name.LIBRARY);
					break;
				case "l":
					board[row][col] = new Room(Room.Name.LOUNGE);
					break;
				case "H":
					board[row][col] = new Room(Room.Name.HALL);
					break;
				case "s":
					board[row][col] = new Hallway(true);
					break;
				case "#":
					board[row][col] = new Hallway();
					break;
				case "@k":
					board[row][col] = new Room(Room.Name.KITCHEN, true);
					break;
				case "@D":
					board[row][col] = new Room(Room.Name.DININGROOM, true);
					break;
				case "@L":
					board[row][col] = new Room(Room.Name.LIBRARY, true);
					break;
				case "@l":
					board[row][col] = new Room(Room.Name.LOUNGE, true);
					break;
				case "@H":
					board[row][col] = new Room(Room.Name.HALL, true);
					break;
				case "@S":
					board[row][col] = new Room(Room.Name.STUDY, true);
					break;
				case "@C":
					board[row][col] = new Room(Room.Name.CONSEVERTORY, true);
					break;
				case "@b":
					board[row][col] = new Room(Room.Name.BALLROOM, true);
					break;
				case "@B":
					board[row][col] = new Room(Room.Name.BILLIARD, true);
					break;
				}
			}
			row++;
		}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
	/**
	 * puts all the characters into the board 2D array at their starting position
	 * @param characters 
	 */
	private static void setCharacters(ArrayList<Player> players) {
		for(Player p: players){
			board[p.getLocation().getY()][p.getLocation().getX()] = p ;
		}
		
	}
	
	public String toString(){
		String boardStr = "\n***************** Cluedo Board ******************\n\n";
		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				if(board[row][col] == null)
					boardStr += "X ";
				else
				boardStr += board[row][col].toString() + " ";
			}
			boardStr += "\n";
		}
		return boardStr;
	}
}
