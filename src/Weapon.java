public class Weapon {
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
}
