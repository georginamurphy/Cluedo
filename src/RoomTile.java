
public class RoomTile implements BoardPiece {
	public Room.Name name;
	public boolean door;

	public RoomTile(Room.Name name, boolean door) {
		this.name = name;
		this.door = door;
	}

	public String toString() {
		if(this.door){
			return "@";
		}
		switch (this.name) {
		case KITCHEN:
			return "K";
		case BALLROOM:
			return "b";
		case BILLIARD:
			return "B";
		case DININGROOM:
			return "D";
		case CONSERVATORY:
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
