import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private Board board;
	private Solution solution;
	private ArrayList<Player> players; // Consist of every player, both human and non-human
	private ArrayList<Player> humanPlayers; // Consists of ONLY the human players
	private boolean gameEnd;
	
	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	public Game(Board board, ArrayList<Player> players) {
		this.board = board;
		this.players = players;
		this.gameEnd = false;
	}

	/**
	 * Takes 3 ArrayLists, one of each of the following types: Character, Weapon
	 * and Room Will select one random Object from each Array and use it to form
	 * the Solution for the game. Then stores that Solution in the private field
	 * 'solution'.
	 * 
	 * After storing the solution, it will then remove those 3 objects that were
	 * used from each of their respective ArrayLists.
	 * 
	 * @param chars
	 * @param weapons
	 * @param rooms
	 */
	public void createSolution(ArrayList<Character> chars, ArrayList<Weapon> weapons, ArrayList<Room> rooms) {
		int charIndex = (int) Math.random() * chars.size();
		int weaponIndex = (int) Math.random() * weapons.size();
		int roomIndex = (int) Math.random() * rooms.size();

		this.solution = new Solution(weapons.get(weaponIndex), chars.get(charIndex), rooms.get(roomIndex));

		chars.remove(charIndex);
		weapons.remove(weaponIndex);
		rooms.remove(roomIndex);
	}

	/**
	 * Takes 3 ArrayLists, one of each of the following types: Character, Weapon
	 * and Room
	 * 
	 * @param chars
	 * @param weapons
	 * @param rooms
	 */
	public void dealCards(ArrayList<Character> chars, ArrayList<Weapon> weapons, ArrayList<Room> rooms) {
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Player> realPlayers = new ArrayList<Player>();
		// Get the real players, and add them to an ArrayList
		int numOfPlayers = 0;
		for (Player p : players) {
			if(p.getUsed() ){
				realPlayers.add(p);
				numOfPlayers++;
			}
		}
		// Set the field that holds all the human players to be the array we just calculated
		this.humanPlayers = realPlayers;
		// Also, intitalize the game field for each player to be this game object
		for(Player p : players){
			p.setGame(this);
		}
		
		cards.addAll(chars);
		cards.addAll(weapons);
		cards.addAll(rooms);

		// Shuffle the cards
		Collections.shuffle(cards);
		
		// Now deal them out to the players, some players WILL have more cards than others if 
		// the cards dont split amongst the players evenly.
		int playerIndex = 0;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			realPlayers.get(playerIndex).dealCard(card);
			playerIndex++;
			if(!(playerIndex < numOfPlayers) ){
				playerIndex = 0;
			}
		}
		
		// For testing
		//for(Player p : realPlayers){
		//	p.printCards();
		//}
	}
	
	public void run(){
		while(!gameEnd){
			for(Player p : humanPlayers){
				p.startTurn();
			}
		}
	}
	
	public boolean checkValidMove(Player player, Direction direction){
		BoardPiece[][] gameBoard = this.board.getBoard();
		
		// Get the x and y indexes for the Player on the board
		int playerX = player.getLocation().x;
		int playerY = player.getLocation().y;
		
		// Depending on the direction the player wants to move, check if it is valid
		if(direction == Direction.UP ){
			if(playerY == 0){return false;} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY - 1][playerX];
			
			if(piece instanceof Hallway){return true ;}
			if(piece instanceof RoomTile){return roomTileCheck(player, direction);}
		}
		else if(direction == Direction.DOWN){
			if(playerY == 24){return false;} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY + 1][playerX];
			
			if(piece instanceof Hallway){return true;}
			if(piece instanceof RoomTile){return roomTileCheck(player, direction);}
			
		}
		else if(direction == Direction.LEFT){
			if(playerX == 0){return false;} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX - 1];
			
			if(piece instanceof Hallway){return true;}
			if(piece instanceof RoomTile){return roomTileCheck(player, direction);}
			
		}
		else if(direction == Direction.RIGHT){
			if(playerX == 24){return false;} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX + 1];
			
			if(piece instanceof Hallway){return true;}
			if(piece instanceof RoomTile){return roomTileCheck(player, direction);}
	
		}
		return false;
	}
	
	public void applyMove(Player player, Direction direction){
		switch(direction){
			case UP:
				player.getLocation().moveUp();
				break;
			case DOWN:
				player.getLocation().moveDown();
				break;
			case RIGHT:
				player.getLocation().moveRight();
				break;
			case LEFT:
				player.getLocation().moveLeft();
				break;
		}

		board.updateBoard(players);
	}
	
	public boolean roomTileCheck(Player player, Direction direction){
		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();
		
		BoardPiece tile = null;
		if(direction == Direction.UP){
			tile = this.board.getBoard()[playerY - 1][playerX];
			if(tile instanceof RoomTile){
				if( ( (RoomTile) tile).door){return true;}
			}
		}
		else if(direction == Direction.DOWN){
			tile = this.board.getBoard()[playerY + 1][playerX];
			if(tile instanceof RoomTile){
				if( ( (RoomTile) tile).door){return true;}
			}
		}
		else if(direction == Direction.LEFT){
			tile = this.board.getBoard()[playerY][playerX - 1];
			if(tile instanceof RoomTile){
				if( ( (RoomTile) tile).door){return true;}
			}
		}
		else if(direction == Direction.RIGHT){
			tile = this.board.getBoard()[playerY][playerX + 1];
			if(tile instanceof RoomTile){
				if( ( (RoomTile) tile).door){return true;}
			}
		}
		return false;
	}
	
	public void printBoard(){
		System.out.println(this.board.toString());
	}
}
