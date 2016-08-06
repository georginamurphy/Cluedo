package cluedo.game;
import cluedo.cards.Card;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.cards.Weapon;

/**
 * A solution represents the answer to the Cluedo Game.
 * Will hold one type of each card, a weapon, a character and a room.
 * 
 * Should a player make an accusation matching this Set of cards, they will win the game.
 * If they make an accusation that does not match however, they will be eliminated.
 */
public class Solution {
	
	// The weapon component of the solution
	private Weapon weapon;
	
	// The character component of the solution
	private Character character;
	
	// The room component of the solution
	private Room room;

	/**
	 * A constructor for a solution, takes one weapon card, one character card and one room card
	 * 
	 * @param weapon
	 * @param character
	 * @param room
	 */
	public Solution(Weapon weapon, Character character, Room room) {
		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}
	
	/**
	 * Getter for the room component of the solution
	 * @return
	 */
	private Room getRoom() {
		return room;
	}

	/**
	 * Getter for the character component of the solution
	 * @return
	 */
	private Character getCharacter() {
		return character;
	}

	/**
	 * Getter for the weapon component of the solution
	 * @return
	 */
	private Weapon getWeapon() {
		return weapon;
	}

	/**
	 * A method to compare two solution objects together
	 * @param guess - The solution we are comparing this instance to
	 * @return
	 */
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

	/**
	 * A method to compare a specific component of the solution
	 * @param c
	 * @return
	 */
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

	/**
	 * A method to compare equality between solutions
	 * Two solutions are considered equal if the respective components of each solution are considered
	 * equal to each other
	 * 
	 * @param solution
	 * @return
	 */
	public boolean equals(Solution solution) {
		if ((this.weapon.equals(solution.weapon)) && (this.character.equals(solution.character)) && (this.room.equals(solution.room))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A toString method for a solution
	 */
	public String toString() {
		return "Room: " + this.room.toString() + "\nCharacter: " + this.character.toString() + "\nWeapon: "
				+ this.weapon.toString();
	}

}
