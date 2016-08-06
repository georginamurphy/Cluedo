package cluedo.cards;

/**
 * A weapon card for the Cluedo game.
 */
public class Weapon implements Card{
	
	// The name of the weapon
	public Type name;
	
	/**
	 * An enum representing the different names for weapons in Cluedo
	 */
	public enum Type{ 
		CANDLESTICK,
		DAGGER,
		LEADPIPE,
		ROPE,
		SPANNER,
		REVOLVER;
	}
	
	/**
	 * A constructor for a weapon card, takes a Weapon.Name
	 * @param name
	 */
	public Weapon(Type name) {
		this.name = name;
	}
	
	/**
	 * A method to check equality between two weapon cards
	 * Two weapons are considered equal if they share the same Weapon.Name
	 * @param weapon
	 * @return
	 */
	public boolean equals(Weapon weapon){
		if(this.name == weapon.name){return true;}
		return false;
	}
	
	/**
	 * A toString method for a weapon
	 */
	public String toString(){
		return this.name.toString();
	}
}
