package util;
import java.io.Serializable;
import java.util.ArrayList;


public class SolvedGrid implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7283354771175345988L;
	
	private final Grid grid;
	
	private final ArrayList<Tile> path;
	
	public SolvedGrid(Grid grid, ArrayList<Tile>path) {
		this.grid = grid;
		this.path = path;
	}

	/**
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * @return the path
	 */
	public ArrayList<Tile> getPath() {
		return path;
	}
	
	
}
