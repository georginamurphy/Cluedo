package cluedo.controller;

import java.util.ArrayList;

import cluedo.boardpieces.Player;
import cluedo.cards.Card;
import cluedo.cards.Character;
import cluedo.cards.Character.Colour;
import cluedo.cards.Room;
import cluedo.cards.Weapon;
import cluedo.game.Board;
import cluedo.game.GUI;
import cluedo.game.Game;
import cluedo.game.Game.Direction;
import cluedo.game.Solution;

/**
 * A controller is an object that coordinates interactions between the GUI and the Game
 * Essentially serves as a middle man so that game logic and game visuals can remain largely 
 * separated and more organized.
 */
public class CluedoController {
	
	// The GUI for the game
	private GUI GUI;
	
	// The game responsible for logic
	private Game game;
	
	// The board for the game
	private Board board;
	
	// ArrayLists for players
	private ArrayList<Player> humanPlayers;
	private ArrayList<Player> allPlayers;
	
	// Variables for initialization
	int numPlayers;
	int movesLeft;
	
	// The player who's turn it is currently
	Player focusPlayer;
	
	// ArrayLists to hold cards, used for initialization
	ArrayList<Character> characters;
	ArrayList<Weapon> weapons;
	ArrayList<Room> rooms;
	
	/**
	 * Constructor for a Controller
	 */
	public CluedoController(){
		makeCharacters();
		makeRooms();
		makeWeapons();
		this.board = new Board();
		this.GUI = new GUI(this);
	}
	
	/**
	 * Returns the board
	 * @return
	 */
	public Board getBoard(){
		return this.board;
	}
	
	/**
	 * Sets focus to first player in humanPlayers (player1)
	 */
	public void resetFocus(){
		if(!humanPlayers.isEmpty() )
			this.focusPlayer = humanPlayers.get(0);
	}
	
	/**
	 * Getter for the humanPlayers Array
	 * @return
	 */
	public ArrayList<Player> getHumanPlayers(){
		return humanPlayers;
	}
	
	/**
	 * Returns number of players in the game
	 * @return
	 */
	public int getNumPlayers(){
		return numPlayers;
	}
	
	/**
	 * Returns the focusPlayer
	 * @return
	 */
	public Player getFocus(){
		return focusPlayer;
	}
	
	/**
	 * Sets the focusPlayer
	 * @param pl
	 */
	public void setFocus(Player pl){
		this.focusPlayer = pl;
	}
	
	/**
	 * Creates a new board with a complete list of players.
	 * Called after users have selected names and characters.
	 */
	public void createBoardWithPlayers(){
		board = new Board(allPlayers);
	}
	
	public ArrayList<Player> getAllPlayers(){
		return allPlayers;
	}
	
	/**
	 * Fills allPlayers with nonHuman players using characters
	 * not selected by users
	 */
	public void fillRemainingPlayers(){
		this.allPlayers = new ArrayList<Player>();
		allPlayers.addAll(humanPlayers);
		
		if(!hasColorPlayer(Colour.RED) ){allPlayers.add(new Player(getCharacter(Colour.RED), false) );}
		if(!hasColorPlayer(Colour.YELLOW) ){allPlayers.add(new Player(getCharacter(Colour.YELLOW), false) );}
		if(!hasColorPlayer(Colour.BLUE) ){allPlayers.add(new Player(getCharacter(Colour.BLUE), false) );}
		if(!hasColorPlayer(Colour.GREEN) ){allPlayers.add(new Player(getCharacter(Colour.GREEN), false) );}
		if(!hasColorPlayer(Colour.PURPLE) ){allPlayers.add(new Player(getCharacter(Colour.PURPLE), false) );}
		if(!hasColorPlayer(Colour.WHITE) ){allPlayers.add(new Player(getCharacter(Colour.WHITE), false) );}
		
		for(Player p : allPlayers){
			p.updateLocation(p.getCharacter().getStartLoc() );
		}
	}
	
