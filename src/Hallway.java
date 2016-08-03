public class Hallway implements BoardPiece {
	private boolean hasPlayer;
	
	public Hallway(){
		this.hasPlayer = false;
	}
	
	/**
	 * Returns true if a player is standing in this Hallway object
	 * @return
	 */
	public boolean isFull(){
		return this.hasPlayer;
	}
	
	public String toString(){
		return "-";
	}
	
}
