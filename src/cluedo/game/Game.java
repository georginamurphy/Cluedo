package cluedo.game;

import java.util.ArrayList;
import java.util.Collections;

import cluedo.boardpieces.BoardPiece;
import cluedo.boardpieces.Hallway;
import cluedo.boardpieces.Location;
import cluedo.boardpieces.Player;
import cluedo.boardpieces.RoomTile;
import cluedo.cards.Card;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.cards.Weapon;
import cluedo.controller.CluedoController;

/**
 * The game class is what ties everything together and holds a lot of game
 * logic, and information about the board. Player's have a reference to an
 * instance of this game so that they can access information about the board.
 */
public class Game {

	// The controller object for the MVC Model
	public CluedoController controller;

	// The board object for this game
	private Board board;

	// The solution to this game
	private Solution solution;

	// An ArrayList oh players in the game, including non-humans
	private ArrayList<Player> players;

	// An ArrayList of only human players
	public ArrayList<Player> humanPlayers;

	// A boolean to catch when the game ends, and the loop in run() should
	// terminate
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

	// An enum representing a direction on the board
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	/**
	 * The constructor for a game, takes a board and an ArrayList of players
	 * 
	 * @param board
	 *            - The board for the game
	 * @param players
	 *            - The list of all players
	 * @param gui
	 */
	public Game(Board board, ArrayList<Player> humanPlayers, ArrayList<Player> players, CluedoController controller) {
		this.controller = controller;
		this.board = board;
		this.players = players;
		setHumanPlayers(humanPlayers);
		this.gameEnd = false;

		// These two method calls initialize information about the board that
		// never changes between games
		// The information helps when moving player's into and out of rooms
		initialiseRoomTileLists();
		initialiseDoorLocations();
	}

	/**
	 * Getter for the board field
	 * 
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Getter for the solution
	 * 
	 * @return
	 */
	public Solution getSolution() {
		return this.solution;
	}

	/**
	 * Getter for the gameEnd field
	 * 
	 * @return
	 */
	public boolean getGameEnd() {
		return this.gameEnd;
	}

