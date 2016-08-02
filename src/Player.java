
public class Player implements BoardPiece {

	private Location location;
	private Character character;
	private boolean used;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
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

	public boolean getUsed() {
		return used;
	}
}
