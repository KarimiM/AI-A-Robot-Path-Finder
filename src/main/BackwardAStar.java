package main;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import util.BinaryHeap;
import util.Grid;
import util.Node;
import util.Tile;
import util.Tree;

public class BackwardAStar {

	private Grid maze;
	private Tile start,end;
	private BinaryHeap heap;
	private ArrayList<Tile> closed = new ArrayList<>();
	int counter = 0;
	private Tree tree, masterTree;
	private final ArrayList<Tile> path = new ArrayList<>();
	ArrayList<Tile> FinalWay = new ArrayList<>();
	
	BackwardAStar(Grid maze, int startx, int starty, int endx, int endy){
		this.maze = maze;
		start = maze.getGrid()[startx][starty];
		maze.updateVisability(start);
		end = maze.getGrid()[endx][endy];
		heap = new BinaryHeap(maze.getLength() * maze.getWidth());
		tree = new Tree(maze.getGrid()[endx][endy]);
	}

	public double findPath(boolean greater) {
		for (Tile[] tiles: maze.getGrid()) {
			for (Tile t: tiles) {
				t.search = 0;
			}
		}
		while (!start.equals(end)) {
			//Resetting the StoppedHere value
			counter++;
			end.g = 0;
			start.search = counter;
			start.g = Integer.MAX_VALUE;
			end.search = counter;
			heap.clear();
			closed.clear();
			end.f = end.g+start.getH(end);
			heap.add(end, greater);
			computePath(greater);
			if (heap.isEmpty()) {
				System.out.println("I cannot reach the target");
				break;
			}
			Node curr = tree.getNode(start, tree.getRoot());
			ArrayList<Tile> theWay = new ArrayList<>();
			while (!curr.data.equals(end)) {
				theWay.add(curr.data);
				curr = curr.parent;
			}
			theWay.add(end);
			Tile newStart = theWay.get(0);
			FinalWay.add(newStart);
			for (int i = 1;i<theWay.size();i++) {
				Tile tile = theWay.get(i);
				if (tile.equals(end)) {
					System.out.println("Reached the Target");
	                start = end;			
				}
			    if (tile.blocked) {
			    	start = newStart;
	                break;
			    }
			    FinalWay.add(tile);
			    newStart = tile;
			    maze.updateVisability(tile);
			}
			tree = new Tree(end);
			start.StoppedHere = true;
			// Implement a type of print statement here
		}
		return maze.printGrid(FinalWay);
	}
	
	private void computePath(boolean greater) {
		//System.out.println("Computing Path");
		while (start.g > heap.minElement().f) {
			//System.out.println("Running loop " + end.g + ", " + bh.minElement().f);
	       Tile min = heap.delete(0, greater);
		   closed.add(min);
		   Tile[] moveable = maze.moveableTiles(min);
		   filterTiles(moveable);
		   for (Tile t : moveable) {
			   if (t == null) {
				   continue;
			   }
			   if (t.search < counter) {
				   t.g = Integer.MAX_VALUE;
				   t.search = counter;
			   }
			   if (t.g > min.g + 1) {
				  // System.out.println("Activating");
				   t.g = min.g + 1;
				   Node node = new Node(t);
				  // System.out.println("Looking for parent: " + min);
				   Node parent = tree.getNode(min, tree.getRoot());
				   if (parent == null) {
					   throw new NoSuchElementException();
				   }
				  // System.out.println("Adding child: " + node.data);
				   node.parent = parent;
				   parent.children.add(node);
				   int index = heap.getIndex(t);
				   if (index != -1) {
					   heap.delete(index, greater);
				   }
				   t.f = t.g + t.getH(end);
				   heap.add(t, greater);
			   }
		   }
		}
	}
	
	public void filterTiles(Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {
			Tile check = tiles[i];
			if (check == null) {
				continue;
			}
			for (Tile t : closed) {
				if (check.equals(t)) {
					tiles[i] = null;
				}
			}
		}
	}
	
	
}
