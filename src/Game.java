import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

	
	private Board board;
	private Solution solution;
	private ArrayList<Player> players; // Consist of every player, both human
										// and non-human
	public ArrayList<Player> humanPlayers; // Consists of ONLY the human
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

		int charIndex = (int) (Math.random() * chars.size());
		int weaponIndex = (int) (Math.random() * weapons.size());
		int roomIndex = (int) (Math.random() * rooms.size());

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
				if (!p.getDead()) {
					p.startTurn();
					if (gameEnd) {
						break;
					}
				}
			}
		}

		System.out.println("The game has been won. The solution was:");
		System.out.println(solution.toString());
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
				if (piece != null) {
					if (((RoomTile) piece).name.equals(player.getRoomLastTurn())) {
						System.out.println("You can't enter that room! You were there during your last turn");
						return false;
					} else {
						return roomTileCheck(player, direction);
					}
				}
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
				if (piece != null) {
					if (((RoomTile) piece).name.equals(player.getRoomLastTurn())) {
						System.out.println("You can't enter that room! You were there during your last turn");
						return false;
					} else {
						return roomTileCheck(player, direction);
					}
				}
			} else {
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
				if (piece != null) {
					if (((RoomTile) piece).name.equals(player.getRoomLastTurn())) {
						System.out.println("You can't enter that room! You were there during your last turn");
						return false;
					} else {
						return roomTileCheck(player, direction);
					}
				}
			} else {
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
				if (piece != null) {
					if (((RoomTile) piece).name.equals(player.getRoomLastTurn())) {
						System.out.println("You can't enter that room! You were there during your last turn");
						return false;
					} else {
						return roomTileCheck(player, direction);
					}
				}
			} else {
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
	 * that represents a door
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
		roomTileLists.add(ballRoomTiles);
		roomTileLists.add(conservatoryTiles);
		roomTileLists.add(billiardTiles);
		roomTileLists.add(libraryTiles);
		roomTileLists.add(studyTiles);
		roomTileLists.add(hallTiles);
		roomTileLists.add(loungeTiles);
		roomTileLists.add(diningRoomTiles);

		for (ArrayList<Location> list : roomTileLists) {
			if (list.contains(p.getLocation())) {
				return true;
			}
		}
		return false;
	}

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

		System.out.println("SOMETHING WENT HORRIBLY WRONG");
		return null;
	}

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
		System.out.println("SHOULDNT HAVE GOT HERE LOL");
		return false;
	}

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
		System.out.println("HOLY SOMEWTHING WENT REALLY WRONG");
		return null;
	}

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

	public void makeSuggestionDecisions(Player p) {
		Scanner input = new Scanner(System.in);
		printBoard();
		System.out.println("You have entered a room you can make eiter a suggestion or an accusation");
		System.out.println("Press 1 for a suggestion or 2 for an accusation");

		int userInput = getUserInput(input, 1, 2);

		switch (userInput) {
		case 1:
			p.printCards();
			makeSuggestion(p, input);
			break;
		case 2:
			makeAccusation(p, input);
			break;

		}

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
	public void makeSuggestion(Player player, Scanner input) {
		Solution guess = constructGuess(player, input, false);
		System.out.println(guess.toString());
		for (Player p : humanPlayers) {
			if (!p.equals(player)) {
				for (Card c : p.getCards()) {
					if (guess.checkCard(c)) {
						System.out.println("");
						System.out.println(p.getCharacter().name + " has the card \n" + "\n*******************\n* "
								+ c.toString() + " *\n*******************\n");
						return;
					}
				}
			}
		}

		System.out.println("\n   None of the other players had any of the cards in your suggestion\n");
	}

	/**
	 * Checks the users guess against the secret solution. If they are correct
	 * the game is over if they are incorrect they are removed from the game
	 * 
	 * @param p
	 *            - player making the accusation
	 * @param input2
	 */
	public void makeAccusation(Player p, Scanner input) {
		System.out.println("Are you sure you want to submit an accusation?\n"
				+ "If you are incorrect you will be removed from the game\n"
				+ "Press 1 to continue or press any other key to go back to game");

		if (input.next().equals("1")) {
			p.printCards();
			Solution guess = constructGuess(p, input, true);
			System.out.println("Guess:");
			System.out.println(guess.toString());
			System.out.println("Solution:");
			System.out.println(solution.toString());
			if (guess.equals(solution)) {
				gameEnd = true;
				System.out.println("\n  Congratualtions " + p.getCharacter().name + " you solved the murder\n");
			} else {
				System.out.println("\n  Your guess was incorrect, you have been removed from the game\n");
				p.removeFromGame();
			}
		}
	}

	/**
	 * Constructs a solution based on the users current room and their choice of
	 * weapon and character
	 * 
	 * @param input
	 * 
	 * @param Player
	 *            - the current player that is making the guess
	 * @return Solution
	 */
	public Solution constructGuess(Player player, Scanner input, boolean accusation) {
		int userInput;
		Room room;
		if(accusation){
			System.out.println("Room:  1: Kitchen\n       2: Ball Room\n       3: Conservatory\n       4: Dining Room\n       5: Billiard Room\n       6: Library\n       7: Lounge\n       8: Hall\n       9: Study");
			System.out.println("Select a number for the room you are accusing");

			userInput = getUserInput(input, 1, 9);
			room = new Room(getRoomName(userInput));
		}else{
			room = new Room(inRoom(player));
		}

		System.out.println("Room: " + room.toString());
		System.out.println("Character:  1: Mrs White\n            2: Reverend Green\n"
				+ "            3: Mrs Peacock\n            4: Professor Plum\n            5: Miss Scarlett\n"
				+ "            6: Colonel Mustard\n");
		System.out.println("Select a number for the character you are accusing");

		userInput = getUserInput(input, 1, 6);
		Character character = new Character(getCharacterColour(userInput));

		for (Player p : players) {
			if (p.getCharacter().equals(character)) {
				Location newPlayerLocation = getRoomTile(room.name);
				p.updateLocation(newPlayerLocation);
				this.board.updateBoard(humanPlayers);
			}
		}

		System.out.println("Room: " + room.toString());
		System.out.println("Character: " + character.toString());
		System.out.println("Weapon:  1: Candlestick\n         2: Dagger\n"
				+ "         3: Leadpipe\n         4: Rope\n         5: Spanner\n" + "         6: Revolver\n");

		System.out.println("Select a number for the weapon you are using");

		userInput = getUserInput(input, 1, 6);
		Weapon weapon = new Weapon(getWeaponName(userInput));
		return new Solution(weapon, character, room);
	}

	/**
	 * gets the users input from the console. expects a integer between the low
	 * and high bounds (inclusive)
	 * 
	 * @param input
	 *            - scanner
	 * @param low
	 *            - lowest valid number
	 * @param high
	 *            = highest valid number
	 * @return the users valid input
	 */
	public int getUserInput(Scanner input, int low, int high) {
		int userInput = 0;
		boolean isValid = false;

		while (!isValid) {
			try {
				userInput = input.nextInt();
				if (userInput >= low && userInput <= high)
					isValid = true;
				else
					System.out.println("Please enter a number from " + low + " to " + high);
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number from " + low + " to " + high);
				continue;
			}

		}
		return userInput;
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
					if (((RoomTile) piece).door) {
						Room.Name name = ((RoomTile) piece).name;
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

	public Board getBoard() {
		return this.board;
	}
	
	public boolean getGameEnd(){
		return this.gameEnd;
	}

	/**
	 * Prints the board
	 */
	public void printBoard() {
		System.out.println(this.board.toString());
	}

	public void mockGame(ArrayList<Character> characters, ArrayList<Weapon> weapons, ArrayList<Room> rooms) {
		solution = new Solution(new Weapon(Weapon.Type.ROPE), new Character(Character.Colour.BLUE),
				new Room(Room.Name.BALLROOM));

	}

	public void printDoors(Room.Name name) {
		switch (name) {
		case BALLROOM:
			for (Location l : ballRoomDoors) {
				System.out.println(l.x + " " + l.y);
			}
			break;
		}
	}
}
