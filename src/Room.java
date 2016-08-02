public class Room implements Card {
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
	
	public boolean equals(Room room){
		if(this.name == room.name){return true;}
		return false;
	}

	public String toString(){
		return this.name.toString();
	}
}
