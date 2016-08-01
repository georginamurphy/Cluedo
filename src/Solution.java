
public class Solution {
	private Weapon weapon;
	private Character character;
	private Room room;
	
	public Solution(Weapon weapon, Character character, Room room){
		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}
	
	public boolean checkSolution(Weapon weapon, Character character, Room room){
		if(!this.weapon.equals(weapon) ){return false;}
		if(!this.character.equals(character) ){return false;}
		if(!this.room.equals(room) ){return false;}
		return true;
	}

}