	/**
	 * Returns true if a player is already using the given color
	 * @param col
	 * @return
	 */
	public boolean hasColorPlayer(Colour col){
		for(Player p : allPlayers){
			if(p.getCharacter().colour == col){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Starts the user interaction to ask for names, and characters
	 */
	public void setupGame(){
		humanPlayers = new ArrayList<Player>();
		numPlayers = GUI.getNumPlayers();
		GUI.chooseNames();
	}
	
	/**
	 * Adds the given player to the humanPlayers list
	 * @param p
	 */
	public void addPlayer(Player p){
		humanPlayers.add(p);
	}
	
	/**
	 * Adds a given character to a given player
	 * @param p
	 * @param character
	 */
	public void addCharacterToPlayer(Player p, Character character){
		p.setCharacter(character);
	}
	
	/**
	 * Returns the character with the given color
	 * @param col
	 * @return
	 */
	public Character getCharacter(Character.Colour col){
		for(Character c : characters){
			if(c.colour == col){
				return c;
			}
		}
		return null; // shouldn't happen
	}
	
	/**
	 * Returns the player whos turn should follow after the focusPlayer
	 * @return
	 */
	public Player getNextPlayer() {
		int index = humanPlayers.indexOf(focusPlayer);
		index++;
		if (index == humanPlayers.size()) {
			index = 0;
		}
		while (index < humanPlayers.size()) {
			if (!humanPlayers.get(index).getDead()) {
				return humanPlayers.get(index);
			}
			else if (index == humanPlayers.size() - 1) {
				index = 0;
			}
			else{
				index++;
			}
		}
		return null; // shouldn't happen
	}
	
	public void decMovesLeft(){
		movesLeft--;
	}
	public int getMovesLeft(){
		return movesLeft;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void startGame(){
		game = new Game(board, humanPlayers, allPlayers, this);
		makeCharacters();
		makeWeapons();
		makeRooms();
		game.createSolution(characters, weapons, rooms);
		game.dealCards(characters, weapons, rooms);
		focusPlayer = humanPlayers.get(0);
		GUI.takeTurn();
	}
	
	public void rollDice(){
		movesLeft = game.rollDice();
	}
	
	/**
	 * Calls the GUI to draw the board again, used by Game
	 */
	public void drawBoard(){
		GUI.drawBoard();
	}
	
	public int getDoorNumber(int numDoors){
		return GUI.getDoorNumber(numDoors);
	}
	
	public void doMoveIfValid(Direction direction){
		if(game.checkValidMove(focusPlayer, direction) ){
			// User made a valid move, apply to the board and update the GUI
			game.applyMove(focusPlayer, direction);
			decMovesLeft();
			GUI.updateFeedbackMoves(true);
			
			// Check if user has entered a room and if they have run out of moves
			// Has the user entered a room?
			if(game.isInRoom(focusPlayer) ){
				GUI.enteredRoom();
			}
			// Else have they ended their turn not in a room? Move onto next
			// player
			else if (movesLeft == 0 && !game.isInRoom(focusPlayer)) {
				// Shift focus to the next player
				setFocus(getNextPlayer() );
				
				if(focusPlayer == null){System.out.println("NEXT PLAYER WAS NULL???");} // shouldn't happen
				else{
					if(game.isInRoom(focusPlayer) ){
						GUI.updateLabelsEnteredRoom();
						GUI.leaveRoom();
					}
					else{
						GUI.takeTurn();
					}
				}
			}
		}
		else{
			GUI.updateFeedbackMoves(false);
		}
	}
	
	public void startTurnInRoom(){
		boolean cantLeaveRoom = focusPlayer.startTurnInRoom();
		if(!cantLeaveRoom){
			GUI.takeTurn();
		}
		else{
			Player skipped = focusPlayer;
			setFocus(getNextPlayer() );
			GUI.displaySkippedTurn(skipped);
			GUI.takeTurn();
		}
	}
	
	public boolean isInRoom(Player p){
		return game.isInRoom(p);
	}
	
	public Room.Name getRoom(){
		return game.inRoom(focusPlayer);
	}
	
	public Card makeSuggestion(Solution suggestion){
		Card result = game.makeSuggestion(focusPlayer, suggestion);
		return result;
	}
	
	public void nextPlayerTurn(){
		setFocus(getNextPlayer() );
		GUI.takeTurn();
	}
	
	public void movePlayerToRoom(Character toMove, Room.Name toMoveTo){
		for(Player p : allPlayers){
			if(p.getCharacter().equals(toMove) ){
				if(!p.equals(focusPlayer) ){game.movePlayerToRoom(p, toMoveTo);}
				break;
			}
		}
	}
	
	public boolean makeAccusation(Solution guess){
		return this.game.checkSolution(guess, focusPlayer);
	}
	
	// ==================================================================================================================================
	//                                                    MAKING NEW CARDS
	// ==================================================================================================================================
	
	/**
	 * Declares and Initializes all of the Character cards
	 */
	private void makeCharacters() {
		this.characters = new ArrayList<Character>();
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
		this.rooms = new ArrayList<Room>();
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
		this.weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon(Weapon.Type.CANDLESTICK));
		weapons.add(new Weapon(Weapon.Type.DAGGER));
		weapons.add(new Weapon(Weapon.Type.LEADPIPE));
		weapons.add(new Weapon(Weapon.Type.REVOLVER));
		weapons.add(new Weapon(Weapon.Type.ROPE));
		weapons.add(new Weapon(Weapon.Type.SPANNER));
	}
}