	/**
	 * Setter for the humanPlayers field
	 */
	public void setHumanPlayers(ArrayList<Player> humanPlayers) {
		this.humanPlayers = humanPlayers;
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

		// Get random indexes in each ArrayList
		int charIndex = (int) (Math.random() * chars.size());
		int weaponIndex = (int) (Math.random() * weapons.size());
		int roomIndex = (int) (Math.random() * rooms.size());

		this.solution = new Solution(weapons.get(weaponIndex), chars.get(charIndex), rooms.get(roomIndex));
		// Remove the cards from the ArrayLists so they are not dealt to players
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

		// Also, initialize the game field for each player to be this game
		// object
		for (Player p : players) {
			p.setGame(this);
		}

		// Place all cards into a single ArrayList and shuffle it
		cards.addAll(chars);
		cards.addAll(weapons);
		cards.addAll(rooms);
		Collections.shuffle(cards);

		// Now deal them out to the players, some players WILL have more cards
		// than others if the cards don't split amongst the players evenly.
		int playerIndex = 0;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			humanPlayers.get(playerIndex).dealCard(card);
			playerIndex++;
			if (!(playerIndex < controller.getNumPlayers() )) {
				playerIndex = 0;
			}
		}
	}

	/**
	 * A simple method that will generate two values between 1 and 6, inclusive
	 * and return their sum.
	 */
	public int rollDice() {
		int roll = (int) (Math.random() * 6) + 1;
		int roll2 = (int) (Math.random() * 6) + 1;
		return roll + roll2;
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

		// A series of 4 if statements will catch what direction we are needing
		// to check
		// Appropriate checks will then be made that apply to the piece in that
		// direction from the player

		if (direction == Direction.UP) {
			if (playerY == 0) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY - 1][playerX];

			if (piece instanceof Hallway) {
				return true;
			} // The tile in the given direction is available to move into

			// If the piece is a RoomTile, they may only move onto it if it is a
			// door that does not belong to the room they were in last turn
			if (piece instanceof RoomTile) {
				if (piece != null) {
					if (((RoomTile) piece).getName().equals(player.getRoomLastTurn())) {
						return false;
					} else {
						return isRoomDoor(player, direction);
					}
				}
			}
		}

		else if (direction == Direction.DOWN) {
			if (playerY == 24) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY + 1][playerX];

			if (piece instanceof Hallway) {
				return true;
			} // The tile in the given direction is available to move into

			// If the piece is a RoomTile, they may only move onto it if it is a
			// door that does not belong to the room they were in last turn
			if (piece instanceof RoomTile) {
				if (piece != null) {
					if (((RoomTile) piece).getName().equals(player.getRoomLastTurn())) {
						return false;
					} else {
						return isRoomDoor(player, direction);
					}
				}
			}
		}

		else if (direction == Direction.LEFT) {
			if (playerX == 0) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX - 1];

			if (piece instanceof Hallway) {
				return true;
			}

			// If the piece is a RoomTile, they may only move onto it if it is a
			// door that does not belong to the room they were in last turn
			if (piece instanceof RoomTile) {
				if (piece != null) {
					if (((RoomTile) piece).getName().equals(player.getRoomLastTurn())) {
						return false;
					} else {
						return isRoomDoor(player, direction);
					}
				}
			}
		}

		else if (direction == Direction.RIGHT) {
			if (playerX == 24) {
				return false;
			} // Player can't move off the board
			BoardPiece piece = gameBoard[playerY][playerX + 1];

			if (piece instanceof Hallway) {
				return true;
			}

			// If the piece is a RoomTile, they may only move onto it if it is a
			// door that does not belong to the room they were in last turn
			if (piece instanceof RoomTile) {
				if (piece != null) {
					if (((RoomTile) piece).getName().equals(player.getRoomLastTurn())) {
						return false;
					} else {
						return isRoomDoor(player, direction);
					}
				}
			}
		}
		return false;
	}

	/**
	 * Applies a given move to the board
	 * 
	 * @param player
	 *            - The player to apply the move to
	 * @param direction
	 *            - The direction a player must move
	 */
	public void applyMove(Player player, Direction direction) {
		int playerX = player.getLocation().x;
		int playerY = player.getLocation().y;
		BoardPiece piece;

		// Given a Direction the player is moving in, it will shift the player
		// on the board
		// It detects if the player has just moved onto a door, meaning they now
		// need to be
		// moved to one of the designated locations within the room for players
		// to sit
		// The 2D Array that represents the board is only updated at the end of
		// this method

		// The first case's comment apply to the remaining cases as well

		switch (direction) {
		case UP:
			// Shift the player's location in the appropriate direction, note
			// the board is not physically updated yet
			player.getLocation().moveUp();

			// If the player moved onto a RoomTile that is a door, update their
			// location to a designated spot in the room
			piece = this.board.getBoard()[playerY - 1][playerX];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).getName();
				player.updateLocation(getRoomTile(name));
			}
			break;
		case DOWN:
			player.getLocation().moveDown();
			piece = this.board.getBoard()[playerY + 1][playerX];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).getName();
				player.updateLocation(getRoomTile(name));
			}
			break;
		case RIGHT:
			player.getLocation().moveRight();
			piece = this.board.getBoard()[playerY][playerX + 1];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).getName();
				player.updateLocation(getRoomTile(name));
			}
			break;
		case LEFT:
			player.getLocation().moveLeft();
			piece = this.board.getBoard()[playerY][playerX - 1];
			if (piece instanceof RoomTile) {
				Room.Name name = ((RoomTile) piece).getName();
				player.updateLocation(getRoomTile(name));
			}
			break;
		}

		board.updateBoard(players); // Physically updates the board
		controller.drawBoard();
	}

	/**
	 * Checks if a given tile in the direction FROM the player is a room tile
	 * that represents a door
	 * 
	 * @param player
	 *            - The player we are checking for a door from
	 * @param direction
	 *            - The direction from the player where we want to check
	 * @return
	 */
	public boolean isRoomDoor(Player player, Direction direction) {
		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();

		BoardPiece tile = null;
		if (direction == Direction.UP) {
			tile = this.board.getBoard()[playerY - 1][playerX];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).isDoor()) {
					return true;
				}
			}
		} else if (direction == Direction.DOWN) {
			tile = this.board.getBoard()[playerY + 1][playerX];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).isDoor()) {
					return true;
				}
			}
		} else if (direction == Direction.LEFT) {
			tile = this.board.getBoard()[playerY][playerX - 1];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).isDoor()) {
					return true;
				}
			}
		} else if (direction == Direction.RIGHT) {
			tile = this.board.getBoard()[playerY][playerX + 1];
			if (tile instanceof RoomTile) {
				if (((RoomTile) tile).isDoor()) {
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
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case BALLROOM:
			for (int i = 0; i < this.ballRoomTiles.size(); i++) {
				Location loc = this.ballRoomTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case CONSERVATORY:
			for (int i = 0; i < this.conservatoryTiles.size(); i++) {
				Location loc = this.conservatoryTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case BILLIARD:
			for (int i = 0; i < this.billiardTiles.size(); i++) {
				Location loc = this.billiardTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case LIBRARY:
			for (int i = 0; i < this.libraryTiles.size(); i++) {
				Location loc = this.libraryTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case STUDY:
			for (int i = 0; i < this.studyTiles.size(); i++) {
				Location loc = this.studyTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case HALL:
			for (int i = 0; i < this.hallTiles.size(); i++) {
				Location loc = this.hallTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case LOUNGE:
			for (int i = 0; i < this.loungeTiles.size(); i++) {
				Location loc = this.loungeTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		case DININGROOM:
			for (int i = 0; i < this.diningRoomTiles.size(); i++) {
				Location loc = this.diningRoomTiles.get(i);
				if (!(this.board.getBoard()[loc.y][loc.x] instanceof Player)) {
					return loc;
				}
			}
			break;
		}
		return null; // This should never happen
	}

	/**
	 * Returns true if the player p is in a room
	 * 
	 * @param player
	 * @return boolean
	 */
	public boolean isInRoom(Player p) {
		ArrayList<ArrayList<Location>> roomTileLists = new ArrayList<ArrayList<Location>>();
		roomTileLists.add(kitchenTiles);
		roomTileLists.add(ballRoomTiles);
		roomTileLists.add(conservatoryTiles);
		roomTileLists.add(billiardTiles);
		roomTileLists.add(libraryTiles);
		roomTileLists.add(studyTiles);
		roomTileLists.add(hallTiles);
		roomTileLists.add(loungeTiles);
		roomTileLists.add(diningRoomTiles);

		// Will return true if a player's location is equal to one of
		// the 6 designated spots in each room for players to sit in.
		for (ArrayList<Location> list : roomTileLists) {
			if (list.contains(p.getLocation())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return's the name of a room that a player sit's in
	 * 
	 * @param p
	 * @return
	 */
	public Room.Name inRoom(Player p) {
		if (kitchenTiles.contains(p.getLocation()))
			return Room.Name.KITCHEN;
		if (ballRoomTiles.contains(p.getLocation()))
			return Room.Name.BALLROOM;
		if (conservatoryTiles.contains(p.getLocation()))
			return Room.Name.CONSERVATORY;
		if (billiardTiles.contains(p.getLocation()))
			return Room.Name.BILLIARD;
		if (libraryTiles.contains(p.getLocation()))
			return Room.Name.LIBRARY;
		if (studyTiles.contains(p.getLocation()))
			return Room.Name.STUDY;
		if (hallTiles.contains(p.getLocation()))
			return Room.Name.HALL;
		if (loungeTiles.contains(p.getLocation()))
			return Room.Name.LOUNGE;
		if (diningRoomTiles.contains(p.getLocation()))
			return Room.Name.DININGROOM;

		return null; // Should never do this
	}

	/**
	 * Checks whether a room has a free door to exit from
	 * 
	 * @param name
	 *            - The name of the room we wish to inspect
	 * @return
	 */
	public boolean hasFreeDoor(Room.Name name) {
		switch (name) {
		case KITCHEN:
			for (Location l : kitchenDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case BALLROOM:
			for (Location l : ballRoomDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case CONSERVATORY:
			for (Location l : conservatoryDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case BILLIARD:
			for (Location l : billiardDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case LIBRARY:
			for (Location l : libraryDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case STUDY:
			for (Location l : studyDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case HALL:
			for (Location l : hallDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case LOUNGE:
			for (Location l : loungeDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		case DININGROOM:
			for (Location l : diningRoomDoors) {
				if (isFreeDoor(l) != null) {
					return true;
				}
			}
			return false;
		}
		return false; // Should not get here
	}

	/**
	 * Will check if the given door is not blocked by a player and can be moved
	 * through
	 * 
	 * @param doorLocation
	 * @return
	 */
	public Location isFreeDoor(Location doorLocation) {
		Location left = new Location(doorLocation.x - 1, doorLocation.y);
		Location right = new Location(doorLocation.x + 1, doorLocation.y);
		Location up = new Location(doorLocation.x, doorLocation.y - 1);
		Location down = new Location(doorLocation.x, doorLocation.y + 1);
		BoardPiece[][] gameBoard = this.board.getBoard();

		// If a door does not have a player in front of it, return the location
		// of the hallway
		// Otherwise, a player is standing in front of the door, so return null
		if (gameBoard[left.y][left.x] instanceof Hallway) {
			return doorLocation;
		}
		if (gameBoard[right.y][right.x] instanceof Hallway) {
			return doorLocation;
		}
		if (gameBoard[up.y][up.x] instanceof Hallway) {
			return doorLocation;
		}
		if (gameBoard[down.y][down.x] instanceof Hallway) {
			return doorLocation;
		}
		return null;
	}

	/**
	 * Returns the ArrayList of doors for a given room
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Location> getDoorLocations(Room.Name name) {
		switch (name) {
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
		return null; // Should never return null
	}
	
	/**
	 * Returns a character color given an String
	 * 
	 * @param num
	 * @return
	 */
	public Character.Colour getCharacterColour(String rm) {
		switch (rm) {
		case "Mrs White":
			return Character.Colour.WHITE;
		case "Reverend Green":
			return Character.Colour.GREEN;
		case "Mrs Peacock":
			return Character.Colour.BLUE;
		case "Professor Plum":
			return Character.Colour.PURPLE;
		case "Miss Scarlett":
			return Character.Colour.RED;
		case "Colonel Mustard":
			return Character.Colour.YELLOW;
		}
		return null;
	}

	/**
	 * Returns a character color given an integer
	 * 
	 * @param num
	 * @return
	 */
	public Character.Colour getCharacterColour(int num) {
		switch (num) {
		case 1:
			return Character.Colour.WHITE;
		case 2:
			return Character.Colour.GREEN;
		case 3:
			return Character.Colour.BLUE;
		case 4:
			return Character.Colour.PURPLE;
		case 5:
			return Character.Colour.RED;
		case 6:
			return Character.Colour.YELLOW;
		}
		return null;
	}

	/**
	 * Returns a room name given an integer
	 * 
	 * @param num
	 * @return
	 */
	public Room.Name getRoomName(int num) {
		switch (num) {
		case 1:
			return Room.Name.KITCHEN;
		case 2:
			return Room.Name.BALLROOM;
		case 3:
			return Room.Name.CONSERVATORY;
		case 4:
			return Room.Name.DININGROOM;
		case 5:
			return Room.Name.BILLIARD;
		case 6:
			return Room.Name.LIBRARY;
		case 7:
			return Room.Name.LOUNGE;
		case 8:
			return Room.Name.HALL;
		case 9:
			return Room.Name.STUDY;
		}
		return null;
	}
	
	/**
	 * Returns a room name given an String
	 * 
	 * @param num
	 * @return
	 */
	public Room.Name getRoomName(String rm) {
		switch (rm) {
		case "KITCHEN":
			return Room.Name.KITCHEN;
		case "BALLROOM":
			return Room.Name.BALLROOM;
		case "CONSERVATORY":
			return Room.Name.CONSERVATORY;
		case "DININGROOM":
			return Room.Name.DININGROOM;
		case "BILLIARD":
			return Room.Name.BILLIARD;
		case "LIBRARY":
			return Room.Name.LIBRARY;
		case "LOUNGE":
			return Room.Name.LOUNGE;
		case "HALL":
			return Room.Name.HALL;
		case "STUDY":
			return Room.Name.STUDY;
		}
		return null;
	}
	
	/**
	 * Returns a weapon name given an string
	 * 
	 * @param num
	 * @return
	 */
	public Weapon.Type getWeaponName(String rm) {
		switch (rm) {
		case "CANDLESTICK":
			return Weapon.Type.CANDLESTICK;
		case "DAGGER":
			return Weapon.Type.DAGGER;
		case "LEADPIPE":
			return Weapon.Type.LEADPIPE;
		case "ROPE":
			return Weapon.Type.ROPE;
		case "SPANNER":
			return Weapon.Type.SPANNER;
		case "REVOLVER":
			return Weapon.Type.REVOLVER;
		}
		return null;
	}

	/**
	 * Returns a weapon name given an integer
	 * 
	 * @param num
	 * @return
	 */
	public Weapon.Type getWeaponName(int num) {
		switch (num) {
		case 1:
			return Weapon.Type.CANDLESTICK;
		case 2:
			return Weapon.Type.DAGGER;
		case 3:
			return Weapon.Type.LEADPIPE;
		case 4:
			return Weapon.Type.ROPE;
		case 5:
			return Weapon.Type.SPANNER;
		case 6:
			return Weapon.Type.REVOLVER;
		}
		return null;
	}

	/**
	 * Checks the users guess against all the other players cards and shows them
	 * the first one it finds. If none of the cards are found the player is told
	 * no matching cards were found
	 * 
	 * @param player
	 *            - player making the suggestion
	 * @param input
	 */
	public Card makeSuggestion(Player player, Solution guess) {
		for (Player p : controller.getHumanPlayers() ) {
			if (!p.equals(player)) {
				for (Card c : p.getCards()) {
					if (guess.checkCard(c)) {
						return c;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Checks a guess against this game's solution and returns the result Will
	 * also set the gameEnd field to true if the solution was correct Will
	 * remove the player from the game if their guess was incorrect
	 * 
	 * @param guess
	 * @param p
	 * @return
	 */
	public boolean checkSolution(Solution guess, Player p) {
		if (guess.equals(solution)) {
			gameEnd = true;
			return true;
		} else {
			p.removeFromGame();
			return false;
		}
	}
	
	/**
	 * Moves a player to a room
	 * @param p - player to move
	 * @param toMoveTo - room to move to
	 */
	public void movePlayerToRoom(Player p, Room.Name toMoveTo){
		p.updateLocation(this.getRoomTile(toMoveTo) );
		this.board.updateBoard(controller.getAllPlayers() );
		
	}

	// ===================================================================================================================================================================
	//
	// The methods beyond this point are to initialize specific information
	// about the board that does not change between games.
	// They provide information that is mainly used when moving players to and
	// from the different rooms in the game.
	//
	// ===================================================================================================================================================================

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
	 * Initializes the arrays of doors for each room. Doors will be listed from
	 * top to bottom, left to right
	 */
	public void initialiseDoorLocations() {
		this.kitchenDoors = new ArrayList<Location>();
		this.ballRoomDoors = new ArrayList<Location>();
		this.conservatoryDoors = new ArrayList<Location>();
		this.billiardDoors = new ArrayList<Location>();
		this.libraryDoors = new ArrayList<Location>();
		this.studyDoors = new ArrayList<Location>();
		this.hallDoors = new ArrayList<Location>();
		this.loungeDoors = new ArrayList<Location>();
		this.diningRoomDoors = new ArrayList<Location>();
		// Initialise some arrays to use in the method
		BoardPiece[][] gameBoard = this.board.getBoard();
		// ArrayList<RoomTile> doors = new ArrayList<RoomTile>();

		// Loop through every position on the board, adding it to doors if it is
		// a roomTile representing a door
		for (int y = 0; y < gameBoard.length; y++) {
			for (int x = 0; x < gameBoard[0].length; x++) {
				// Get the piece at the [y][x] position
				BoardPiece piece = gameBoard[y][x];

				// If it is a RoomTile and represents a door, add it's location
				// to the respective array
				if (piece instanceof RoomTile) {
					if (((RoomTile) piece).isDoor()) {
						Room.Name name = ((RoomTile) piece).getName();
						switch (name) {
						case KITCHEN:
							kitchenDoors.add(new Location(x, y));
							break;
						case BALLROOM:
							ballRoomDoors.add(new Location(x, y));
							break;
						case CONSERVATORY:
							conservatoryDoors.add(new Location(x, y));
							break;
						case BILLIARD:
							billiardDoors.add(new Location(x, y));
							break;
						case LIBRARY:
							libraryDoors.add(new Location(x, y));
							break;
						case STUDY:
							studyDoors.add(new Location(x, y));
							break;
						case HALL:
							hallDoors.add(new Location(x, y));
							break;
						case LOUNGE:
							loungeDoors.add(new Location(x, y));
							break;
						case DININGROOM:
							diningRoomDoors.add(new Location(x, y));
							break;
						}
					}
				}
			}
		}
	}

}
