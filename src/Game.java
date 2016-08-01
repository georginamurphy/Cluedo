import java.util.ArrayList;

public class Game {

	private Board board;
	private Solution solution;
	
	public Game(Board board){
		this.board = board;
	}
	
	/**
	 * Takes 3 ArrayLists, one of each of the following types: Character, Weapon and Room
	 * Will select one random Object from each Array and use it to form the Solution for the game.
	 * Then stores that Solution in the private field 'solution'.
	 * 
	 * After storing the solution, it will then remove those 3 objects that were used from each of their
	 * 	respective ArrayLists.
	 * 
	 * @param chars
	 * @param weapons
	 * @param rooms
	 */
	public void createSolution(ArrayList<Character> chars, ArrayList<Weapon> weapons, ArrayList<Room> rooms){
		int charIndex = (int) Math.random() * chars.size();
		int weaponIndex = (int) Math.random() * weapons.size();
		int roomIndex = (int) Math.random() * rooms.size();
		
		this.solution = new Solution(weapons.get(weaponIndex), chars.get(charIndex), rooms.get(roomIndex) );
		
		chars.remove(charIndex);
		weapons.remove(weaponIndex);
		rooms.remove(roomIndex);
	}
	
	/**
	 * Takes 3 ArrayLists, one of each of the following types: Character, Weapon and Room
	 * 
	 * @param chars
	 * @param weapons
	 * @param rooms
	 */
	public void dealCards(ArrayList<Character> chars, ArrayList<Weapon> weapons, ArrayList<Room> rooms){
		
	}
}
