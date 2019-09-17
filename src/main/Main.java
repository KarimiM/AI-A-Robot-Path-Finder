package main;

import java.util.ArrayList;

import util.DataStorage;
import util.Grid;
import util.SolvedGrid;
import util.Tile;

public class Main {

	public static boolean GUI_ENABLED = true;
	
	public static long startTime = 0;
	
	public static void main(String[] args) {
		Grid grid = new Grid(40, 40);
		Tile[] tiles = grid.generateMaze();
		
		
		/*
		 * Enable this and disable other code to test avg runtime*/
		/*GUI_ENABLED = false;
		int runs = 50;
		double totalRunTime = 0;
		Grid[] mazes = new Grid[50];
		ArrayList<Tile[]> tilesM = new ArrayList<>();
		for (int i = 0;i<runs;i++) {
			mazes[i] = new Grid(101,101);
			tilesM.add(mazes[i].generateMaze());
		}
		for (int i = 0; i < runs; i++) {
			Grid c = mazes[i];
			startTime = System.nanoTime();
			ForwardAStar finder = new ForwardAStar(c, tilesM.get(i)[0].x, tilesM.get(i)[0].y, tilesM.get(i)[1].x, tilesM.get(i)[1].y);
			totalRunTime+=finder.findPath(true);
		}
		System.out.println("True Avg runtime: " + (totalRunTime / runs) + "(MS)");
		totalRunTime = 0;
		for (int i = 0; i < runs; i++) {
			Grid c = mazes[i];
			startTime = System.nanoTime();
			ForwardAStar finder = new ForwardAStar(c, tilesM.get(i)[0].x, tilesM.get(i)[0].y, tilesM.get(i)[1].x, tilesM.get(i)[1].y);
			totalRunTime+=finder.findPath(false);
		}
		System.out.println("False Avg runtime: " + (totalRunTime / runs) + "(MS)");*/
		
		
		//Test GUI with this
		AdaptiveAStar finder = new AdaptiveAStar(grid, tiles[0].x, tiles[0].y, tiles[1].x, tiles[1].y);
		finder.findPath(true);
		
		//DataStorage.parseGrids();
		//SolvedGrid grid = DataStorage.SOLVED_GRIDS.get(0);
		//grid.getGrid().printGrid(grid.getPath());
	}
}
