
public class Solution {
	private Weapon weapon;
	private Character character;
	private Room room;
	
	public Solution(Weapon weapon, Character character, Room room){
		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}
	
	public boolean checkSolution(Solution guess){
		if(!this.weapon.equals(guess.getWeapon()) ){return false;}
		if(!this.character.equals(guess.getCharacter()) ){return false;}
		if(!this.room.equals(guess.getRoom()) ){return false;}
		return true;
	}

	private Room getRoom() {
		return room;
	}

	private Character getCharacter() {
		return character;
	}

	private Weapon getWeapon() {
		return weapon;
	}
	
	

}
