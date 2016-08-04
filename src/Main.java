import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	private static ArrayList<Character> characters = new ArrayList<Character>();
	private static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	private static Board board;
	private static Game game;
	private static ArrayList<Player> players = new ArrayList<Player>();

	public static void main(String[] args) {
		System.out.println(""
				+ "**********************************************************\n"
				+ "                    Welcome to CLUEDO\n"
				+ "**********************************************************");
		makeCharacters();
		makeWeapons();
		makeRooms();
		setPlayers();
		board = new Board(players);
		game = new Game(board, players);
		//game.mockGame(characters, weapons, rooms);
		game.createSolution(characters, weapons, rooms);
		game.dealCards(characters, weapons, rooms);
		game.run();
	}

	/**
	 * Declares and Initializes all of the Character cards
	 */
	private static void makeCharacters() {
		characters.add(new Character("Mrs White", Character.Colour.WHITE, 9, 0));
		characters.add(new Character("Reverend Green", Character.Colour.GREEN, 15, 0));
		characters.add(new Character("Mrs Peacock", Character.Colour.BLUE, 24, 6));
		characters.add(new Character("Professor Plum", Character.Colour.PURPLE, 24, 19));
		characters.add(new Character("Miss Scarlett", Character.Colour.RED, 7, 24));
		characters.add(new Character("Colonel Mustard", Character.Colour.YELLOW, 0, 17));
	}

	/**
	 * Declares and Initializes all of the Room cards
	 */
	private static void makeRooms() {
		rooms.add(new Room(Room.Name.BALLROOM));
		rooms.add(new Room(Room.Name.BILLIARD));
		rooms.add(new Room(Room.Name.CONSERVATORY));
		rooms.add(new Room(Room.Name.DININGROOM));
		rooms.add(new Room(Room.Name.HALL));
		rooms.add(new Room(Room.Name.KITCHEN));
		rooms.add(new Room(Room.Name.LIBRARY));
		rooms.add(new Room(Room.Name.LOUNGE));
		rooms.add(new Room(Room.Name.STUDY));
	}

	/**
	 * Declares and Initializes all of the Weapon cards
	 */
	private static void makeWeapons() {
		weapons.add(new Weapon(Weapon.Type.CANDLESTICK));
		weapons.add(new Weapon(Weapon.Type.DAGGER));
		weapons.add(new Weapon(Weapon.Type.LEADPIPE));
		weapons.add(new Weapon(Weapon.Type.REVOLVER));
		weapons.add(new Weapon(Weapon.Type.ROPE));
		weapons.add(new Weapon(Weapon.Type.SPANNER));
	}

	/**
	 * Asks the user for the number of players. Checks the input is valid and
	 * constructs an arrayList with the desired number of players
	 */
	private static void setPlayers() {
		Scanner input = new Scanner(System.in);

		System.out.println("There must be between 3 and 6 players, inclusive.");
		System.out.println("Please enter the number of players: ");
		int numPlayers = getUserInput(input, 3, 6);
		
		int count = 0;
		for (Character c : characters) {
			if (count < numPlayers) {
				players.add(new Player(c, true));
				count++;
			} else {
				players.add(new Player(c, false));
			}
			
		}
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
	 *            - highest valid number
	 * @return the users valid input
	 */
	public static int getUserInput(Scanner input, int low, int high) {
		int userInput = 0;
		boolean isValid = false;

		while (!isValid) {
			userInput = 0;
			try {
				userInput = input.nextInt();
				if (userInput >= low && userInput <= high)
					isValid = true;
				else
					System.out.println("Please enter a number from " + low + " to " + high);
			} catch (InputMismatchException e) {
				System.out.println("Catch: "+ userInput +"Please enter a number from " + low + " to " + high);
				continue;
			}
			
		}
		return userInput;
	}
}
