package cluedo.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import cluedo.game.*;
import cluedo.game.Game.Direction;
import cluedo.boardpieces.*;
import cluedo.cards.*;
import cluedo.cards.Character;

public class Tests{
	private ArrayList<Player> players;
	private Player playerOne;
	private Player playerTwo;
	
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
		
		assertTrue(game.checkValidMove(playerOne, movingDirection) );
		game.applyMove(playerOne, movingDirection);
		assertEquals(board.getBoard()[expected.getY()][expected.getX()], playerOne);
		
	}
	
	private void setupPlayers(Location loc1, Location loc2){
		this.players = new ArrayList<Player>();
		this.playerOne = new Player(new Character("Mrs White", Character.Colour.WHITE, loc1.x, loc1.y), true);
		this.playerTwo = new Player(new Character("Reverend Green", Character.Colour.GREEN, loc2.x, loc2.y), true);
		
		this.players.add(playerOne);
		this.players.add(playerTwo);
	}
}
