public class Room implements BoardPiece {
	public Name name;
	public boolean door;
	
	public enum Name{ 
		KITCHEN,
		BALLROOM,
		CONSEVERTORY,
		BILLIARD,
		LIBRARY,
		STUDY,
		HALL,
		LOUNGE,
		DININGROOM;
	}
	
	public Room(Name name){
		this.name = name;
		this.door = false;
		
	}
	
	public Room(Name name, boolean door){
		this.name = name;
		this.door = door;
		
	}
}
