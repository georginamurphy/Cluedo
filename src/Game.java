import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private Board board;
	private Solution solution;
	private ArrayList<Player> players; // Consist of every player, both human
										// and non-human
	private ArrayList<Player> humanPlayers; // Consists of ONLY the human
											// players
	private boolean gameEnd;

	// Array Lists of tiles where players sit for each room
	private ArrayList<Location> kitchenTiles;
	private ArrayList<Location> ballRoomTiles;
	private ArrayList<Location> conservatoryTiles;
	private ArrayList<Location> billiardTiles;
	private ArrayList<Location> libraryTiles;
	private ArrayList<Location> studyTiles;
	private ArrayList<Location> hallTiles;
	private ArrayList<Location> loungeTiles;
	private ArrayList<Location> diningRoomTiles;

	// ArrayLists for the doors in each room, left to right and clockwise
	private ArrayList<Location> kitchenDoors;
	private ArrayList<Location> ballRoomDoors;
	private ArrayList<Location> conservatoryDoors;
	private ArrayList<Location> billiardDoors;
	private ArrayList<Location> libraryDoors;
	private ArrayList<Location> studyDoors;
	private ArrayList<Location> hallDoors;
	private ArrayList<Location> loungeDoors;
	private ArrayList<Location> diningRoomDoors;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Game(Board board, ArrayList<Player> players) {
		this.board = board;
		this.players = players;
		this.gameEnd = false;
		initialiseRoomTileLists();
		initialiseDoorLocations();
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
			if (p.getUsed()) {
				realPlayers.add(p);
				numOfPlayers++;
			}
		}
		// Set the field that holds all the human players to be the array we
		// just calculated
		this.humanPlayers = realPlayers;
		// Also, intitalize the game field for each player to be this game
		// object
		for (Player p : players) {
			p.setGame(this);
		}

		cards.addAll(chars);
		cards.addAll(weapons);
		cards.addAll(rooms);

		// Shuffle the cards
		Collections.shuffle(cards);

		// Now deal them out to the players, some players WILL have more cards
		// than others if
		// the cards dont split amongst the players evenly.
		int playerIndex = 0;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			realPlayers.get(playerIndex).dealCard(card);
			playerIndex++;
			if (!(playerIndex < numOfPlayers)) {
				playerIndex = 0;
			}
		}

		// For testing
		// for(Player p : realPlayers){
		// p.printCards();
		// }
	}

	/**
	 * The looping method that keeps the game running, player.startTurn() starts
	 * a series of methods that sequence the game.
	 */
	public void run() {
		while (!gameEnd) {
			for (Player p : humanPlayers) {
				p.startTurn();
			}
		}
	}

	/**
	 * Checks if a move is valid on the board or not.
	 * 
	 * @param player
	 *            - The player the move is being applied too
	 * @param direction
	 *            - The direction they need to move
	 * @return
	 */
	public boolean checkValidMove(Player player, Direction direction) {
		BoardPiece[][] gameBoard = this.board.getBoard();

		// Get the x and y indexes for the Player on the board
		int playerX = player.getLocation().x;
		int playerY = player.getLocation().y;

		// Depending on the direction the player wants to move, check if it is
		// valid
		if (direction == Direction.UP) {
			if (playerY == 0) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY - 1][playerX];

			if (piece instanceof Hallway) {
				return true;
			}
			if (piece instanceof RoomTile) {
				return roomTileCheck(player, direction);
			}
		} else if (direction == Direction.DOWN) {
			if (playerY == 24) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY + 1][playerX];

			if (piece instanceof Hallway) {
				return true;
			}
			if (piece instanceof RoomTile) {
				return roomTileCheck(player, direction);
			}

		} else if (direction == Direction.LEFT) {
			if (playerX == 0) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX - 1];

			if (piece instanceof Hallway) {
				return true;
			}
			if (piece instanceof RoomTile) {
				return roomTileCheck(player, direction);
			}

		} else if (direction == Direction.RIGHT) {
			if (playerX == 24) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX + 1];

			if (piece instanceof Hallway) {
				return true;
			}
			if (piece instanceof RoomTile) {
				return roomTileCheck(player, direction);
			}

		}
		return false;
	}

	/**
	 * Applies a given move to the board
	 * 
	 * @param player
	 * @param direction
	 */
	public void applyMove(Player player, Direction direction) {
		int playerX = player.getLocation().x;
		int playerY = player.getLocation().y;
		BoardPiece piece;

		switch (direction) {
		case UP:
			player.getLocation().moveUp();
			piece = this.board.getBoard()[playerY - 1][playerX];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).name;
				player.updateLocation(getRoomTile(name));
			}
			break;
		case DOWN:
			player.getLocation().moveDown();
			piece = this.board.getBoard()[playerY + 1][playerX];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).name;
				player.updateLocation(getRoomTile(name));
			}
			break;
		case RIGHT:
			player.getLocation().moveRight();
			piece = this.board.getBoard()[playerY][playerX + 1];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).name;
				player.updateLocation(getRoomTile(name));
			}
			break;
		case LEFT:
			player.getLocation().moveLeft();
			piece = this.board.getBoard()[playerY][playerX - 1];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).name;
				player.updateLocation(getRoomTile(name));
			}
			break;
		}

		board.updateBoard(players);
	}

	/**
	 * Checks if a given tile in the direction FROM the player is a room tile
	 * 
	 * @param player
	 * @param direction
	 * @return
	 */
	public boolean roomTileCheck(Player player, Direction direction) {
		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();

		BoardPiece tile = null;
		if (direction == Direction.UP) {
			tile = this.board.getBoard()[playerY - 1][playerX];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).door) {
					return true;
				}
			}
		} else if (direction == Direction.DOWN) {
			tile = this.board.getBoard()[playerY + 1][playerX];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).door) {
					return true;
				}
			}
		} else if (direction == Direction.LEFT) {
			tile = this.board.getBoard()[playerY][playerX - 1];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).door) {
					return true;
				}
			}
		} else if (direction == Direction.RIGHT) {
			tile = this.board.getBoard()[playerY][playerX + 1];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).door) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get's a tile from the given Room for the player to sit in while they are
	 * in the room
	 * 
	 * @param name
	 * @return
	 */
	public Location getRoomTile(Room.Name name) {
		switch (name) {
		case KITCHEN:
			for (int i = 0; i < this.kitchenTiles.size(); i++) {
				Location loc = this.kitchenTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case BALLROOM:
			for (int i = 0; i < this.ballRoomTiles.size(); i++) {
				Location loc = this.ballRoomTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case CONSERVATORY:
			for (int i = 0; i < this.conservatoryTiles.size(); i++) {
				Location loc = this.conservatoryTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case BILLIARD:
			for (int i = 0; i < this.billiardTiles.size(); i++) {
				Location loc = this.billiardTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case LIBRARY:
			for (int i = 0; i < this.libraryTiles.size(); i++) {
				Location loc = this.libraryTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case STUDY:
			for (int i = 0; i < this.studyTiles.size(); i++) {
				Location loc = this.studyTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case HALL:
			for (int i = 0; i < this.hallTiles.size(); i++) {
				Location loc = this.hallTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case LOUNGE:
			for (int i = 0; i < this.loungeTiles.size(); i++) {
				Location loc = this.loungeTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		case DININGROOM:
			for (int i = 0; i < this.diningRoomTiles.size(); i++) {
				Location loc = this.diningRoomTiles.get(i);
				for (Player p : this.players) {
					if (!p.getLocation().equals(loc)) {
						return loc;
					}
				}
			}
			break;
		}
		System.out.println("SOMETHING WENT VERY BAD");
		return null; // SHOULD NEVER HAPPEN
	}

	/**
	 * returns true if the player p is in a room
	 * 
	 * @param player
	 * @return boolean
	 */
	public boolean isInRoom(Player p) {
		ArrayList<ArrayList<Location>> roomTileLists = new ArrayList<ArrayList<Location>>();
		roomTileLists.add(kitchenTiles);
		roomTileLists.add(conservatoryTiles);
		roomTileLists.add(billiardTiles);
		roomTileLists.add(libraryTiles);
		roomTileLists.add(studyTiles);
		roomTileLists.add(hallTiles);
		roomTileLists.add(loungeTiles);
		roomTileLists.add(diningRoomTiles);

		for (ArrayList<Location> list : roomTileLists) {
			if (list.contains(p.getLocation() ) ){
				//System.out.println("returned true");
				return true;
			}
		}
		//System.out.println("returned false");
		return false;
	}

	public Room.Name inRoom(Player p) {
		if(kitchenTiles.contains(p))
			return Room.Name.KITCHEN;
		if(ballRoomTiles.contains(p))
			return Room.Name.BALLROOM;
		if(conservatoryTiles.contains(p))
			return Room.Name.CONSERVATORY;
		if(billiardTiles.contains(p))
			return Room.Name.BILLIARD;
		if(libraryTiles.contains(p))
			return Room.Name.LIBRARY;
		if(studyTiles.contains(p))
			return Room.Name.STUDY;
		if(hallTiles.contains(p))
			return Room.Name.HALL;
		if(loungeTiles.contains(p))
			return Room.Name.LOUNGE;
		if(diningRoomTiles.contains(p))
			return Room.Name.DININGROOM;

		System.out.println("SOMETHING WENT HORRIBLY WRONG");
		return null;
	}
	
	public Location firstFreeLocation(Location doorLocation){
		Location left = new Location(doorLocation.x - 1, doorLocation.y);
		Location right = new Location(doorLocation.x + 1, doorLocation.y);
		Location up = new Location(doorLocation.x, doorLocation.y + 1);
		Location down = new Location(doorLocation.x, doorLocation.y - 1);
		BoardPiece[][] gameBoard = this.board.getBoard();
		
		// If a door does not have a player in front of it, return the location of the hallway
		// Otherwise, a player is standing in front of the door, so return null
		if(gameBoard[left.y][left.x] instanceof Hallway){return left;}
		if(gameBoard[right.y][right.x] instanceof Hallway){return right;}
		if(gameBoard[up.y][up.x] instanceof Hallway){return up;}
		if(gameBoard[down.y][down.x] instanceof Hallway){return down;}
		return null;
	}
	
	public ArrayList<Location> getDoorLocations(Room.Name name){
		switch(name){
			case KITCHEN:
				return this.kitchenDoors;
			case BALLROOM:
				return this.ballRoomDoors;
			case CONSERVATORY:
				return this.conservatoryDoors;
			case BILLIARD:
				return this.billiardDoors;
			case LIBRARY:
				return this.libraryDoors;
			case STUDY:
				return this.studyDoors;
			case HALL:
				return this.hallDoors;
			case LOUNGE:
				return this.loungeDoors;
			case DININGROOM:
				return this.diningRoomDoors;
		}
		System.out.println("HOLY SOMEWTHING WENT REALLY WRONG");
		return null;
	}

	public boolean makeGuess() {

		return false;
	}

	/**
	 * Initializes the ArrayLists for the room tiles that players will sit in if
	 * they are in a room
	 */
	public void initialiseRoomTileLists() {
		this.kitchenTiles = new ArrayList<Location>();
		this.kitchenTiles.add(new Location(2, 2));
		this.kitchenTiles.add(new Location(3, 2));
		this.kitchenTiles.add(new Location(2, 3));
		this.kitchenTiles.add(new Location(3, 3));
		this.kitchenTiles.add(new Location(4, 2));
		this.kitchenTiles.add(new Location(4, 3));

		this.ballRoomTiles = new ArrayList<Location>();
		this.ballRoomTiles.add(new Location(11, 3));
		this.ballRoomTiles.add(new Location(12, 3));
		this.ballRoomTiles.add(new Location(13, 3));
		this.ballRoomTiles.add(new Location(11, 4));
		this.ballRoomTiles.add(new Location(12, 4));
		this.ballRoomTiles.add(new Location(13, 4));

		this.conservatoryTiles = new ArrayList<Location>();
		this.conservatoryTiles.add(new Location(21, 2));
		this.conservatoryTiles.add(new Location(22, 2));
		this.conservatoryTiles.add(new Location(21, 3));
		this.conservatoryTiles.add(new Location(22, 3));
		this.conservatoryTiles.add(new Location(21, 4));
		this.conservatoryTiles.add(new Location(22, 4));

		this.billiardTiles = new ArrayList<Location>();
		this.billiardTiles.add(new Location(21, 9));
		this.billiardTiles.add(new Location(22, 9));
		this.billiardTiles.add(new Location(21, 10));
		this.billiardTiles.add(new Location(22, 10));
		this.billiardTiles.add(new Location(21, 11));
		this.billiardTiles.add(new Location(22, 11));

		this.libraryTiles = new ArrayList<Location>();
		this.libraryTiles.add(new Location(19, 16));
		this.libraryTiles.add(new Location(20, 16));
		this.libraryTiles.add(new Location(21, 16));
		this.libraryTiles.add(new Location(19, 17));
		this.libraryTiles.add(new Location(20, 17));
		this.libraryTiles.add(new Location(21, 17));

		this.studyTiles = new ArrayList<Location>();
		this.studyTiles.add(new Location(19, 22));
		this.studyTiles.add(new Location(20, 22));
		this.studyTiles.add(new Location(21, 22));
		this.studyTiles.add(new Location(19, 23));
		this.studyTiles.add(new Location(20, 23));
		this.studyTiles.add(new Location(21, 23));

		this.hallTiles = new ArrayList<Location>();
		this.hallTiles.add(new Location(10, 20));
		this.hallTiles.add(new Location(11, 20));
		this.hallTiles.add(new Location(12, 20));
		this.hallTiles.add(new Location(10, 21));
		this.hallTiles.add(new Location(11, 21));
		this.hallTiles.add(new Location(12, 21));

		this.loungeTiles = new ArrayList<Location>();
		this.loungeTiles.add(new Location(2, 21));
		this.loungeTiles.add(new Location(3, 21));
		this.loungeTiles.add(new Location(4, 21));
		this.loungeTiles.add(new Location(2, 22));
		this.loungeTiles.add(new Location(3, 22));
		this.loungeTiles.add(new Location(4, 22));

		this.diningRoomTiles = new ArrayList<Location>();
		this.diningRoomTiles.add(new Location(3, 11));
		this.diningRoomTiles.add(new Location(4, 11));
		this.diningRoomTiles.add(new Location(3, 12));
		this.diningRoomTiles.add(new Location(4, 12));
		this.diningRoomTiles.add(new Location(5, 11));
		this.diningRoomTiles.add(new Location(5, 12));
	}
	
	/**
	 * Initializes the arrays of doors for each room.
	 * Doors will be listed from top to bottom, left to right
	 */
	public void initialiseDoorLocations(){
		this.kitchenDoors = new ArrayList<Location>();
		this.ballRoomDoors = new ArrayList<Location>();
		this.conservatoryDoors = new ArrayList<Location>();
		this.billiardDoors = new ArrayList<Location>();
		this.libraryDoors = new ArrayList<Location>();
		this.studyDoors = new ArrayList<Location>();
		this.hallDoors = new ArrayList<Location>();
		this.loungeDoors = new ArrayList<Location>();
		this.diningRoomDoors = new ArrayList<Location>();
		//Initialise some arrays to use in the method
		BoardPiece[][] gameBoard = this.board.getBoard();
		ArrayList<RoomTile> doors = new ArrayList<RoomTile>();
		
		// Loop through every position on the board, adding it to doors if it is a roomTile representing a door
		for(int y = 0; y < gameBoard.length; y++){
			for(int x = 0; x < gameBoard[0].length; x++){
				//Get the piece at the [y][x] position
				BoardPiece piece = gameBoard[y][x];
				
				// If it is a RoomTile and represents a door, add it's location to the respective array
				if(piece instanceof RoomTile){
					if(((RoomTile) piece).door){
						Room.Name name = ((RoomTile) piece).name;
						switch(name){
							case KITCHEN:
								kitchenDoors.add(new Location(x, y) );
							case BALLROOM:
								ballRoomDoors.add(new Location(x, y) );
							case CONSERVATORY:
								conservatoryDoors.add(new Location(x, y) );
							case BILLIARD:
								billiardDoors.add(new Location(x, y) );
							case LIBRARY:
								libraryDoors.add(new Location(x, y) );
							case STUDY:
								studyDoors.add(new Location(x, y) );
							case HALL:
								hallDoors.add(new Location(x, y) );
							case LOUNGE:
								loungeDoors.add(new Location(x, y) );
							case DININGROOM:
								diningRoomDoors.add(new Location(x, y) );
						}
					}
				}
			}
		}
	}

	/**
	 * Prints the board
	 */
	public void printBoard() {
		System.out.println(this.board.toString());
	}
}
