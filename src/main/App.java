package main;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Grid;
import util.Tile;


public class App extends Application {
	
	static Grid maze;
	static ArrayList<Tile> path;
	
	int current = 0;
	
	Tile[] currentSeen = null;
	
	ArrayList<Tile> previouslySeen = new ArrayList<>();
	Label xLabel = null;
	@Override
	public void start(Stage primaryStage) throws Exception {
		int length = maze.length;
		int width  = maze.width;
		VBox box = new VBox();
		ScrollPane scroll = new ScrollPane();
		GridPane grid = new GridPane();
		Collections.reverse(path);
	    for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				boolean blocked = maze.grid[i][j].blocked;
				if (currentlySeen(maze.grid[i][j])) {
					continue;
				}
				boolean last = false;
				if (blocked) {
					Region rec = new Region();
		            rec.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
		            GridPane.setRowIndex(rec, j);
		            GridPane.setColumnIndex(rec, i);
		            grid.getChildren().addAll(rec);
				} /*else if (path != null && maze.containsTile(path, maze.grid[i][j])) {
					Region rec = new Region();
					String color = maze.grid[i][j].StoppedHere ? "yellow" : "green";
		            rec.setStyle("-fx-background-color: " + color + "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
					if (path.get(0).equals(maze.grid[i][j])) {
						Group g = new Group();
						g.getChildren().add(rec);
						g.getChildren().add(new Label(" E"));
			            GridPane.setRowIndex(g, j);
			            GridPane.setColumnIndex(g, i);
			            grid.getChildren().addAll(g);
					} else if(path.get(path.size() - 1).equals(maze.grid[i][j])) {
						Group g = new Group();
						g.getChildren().add(rec);
						g.getChildren().add(new Label(" S"));
			            GridPane.setRowIndex(g, j);
			            GridPane.setColumnIndex(g, i);
			            grid.getChildren().addAll(g);
					} else {
			            GridPane.setRowIndex(rec, j);
			            GridPane.setColumnIndex(rec, i);
			            grid.getChildren().addAll(rec);
					}
			    } */
			 else if(path.get(path.size() - 1).equals(maze.grid[i][j]) || (last = path.get(0).equals(maze.grid[i][j]))) {
				 
				 current = path.size() - 1;
					Region rec = new Region();
				 String color = "green";
	            rec.setStyle("-fx-background-color: " + color + "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
				
				Group g = new Group();
				g.getChildren().add(rec);
				g.getChildren().add(new Label(last ? " E" : " S"));
	            GridPane.setRowIndex(g, j);
	            GridPane.setColumnIndex(g, i);
	            grid.getChildren().addAll(g);
	            if (!last) {
	            Tile[] nearby = maze.getNearbyTiles(maze.grid[i][j]);
	            currentSeen = nearby;
	            for (Tile check : nearby) {
	               	if (check != null && !pathContains(check) && !containsCheck(check)) {
	               			rec = new Region();
	               			g = new Group();
	    		            rec.setStyle("-fx-background-color: cyan; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
	    					g.getChildren().add(rec);
	    					if (check.blocked) {
	    						g.getChildren().add(new Label(" B"));
	    		            }
	    		            GridPane.setRowIndex(g, check.y);
	    		            GridPane.setColumnIndex(g, check.x);
	    		            grid.getChildren().addAll(g);
	    		            previouslySeen.add(check);
	               	}
	               }
	            }
			} else {
			    	Region rec = new Region();
		            rec.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
		            GridPane.setRowIndex(rec, j);
		            GridPane.setColumnIndex(rec, i);
		            grid.getChildren().addAll(rec);
				}
			}
	    }
	    int dim = 120 * length > 1200 ? 1200 : 120 * length;
	    scroll.setContent(grid);
	    scroll.setMaxWidth(dim);
	    scroll.setMaxHeight(dim);
	    box.getChildren().add(scroll);
	    Button next = new Button("Next");
	    next.setOnMouseClicked((ev) -> {
	    	if (xLabel != null) {
	    		xLabel.setText(" -");
	    		xLabel = null;
	    	}
	    	if (current  == 0) {
	    		return;
	    	}
	    	for (Tile t : currentSeen) {
	    		if (t != null) {
	    			if (t.blocked) {
	    				Region rec = new Region();
			            rec.setStyle("-fx-background-color: red; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
			            GridPane.setRowIndex(rec, t.y);
			            GridPane.setColumnIndex(rec, t.x);
			            grid.getChildren().addAll(rec);
	    			} else if (!pathContains(t)){
	    				Region rec = new Region();
			            rec.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
			            GridPane.setRowIndex(rec, t.y);
			            GridPane.setColumnIndex(rec, t.x);
			            grid.getChildren().addAll(rec);
	    			}
	    		}
	    	}
	    	Tile t = path.get(--current);
	    	Region rec = new Region();
			 String color = t.StoppedHere ? "yellow" : "green";
			 boolean start = false;
			 if (path.get(path.size() - 1).equals(t)) {
				 color = "orange";
				 start = true;
			 }
           rec.setStyle("-fx-background-color: " + color + "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
			
			Group g = new Group();
			g.getChildren().add(rec);
			if (current == 0) {
				g.getChildren().add(new Label(" E"));
				
			} else if (start){
				g.getChildren().add(new Label(" S"));
				
			} else {
				xLabel = new Label(" X");
				g.getChildren().add(xLabel);
			}
           GridPane.setRowIndex(g, t.y);
           GridPane.setColumnIndex(g, t.x);
           grid.getChildren().addAll(g);
           Tile[] nearby = maze.getNearbyTiles(t);
           currentSeen = nearby;
           for (Tile check : nearby) {
           	if (check != null && !pathContains(check) && !containsCheck(check)) {
           			rec = new Region();
           			g = new Group();
		            rec.setStyle("-fx-background-color: cyan; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
					g.getChildren().add(rec);
					if (check.blocked) {
						g.getChildren().add(new Label(" B"));
		            } else if (check.equals(path.get(0))) {
						g.getChildren().add(new Label(" E"));
		            	
		            }
		            GridPane.setRowIndex(g, check.y);
		            GridPane.setColumnIndex(g, check.x);
		            grid.getChildren().addAll(g);
		            previouslySeen.add(t);
           	}
           }
	    });
	    
	    box.getChildren().add(next);
	    Scene scene = new Scene(box, dim, dim);
	    primaryStage.setScene(scene);
	    primaryStage.setMinHeight(120);
	    primaryStage.setMinWidth(100);
	    primaryStage.show();
	}
	
	public boolean currentlySeen(Tile check) {
		if (currentSeen == null) {
			return false;
		}
		for (Tile t : currentSeen) {
			if (t  != null && t.equals(check)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsCheck(Tile check) {
		for (Tile t : previouslySeen) {
			if (t.equals(check)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean pathContains(Tile check) {
		for (int i = path.size() - 1; i > current; i--) {
			if (path.get(i).equals(check)) {
				return true;
			}
		}
		return false;
	}
	public static void showGrid(Grid grid, ArrayList<Tile> paths) {
		maze = grid;
		path = paths;
		launch();
	}
}
