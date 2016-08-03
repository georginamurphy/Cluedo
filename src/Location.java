public class Location {
	public int x;
	public int y;
	public BoardPiece type;
	
	/**
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void moveUp(){
		y--;
	}
	
	public void moveDown(){
		y++;
	}
	
	public void moveLeft(){
		x--;
	}
	
	public void moveRight(){
		x++;
	}
	
	public boolean equals(Location location){
		if(this.x == location.x){
			if(this.y == location.y){
				return true;
			}
		}
		return false;
	}
}
