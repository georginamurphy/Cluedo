import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private Board board;
	private Solution solution;
	private ArrayList<Player> players; // Consist of every player, both human and non-human
	private ArrayList<Player> humanPlayers; // Consists of ONLY the human players
	private boolean gameEnd;
	
	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	public Game(Board board, ArrayList<Player> players) {
		this.board = board;
		this.players = players;
		this.gameEnd = false;
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
			if(p.getUsed() ){
				realPlayers.add(p);
				numOfPlayers++;
			}
		}
		// Set the field that holds all the human players to be the array we just calculated
		this.humanPlayers = realPlayers;
		// Also, intitalize the game field for each player to be this game object
		for(Player p : players){
			p.setGame(this);
		}
		
		cards.addAll(chars);
		cards.addAll(weapons);
		cards.addAll(rooms);

		// Shuffle the cards
		Collections.shuffle(cards);
		
		// Now deal them out to the players, some players WILL have more cards than others if 
		// the cards dont split amongst the players evenly.
		int playerIndex = 0;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			realPlayers.get(playerIndex).dealCard(card);
			playerIndex++;
			if(!(playerIndex < numOfPlayers) ){
				playerIndex = 0;
			}
		}
		
		// For testing
		//for(Player p : realPlayers){
		//	p.printCards();
		//}
	}
	
	public void run(){
		while(!gameEnd){
			for(Player p : humanPlayers){
				p.startTurn();
			}
		}
	}
	
	public Board getBoard() {
		return board;
	}

	public boolean checkValidMove(Player player, Game.Direction direction){
		return false;
	}
	
	public void applyMove(Player player, Game.Direction direction){
		
	}
}
