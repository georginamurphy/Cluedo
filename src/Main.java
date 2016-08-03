import java.util.ArrayList;
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
				//+ "At different points in the game you will be given a menu.\n"
				//+ "To navigate around the game you will enter numbers into the\n"
				//+ "console that correspond to the provided menu.\n"
				//+ "      ONLY NUMBERS WILL BE ACCEPTED AS VALID INPUT\n"
				+ "**********************************************************");
		
		makeCharacters();
		makeWeapons();
		makeRooms();
		setPlayers();
		board = new Board(players);
		game = new Game(board, players);
		game.mockGame(characters, weapons, rooms);
		//game.createSolution(characters, weapons, rooms);
		//game.dealCards(characters, weapons, rooms);
		//game.run();
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
		String number = input.next();
		// If the user has not entered a valid number of players, loop until they do
		while(!validNumber(number) ){
			System.out.println("You must have between 3 and 6 players, inclusive!");
			System.out.println("Please enter the number of players: ");
			number = input.next();
		}
		int numberOfPlayers = Integer.parseInt(number);
		
		int count = 0;
		for (Character c : characters) {
			if (count < numberOfPlayers) {
				players.add(new Player(c, true));
				count++;
			} else {
				players.add(new Player(c, false));
			}
			
		}
		//input.close();
	}
	
	private static boolean validNumber(String s){
		s.trim();
		if(s.equals("3") ){return true;}
		if(s.equals("4") ){return true;}
		if(s.equals("5") ){return true;}
		if(s.equals("6") ){return true;}
		return false;
	}

}
