public class Character implements Card {
	public String name;
	public Colour colour;
	public Location startLoc;

	public enum Colour {
		WHITE, GREEN, BLUE, PURPLE, RED, YELLOW;
	}

	/**
	 * @param name
	 * @param colour
	 */
	public Character(String name, Colour colour, int x, int y) {
		this.name = name;
		this.colour = colour;
		this.startLoc = new Location(x, y);
	}
	
	/**
	 * @param colour
	 */
	public Character(Colour colour) {
		this.colour = colour;
	}

	public boolean equals(Character character) {
		if (this.colour == character.colour) {
			return true;
		}
		return false;
	}

	public Location getStartLoc() {
		return startLoc;
	}


	public String toString() {
		return this.name;
	}
}
