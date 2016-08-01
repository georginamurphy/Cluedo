import java.util.List;

public class Room implements BoardPiece {
	public Name name;
	
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
		
	}
	
	private void createLocList(List<Integer> coords){
		for(int i = 0; i < coords.size() - 1; i++){
			int x = coords.get(i);
			int y = coords.get(i + 1);
			//locs.add(new Location(x, y, this) );
		}
	}
}
