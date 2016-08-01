import java.util.HashSet;
import java.util.Set;

public class Main {

	
private static Set<Character> characters = new HashSet<Character>();
private static Set<Weapon> weapons = new HashSet<Weapon>();
private static Set<Room> rooms = new HashSet<Room>();
private static Board board;

public static void main(String [] args){
	makeCharacters();
	makeWeapons();
	makeRooms();
	board = new Board();
}   

private static void makeWeapons() {
	weapons.add(new Weapon(Weapon.Type.CANDLESTICK));
	weapons.add(new Weapon(Weapon.Type.DAGGER));
	weapons.add(new Weapon(Weapon.Type.LEADPIPE));
	weapons.add(new Weapon(Weapon.Type.REVOLVER));
	weapons.add(new Weapon(Weapon.Type.ROPE));
	weapons.add(new Weapon(Weapon.Type.SPANNER));
	
}

private static void makeCharacters() {
	characters.add(new Character("Miss Scarlett", Character.Colour.RED, 5, 0));
	characters.add(new Character("Professor Pulm", Character.Colour.PURPLE, 0, 8));
	characters.add(new Character("Mrs Peacock", Character.Colour.BLUE, 6, 19));
	characters.add(new Character("Reverend Green", Character.Colour.GREEN, 18, 0));
	characters.add(new Character("Colonel Mustard", Character.Colour.YELLOW, 22, 13));
	characters.add(new Character("Mrs White", Character.Colour.WHITE, 16, 19));
}

private static void makeRooms() {
	rooms.add(new Room(Room.Name.BALLROOM));
	rooms.add(new Room(Room.Name.BILLIARD));
	rooms.add(new Room(Room.Name.CONSEVERTORY));
	rooms.add(new Room(Room.Name.DININGROOM));
	rooms.add(new Room(Room.Name.HALL));
	rooms.add(new Room(Room.Name.KITCHEN));
	rooms.add(new Room(Room.Name.LIBRARY));
	rooms.add(new Room(Room.Name.LOUNGE));
	rooms.add(new Room(Room.Name.STUDY));
}
}
