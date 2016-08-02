public class Character implements Card, BoardPiece{
	public String name;
	public Colour colour;
	public Location startLoc;
	
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
		this.startLoc = new Location(x, y);
	}
	
	public boolean equals(Character character){
		if(this.name == character.name){return true;}
		return false;
	}

	public Location getStartLoc() {
		return startLoc;
	}
	
	public String toString(){
		switch(this.colour){
		case WHITE:
			return "1";
		case GREEN:
			return "2";
		case BLUE:
			return "3";
		case PURPLE:
			return "4";
		case RED:
			return "5";
		case YELLOW:
			return "6";
		}
		return "";
	}
}
