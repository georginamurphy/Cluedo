package cluedo.main;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import cluedo.boardpieces.Player;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.cards.Weapon;
import cluedo.controller.CluedoController;
import cluedo.game.Board;
import cluedo.game.Game;

public class Main {
	// An ArrayList to hold character cards for the game
	private static ArrayList<Character> characters = new ArrayList<Character>();
	
	// An ArrayList to hold weapon cards for the game
	private static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	// An ArrayList to hold room cards the game
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	
	// An ArrayList to hold the players for the game
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	// The board for the game
	private static Board board;
	
	// The game object for the game
	private static Game game;
	
	/**
	 * Responsible for initializing aspects of the game, such as creating the board and game
	 * objects.  Also creates the solution for the game, and deals cards to the human players.
	 * 
	 * The run method called on game is what actually starts the loop that runs the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//makeCharacters();
		//makeWeapons();
		//makeRooms();
		
		//board = new Board();
		//GUI GUI = new GUI(board, characters, weapons, rooms);
		//int numPlayers = GUI.getNumPlayers();
		//GUI.pickCharacters(numPlayers);
		
		CluedoController controller = new CluedoController();
		controller.setupGame();
	
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
		int numPlayers = getValidPlayerCount(number, input);
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
	
	private static int getValidPlayerCount(String number, Scanner input){
		boolean countFound = false;
		while(!countFound){
			if(number.equals("3") ){return 3;}
			else if(number.equals("4") ){return 4;}
			else if(number.equals("5") ){return 5;}
			else if(number.equals("6") ){return 6;}
			else{
				System.out.println("Please enter either 3, 4, 5 or 6 for the number of players.");
				number = input.next();
			}
		}
		return 0; // Shouldnt happen
	}
}