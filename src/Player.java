import java.util.ArrayList;

public class Player implements BoardPiece {

	private Location location;
	private Character character;
	private boolean used;
	private ArrayList<Card> cards;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
	}


	public Location getLocation() {
		return location;
	}

	public boolean getUsed() {
		return used;
	}

	public boolean hasCard(Card card) {
		if (cards.contains(card))
			return true;
		else
			return false;
	}
	
	public void diplayPlayerInfo(){
		System.out.println("Player: " + character.name);
		System.out.println("Player: " + character.name);
	}
	
	public String toString() {
		switch (this.character.colour) {
		case WHITE:
			return "1";
		case GREEN:
			return "2";
		case BLUE:
			return "3";
		case PURPLE:
			return "4";
		case RED:
			return "5";
		case YELLOW:
			return "6";
		}
		return "";
	}
}
