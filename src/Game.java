import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private Board board;
	private Solution solution;
	private ArrayList<Player> players;

	public Game(Board board, ArrayList<Player> players) {
		this.board = board;
		this.players = players;
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
		for (Player p : players) {
			if (p.getUsed())
				realPlayers.add(p);
		}
		cards.addAll(chars);
		cards.addAll(weapons);
		cards.addAll(rooms);

		Collections.shuffle(cards);

		for (int i = 0; i < cards.size(); i++) {
			cards.get(i);
		}
	}
}
