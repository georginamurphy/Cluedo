
public class Player implements BoardPiece {

	private Location location;
	private Character character;

	public Player(Character character) {
		this.character = character;
		this.location = character.getStartLoc();
	}
	
	
}
