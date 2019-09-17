package util;
import java.io.Serializable;
import java.util.ArrayList;

import main.App;
import main.Main;

public class Grid implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8706201675848118184L;

	/**
	 * The length of the grid.
	 */
	public final int length;
	
	/**
	 * The width of the grid
	 */
	public final int width;
	
	/**
	 * The grid data
	 */
	public final Tile[][] grid;
	
	/**
	 * All the tiles!
	 */
	public ArrayList<Tile> allTiles = new ArrayList<>();
	
	/**
	 * 
	 * @param length
	 * @param width
	 */
	public Grid(int length, int width) {
		this.length = length;
		this.width = width;
		this.grid = new Tile[length][width];
		generateTiles();
	}

	public Tile[] generateMaze() {
		Tile start = allTiles.remove(getRandomInt(allTiles.size() - 1));
		Tree tree = new Tree(start);
		createPaths(tree, start, true);
		while(!allTiles.isEmpty()) {
			Tile curr = tree.current.data;
			int x = curr.x;
			int y = curr.y;
			boolean visitedAll = true;
			if (x + 1 < length) {
				Tile t = grid[x+1][y];
				visitedAll = allTiles.contains(t) ? false : visitedAll;
			}
			if (x - 1 >= 0) { 
				Tile t = grid[x-1][y];
				visitedAll = allTiles.contains(t) ? false : visitedAll;
			}
			if (y + 1 < width) {
				Tile t = grid[x][y+1];
				visitedAll = allTiles.contains(t) ? false : visitedAll;
			}
			if (y - 1 >= 0) { 
				Tile t = grid[x][y-1];
				visitedAll = allTiles.contains(t) ? false : visitedAll;
			}
			if (visitedAll) {
				if (tree.current.parent == null) {
					System.out.println("Breaking here..");
					break;
				}
				tree.current = tree.current.parent;
				continue;
			}
			createPaths(tree, tree.current.data, false);
		}
		
		Tile end = tree.findLastChild(tree.root).data;
		System.out.println("Start: " + start + " End: " + end);
		return new Tile[] { start, end };
	}
	
	public void createPaths(Tree tree, Tile start, boolean first) {
		int x = start.x;
		int y = start.y;
		ArrayList<Tile> moveable = new ArrayList<Tile>();
		if (x + 1 < length) {
			moveable.add(grid[x+1][y]);
		}
		if (x - 1 >= 0) { 
			moveable.add(grid[x-1][y]);
		}
		if (y + 1 < width) {
			moveable.add(grid[x][y+1]);
		}
		if (y - 1 >= 0) { 
			moveable.add(grid[x][y-1]);
		}
		if (first) {
			Tile t = moveable.get(getRandomInt(moveable.size() - 1));
			if (!allTiles.contains(t)) {
				return;
			}
			allTiles.remove(t);
			Node n = new Node(t);
			tree.current.children.add(n);
			n.parent = tree.current;
			tree.current = n;
			createPaths(tree, tree.current.data, false);
			return;
		}
		for (Tile t : moveable) {
			if (!allTiles.contains(t)) {
				continue;
			}
			allTiles.remove(t);
			int rand = getRandomInt(9);
			if (rand <= 2) {
				t.blocked = true;
				continue;
			}
			Node n = new Node(t);
			n.parent = tree.current;
			tree.current.children.add(n);
			tree.current = n;
			createPaths(tree, tree.current.data, false);
			return;
		}
	}
	
	public int getRandomInt(int max) {
		  return (int) Math.floor(Math.random() * Math.floor(max));
	}
	
	public void generateTiles() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				Tile t = new Tile(i, j);
				grid[i][j] =  t;
				allTiles.add(t);
			}
		}
	}
	
	public void setBlocked(int x, int y) {
		grid[x][y].blocked = true;
	}
	
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the grid
	 */
	public Tile[][] getGrid() {
		return grid;
	}
	
	public void updateVisability(Tile i) {
		int x = i.x;
		int y = i.y;
		Tile[] moveable = new Tile[4];
		if (x + 1 < length) {
			moveable[0] = grid[x+1][y];
		}
		if (x - 1 >= 0) { 
			moveable[1] = grid[x-1][y];
		}
		if (y + 1 < width) {
			moveable[2] = grid[x][y + 1];
		}
		if (y - 1 >= 0) { 
			moveable[3] = grid[x][y - 1];
		}
		int index = 0;
		for (Tile t : moveable) {
			if (t != null) {
				moveable[index].seen = true;
			}
			index++;
		}
	}
	
	public Tile[] moveableTiles(Tile check) {
		int x = check.x;
		int y = check.y;
		Tile[] moveable = new Tile[4];
		if (x + 1 < length) {
			moveable[0] = grid[x+1][y];
		}
		if (x - 1 >= 0) { 
			moveable[1] = grid[x-1][y];
		}
		if (y + 1 < width) {
			moveable[2] = grid[x][y+1];
		}
		if (y - 1 >= 0) { 
			moveable[3] = grid[x][y-1];
		}
		for (int i = 0;i<moveable.length;i++) {
			if (moveable[i] != null && moveable[i].isBlocked()) {
				moveable[i] = null;
			}
		}
		return moveable;
	}
	
	public Tile[] getNearbyTiles(Tile check) {
		int x = check.x;
		int y = check.y;
		Tile[] moveable = new Tile[4];
		if (x + 1 < length) {
			moveable[0] = grid[x+1][y];
		}
		if (x - 1 >= 0) { 
			moveable[1] = grid[x-1][y];
		}
		if (y + 1 < width) {
			moveable[2] = grid[x][y+1];
		}
		if (y - 1 >= 0) { 
			moveable[3] = grid[x][y-1];
		}
		return moveable;
	}
	
	/**
	 * Prints the grid
	 */
	public double printGrid(ArrayList<Tile> path) {
		double totalTime = 0;
		if (path != null) {
		long endTime   = System.nanoTime();
		totalTime = (double) (endTime - Main.startTime) / 1000000;
		System.out.println("Total time (milliseconds) : " + totalTime);
		}
		if (!Main.GUI_ENABLED) {
			return totalTime;
		}
		DataStorage.SOLVED_GRIDS.add(new SolvedGrid(this, path));
		String letters = "  ";
		/*for (int i = 0; i < length; i++) {
			letters += i + " ";
		}
		System.out.println(letters);
		for (int i = 0; i < width; i++) {
			String print = i + " ";
			for (int j = 0; j < length; j++) {
				boolean blocked = grid[j][i].blocked;
				if (blocked) {
					print += "x ";
				} else if (path != null && containsTile(path, grid[j][i])) {
					print += "^ ";
			    } else {
					print += "- ";
				}
			}
			System.out.println(print);
		}*/
		if (path != null) {
			DataStorage.saveAllGrids();
			App.showGrid(this, path);
		}
		return totalTime;
	}
	
	public boolean containsTile(ArrayList<Tile> path, Tile t) { 
		for (Tile tile : path) {
			if (t.equals(tile)) {
				return true;
			}
		}
		return false;
	}

	public Tile getTile(int i, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
