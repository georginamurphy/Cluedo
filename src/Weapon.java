public class Weapon implements Card{
	public Type name;
	
	public enum Type{ 
		CANDLESTICK,
		DAGGER,
		LEADPIPE,
		ROPE,
		SPANNER,
		REVOLVER;
	}
	
	/**
	 * @param weapon
	 */
	public Weapon(Type name) {
		this.name = name;
	}
	
	public boolean equals(Weapon weapon){
		if(this.name == weapon.name){return true;}
		return false;
	}
	
	public String toString(){
		return this.name.toString();
	}
}
