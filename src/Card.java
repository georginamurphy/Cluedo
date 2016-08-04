/*
 * A simple interface that allows us to generalize Room, Character and Weapon objects as Cards.
 * Cards are used to store the Solution for the game, and the hand that each player holds.
 */
public interface Card {
	
	public String toString();
}
