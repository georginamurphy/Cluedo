
public class Player implements BoardPiece {

	private Location location;
	private Character character;

	public Player(Character character) {
		this.character = character;
		this.location = character.getStartLoc();
	}
	
	public String toString(){
		switch(this.character.colour){
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

	public Location getLocation() {
		return location;
	}
}
