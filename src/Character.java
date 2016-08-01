package Hi;

public class Character {
	public String name;
	public Colour colour;
	public Location startPos;
	
	
	public enum Colour{
		RED,
		PURPLE,
		BLUE,
		GREEN,
		YELLOW,
		WHITE;
	}
	
	/**
	 * @param name
	 * @param colour
	 */
	public Character(String name, Colour colour, int x, int y) {
		this.name = name;
		this.colour = colour;
		//this.startPos = new Location(x, y, Hallway);
	}

}
