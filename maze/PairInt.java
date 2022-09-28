package maze;

public class PairInt {
	
	private int x;
	private int y;
	
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Object p) {
		PairInt P = (PairInt) p;
		
		if(p != null) {
			if(P.x == this.x && P.y == this.x) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public PairInt copy() {
		PairInt copyOf = new PairInt(x, y);
		return copyOf;
	}
}
