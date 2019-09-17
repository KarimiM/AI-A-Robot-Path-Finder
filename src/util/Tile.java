package util;

import java.io.Serializable;

public class Tile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7879269707560672767L;

	public boolean blocked;
	
	public boolean seen;
	
	public boolean StoppedHere;
	
	public double g,h,f;
	
	public final int x, y;

	public int search;
	
	public boolean isBlocked() {
		return blocked && seen;
	}
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		seen = false;
		blocked = false;
		h = Double.MIN_VALUE;
		StoppedHere = false;
	}
	
	public double getH(Tile end) {
		return Math.max(Math.abs(end.x-x)+Math.abs(end.y-y),h);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Tile) || object == null) {
			return false;
		}
		Tile t = (Tile) object;
		return t.x == this.x && t.y == this.y;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
}
