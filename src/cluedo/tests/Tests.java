package cluedo.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import cluedo.game.*;
import cluedo.game.Game.Direction;
import cluedo.boardpieces.*;
import cluedo.cards.*;
import cluedo.cards.Character;

/**
 * A JUnit Testing class for the Cluedo game.  Please ignore any output printed to the console
 * It is simply a biproduct of some of the methods in the game that we receive output to the console.
 * The output does not relate to the tests passing or failing in any way.
 */
public class Tests{
	private ArrayList<Player> players;
	private Player playerOne;
	private Player playerTwo;
	
	// An ArrayList to hold character cards for the game
	private ArrayList<Character> characters = new ArrayList<Character>();
		
	// An ArrayList to hold weapon cards for the game
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		
	// An ArrayList to hold room cards the game
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	/*
	 * Tests a player moving onto a Hallway BoardPiece is both valid and applied to the board correctly
	 */
	@Test
	public void testValidMove(){
		setupPlayers(new Location(9, 0), new Location(15, 0) );
		Board board = new Board(players);
		Game game = new Game(board, players);
		Direction movingDirection = Direction.DOWN;
		Location expected = new Location(9, 1);
		
		// Check the move is considered valid
		assertTrue(game.checkValidMove(playerOne, movingDirection) );
		
		// Apply the move to the board
		game.applyMove(playerOne, movingDirection);
		
		// Check the BoardPiece at the expected location for playerOne is in fact playerOne
		assertEquals(board.getBoard()[expected.getY()][expected.getX()], playerOne);
	}
	
	/*
	 * Tests that moving onto a RoomTile that is a door is returned as valid
	 * and that the player is moved to the first free location that are designated
	 * for players to sit in while they are in a room
	 */
	@Test
	public void testValidMove2(){
		// Setting up players for the test
		setupPlayers(new Location(4, 7), new Location(15, 0) );
		
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		
		
		Direction movingDirection = Direction.UP;
		Location expected = game.getRoomTile(Room.Name.KITCHEN);
		
		// Check the move is valid, then apply move to the board
		// Then check that the BoardPiece at the expected Location is playerOne
		assertTrue(game.checkValidMove(playerOne, movingDirection) );
		game.applyMove(playerOne, movingDirection);
		assertEquals(board.getBoard()[expected.getY()][expected.getX()], playerOne);
	}
	
	/**
	 * Tests trying to move off the board is returned as invalid
	 */
	@Test
	public void testInvalidMove(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
		
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		
		Direction movingDirection = Direction.UP;
		
		// Check the move is returned as invalid
		assertFalse(game.checkValidMove(playerOne, movingDirection) );
	}
	
	/*
	 * Tests trying to move onto an Out of Bounds location is invalid
	 */
	@Test
	public void testInvalidMove2(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
				
		Direction movingDirection = Direction.LEFT;
				
		// Check the move is returned as invalid
		assertFalse(game.checkValidMove(playerOne, movingDirection) );
	}
	
