
public class RoomTile implements BoardPiece {
	public Room.Name name;
	public boolean door;

	public RoomTile(Room.Name name, boolean door) {
		this.name = name;
		this.door = door;
	}

	public String toString() {
		switch (this.name) {
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
