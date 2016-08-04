
public class Solution {
	private Weapon weapon;
	private Character character;
	private Room room;

	public Solution(Weapon weapon, Character character, Room room) {
		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}

	public boolean checkSolution(Solution guess) {
		if (!this.weapon.equals(guess.getWeapon())) {
			return false;
		}
		if (!this.character.equals(guess.getCharacter())) {
			return false;
		}
		if (!this.room.equals(guess.getRoom())) {
			return false;
		}
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

	public boolean equals(Solution solution) {
		if ((this.weapon.equals(solution.weapon)) && (this.character.equals(solution.character)) && (this.room.equals(solution.room))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkCard(Card c) {
		if (c instanceof Character) {
			if (character.equals((Character) c)) {
				return true;
			}
		} else if (c instanceof Weapon) {
			if (weapon.equals((Weapon) c)) {
				return true;
			}
		} else if (c instanceof Room) {
			if (room.equals((Room) c)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Room: " + this.room.toString() + "\nCharacter: " + this.character.toString() + "\nWeapon: "
				+ this.weapon.toString();
	}

}
