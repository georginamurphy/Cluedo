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
		makeCharacters();
		makeWeapons();
		makeRooms();
		makePlayers();
		board = new Board();
		game = new Game(board, players);
		game.createSolution(characters, weapons, rooms);
	}

	/**
	 * Declares and Initializes all of the Character cards
	 */
	private static void makeCharacters() {
		characters.add(new Character("Miss Scarlett", Character.Colour.RED, 5, 0));
		characters.add(new Character("Professor Pulm", Character.Colour.PURPLE, 0, 8));
		characters.add(new Character("Mrs Peacock", Character.Colour.BLUE, 6, 19));
		characters.add(new Character("Reverend Green", Character.Colour.GREEN, 18, 0));
		characters.add(new Character("Colonel Mustard", Character.Colour.YELLOW, 22, 13));
		characters.add(new Character("Mrs White", Character.Colour.WHITE, 16, 19));
	}

	/**
	 * Declares and Initializes all of the Room cards
	 */
	private static void makeRooms() {
		rooms.add(new Room(Room.Name.BALLROOM));
		rooms.add(new Room(Room.Name.BILLIARD));
		rooms.add(new Room(Room.Name.CONSEVERTORY));
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
	 * Asks the user for the number of players. Checks the input is valid and constructs an 
	 * arraylist with the desired number of players
	 */
	private static void makePlayers() {
		int numPlayers = 0;
		Scanner input = new Scanner(System.in);

		System.out.println("There must be between 3 and 8 players");
		System.out.println("Please enter the number of players: ");
		try {
			numPlayers = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Value entered was not a number");
		}
		
		for (int i = 1; i <= numPlayers; i++) {
			int characterIndex = (int) Math.random() * characters.size();
		}

		input.close();
	}

}
