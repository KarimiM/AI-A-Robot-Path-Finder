package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import util.BinaryHeap;
import util.Grid;
import util.Node;
import util.Tile;
import util.Tree;

public class ForwardAStar {

	private Grid maze;
	private Tile start,end;
	private final BinaryHeap heap;
	private ArrayList<Tile> closed = new ArrayList<>();
	int counter = 0;
	private Tree tree;
	ArrayList<Tile> FinalWay = new ArrayList<>();
	ArrayList<ArrayList<Tile>> GUIUpdated = new ArrayList<>();
	
	ForwardAStar(Grid grid, int startx, int starty, int endx, int endy){
		this.maze = grid;
		start = grid.getGrid()[startx][starty];
		grid.updateVisability(start);
		end = grid.getGrid()[endx][endy];
		heap = new BinaryHeap(grid.getLength() * grid.getWidth());
		tree = new Tree(grid.getGrid()[startx][starty]);
	}
    
	public double findPath(boolean greater) {
		for (Tile[] tiles: maze.getGrid()) {
			for (Tile t: tiles) {
				t.search = 0;
			}
		}
		while (!start.equals(end)) {
			//Resetting StoppedHere to false now
			counter++;
			start.g = 0;
			start.search = counter;
			end.g = Integer.MAX_VALUE;
			end.search = counter;
			heap.clear();
			closed.clear();
			start.f = start.g+start.getH(end);
			heap.add(start, greater);
			computePath(greater);
			if (heap.isEmpty()) {
				System.out.println("I cannot reach the target");
				break;
			}
			Node curr = tree.getNode(end, tree.getRoot());
			ArrayList<Tile> theWay = new ArrayList<>();
			while (!curr.data.equals(start)) {
				theWay.add(curr.data);
				curr = curr.parent;
			}
			theWay.add(start);
			Collections.reverse(theWay);
			Tile newStart = theWay.get(0);
			ArrayList<Tile> addedWay = new ArrayList<>();
			FinalWay.add(newStart);
			addedWay.add(newStart);
			for (int i = 1;i<theWay.size();i++) {
				Tile tile = theWay.get(i);
				if (tile.equals(end)) {
					System.out.println("Reached the Target");
	                start = end;			
				}
			    if (tile.blocked) {
			    	start = newStart;
			    	start.StoppedHere = true;
	                break;
			    }
			    FinalWay.add(tile);
			    addedWay.add(tile);
			    //removed if statement for if tile is already in FinalWay
			    newStart = tile;
			    maze.updateVisability(tile);
			}
			tree = new Tree(newStart);
			//INFO ON UPDATING EACH ITERATION
			// below is the arraylist of arraylists. 
			// addedWay is only what was added to FinalWay during the current iteration
			// Also take a copy of the maze so that you know what is now seen/unseen and blocked/unblocked
			
			GUIUpdated.add(addedWay);
			// Implement a type of print statement here
		}
		return maze.printGrid(FinalWay);
	}
	
	private void computePath(boolean greater) {
		//System.out.println("Computing Path");
		while (end.g > heap.minElement().f) {
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
