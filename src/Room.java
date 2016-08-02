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
	
	public boolean equals(Room room){
		if(this.name == room.name){return true;}
		return false;
	}

	public String toString(){
		switch(this.name){
		case KITCHEN:
			return "Kitchen";
		case BALLROOM:
			return "Ball Room";
		case BILLIARD:
			return "Billiard Room";
		case DININGROOM:
			return "Dining Room";
		case CONSEVERTORY:
			return "Consevertory";
		case STUDY:
			return "Study";
		case LIBRARY:
			return "Library";
		case LOUNGE:
			return "Lounge";
		case HALL:
			return "Hall";
		}
		return "";
	}
}
