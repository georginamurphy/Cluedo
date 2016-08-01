
public class Player implements BoardPiece{
	
	private Location location;
	private Character character;
	
	public Player(Character character, Location loc){
		this.location = loc;
		this.character = character;
	}

}
