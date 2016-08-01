public class Character implements Card{
	public String name;
	public Colour colour;
	
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
	}
	
	public boolean equals(Character character){
		if(this.name == character.name){return true;}
		return false;
	}

}