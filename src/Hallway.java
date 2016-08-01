public class Hallway implements BoardPiece {
	public boolean start;
	
	
	public Hallway(){
		this.start = false;
	}
	
	public Hallway(boolean start) {
		this.start = start;
	}

	public String toString(){
		return "#";
	}
	
}
