import java.util.List;

public class Room implements BoardPiece, Card {
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
	
	public boolean equals(Room room){
		if(this.name == room.name){return true;}
		return false;
	}

	public String toString(){
		switch(this.name){
		case KITCHEN:
			return "K";
		case BALLROOM:
			return "b";
		case BILLIARD:
			return "B";
		case DININGROOM:
			return "D";
		case CONSEVERTORY:
			return "C";
		case STUDY:
			return "S";
		case LIBRARY:
			return "L";
		case LOUNGE:
			return "l";
		case HALL:
			return "H";
		}
		return "";
	}
}