	/*
	 * Tests trying to move onto a RoomTile that is not a door returns as invalid
	 */
	@Test
	public void testInvalidMove3(){
		// Setting up players for the test
		setupPlayers(new Location(6, 3), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
				
		Direction movingDirection = Direction.LEFT;
				
		// Check the move is returned as invalid
		assertFalse(game.checkValidMove(playerOne, movingDirection) );
	}
	
	/*
	 * Tests trying to move out of a room through a door blocked by another player is returned as invalid
	 */
	@Test
	public void testInvalidMove5(){
		// Setting up players for the test
		setupPlayers(new Location(6, 3), new Location(4, 7) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
				
		Location doorLocation = game.getDoorLocations(Room.Name.KITCHEN).get(0);
				
		// Check that game.isFreeDoor return null, meaning the doorLocation is blocked by another player
		// Applying the move to the board is not called if this method returns null
		assertEquals(game.isFreeDoor(doorLocation), null);
	}
	
	/*
	 * Tests trying to move on top of another player is returned as invalid
	 */
	@Test
	public void testInvalidMove4(){
		// Setting up players for the test
		setupPlayers(new Location(6, 7), new Location(7, 7) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
				
		Direction movingDirection = Direction.RIGHT;
		
		// Check the move is returned as invalid
		assertFalse(game.checkValidMove(playerOne, movingDirection) );
	}
	
	/*
	 * Tests that providing a correct solution end the game
	 */
	@Test
	public void testValidSolution(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		makeCharacters();
		makeWeapons();
		makeRooms();
		game.createSolution(characters, weapons, rooms);
		Solution solution = game.getSolution();
				
		assertTrue(game.checkSolution(solution, playerOne) );
		assertTrue(game.getGameEnd() );
	}
	
	/*
	 * Tests that providing an incorrect solution will not end the game
	 * and removes the player from the game
	 */
	@Test
	public void testInvalidSolution(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		playerOne.setGame(game);
		makeCharacters();
		makeWeapons();
		makeRooms();
		game.createSolution(characters, weapons, rooms);
		
		int i = 0;
		Solution incorrectSolution = new Solution(weapons.get(i), characters.get(i), rooms.get(i) );
		
		// Ensures we will actually be checking an invalidSolution
		while(incorrectSolution.equals(game.getSolution() ) ){
			incorrectSolution = new Solution(weapons.get(i), characters.get(i), rooms.get(i) );
			i++;
		}
		
		// Assert the solution is returned as invalid, the game doesn't end and that the player is now considered dead
		assertFalse(game.checkSolution(incorrectSolution, playerOne) );
		assertFalse(game.getGameEnd() );
		assertTrue(playerOne.getDead());
	}
	
	/*
	 * Tests that making a valid suggestion will return the card that is contained
	 * in another player's hand.
	 */
	@Test
	public void testCorrectSuggestion(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		game.setHumanPlayers(players);
		makeCharacters();
		makeWeapons();
		makeRooms();
		game.createSolution(characters, weapons, rooms);
		
		// Deal a card to playerOne
		playerOne.dealCard(weapons.get(0) );
		
		// Now include that card in our guess that playerTwo will make
		Solution guess = new Solution(weapons.get(0), characters.get(0), rooms.get(0) );
		
		// Card c should be equal to weapons.get(0) after makeSuggestion is called
		Card c = game.makeSuggestion(playerTwo, guess);
		assertEquals(c, weapons.get(0));
	}
	
	/*
	 * Tests that making an incorrect suggestion will return a null card 
	 * because it is not contained in any of the other player's hand
	 */
	@Test
	public void testIncorrectSuggestion(){
		// Setting up players for the test
		setupPlayers(new Location(9, 0), new Location(15, 0) );
				
		// Setting up the game and board
		Board board = new Board(players);
		Game game = new Game(board, players);
		game.setHumanPlayers(players);
		makeCharacters();
		makeWeapons();
		makeRooms();
		game.createSolution(characters, weapons, rooms);
		
		// Deal a card to playerOne
		playerOne.dealCard(weapons.get(1) );
		
		// Now include that card in our guess that playerTwo will make
		Solution guess = new Solution(weapons.get(0), characters.get(0), rooms.get(0) );
		
		// Card c should be equal to null after makeSuggestion is called, because playerOne's hand
		// did not contain any of the cards we suggested
		Card c = game.makeSuggestion(playerTwo, guess);
		assertEquals(c, null);
	}
	
	
	
	// ======================================================================================================================================================================
	
	//                                                              TESTER HELPER METHODS
	
	// ======================================================================================================================================================================
	private void setupPlayers(Location loc1, Location loc2){
		this.players = new ArrayList<Player>();
		this.playerOne = new Player(new Character("Mrs White", Character.Colour.WHITE, loc1.x, loc1.y), true);
		this.playerTwo = new Player(new Character("Reverend Green", Character.Colour.GREEN, loc2.x, loc2.y), true);
		
		this.players.add(playerOne);
		this.players.add(playerTwo);
	}
	
	/**
	 * Declares and Initializes all of the Character cards
	 */
	private void makeCharacters() {
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
	private void makeRooms() {
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
	private void makeWeapons() {
		weapons.add(new Weapon(Weapon.Type.CANDLESTICK));
		weapons.add(new Weapon(Weapon.Type.DAGGER));
		weapons.add(new Weapon(Weapon.Type.LEADPIPE));
		weapons.add(new Weapon(Weapon.Type.REVOLVER));
		weapons.add(new Weapon(Weapon.Type.ROPE));
		weapons.add(new Weapon(Weapon.Type.SPANNER));
	}
}
