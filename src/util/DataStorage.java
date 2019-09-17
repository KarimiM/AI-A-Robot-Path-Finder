package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataStorage {
	
	public static final String FILE_NAME = "./SolvedGridData.ai";
	
	public static final ArrayList<SolvedGrid> SOLVED_GRIDS = new ArrayList<>();
	
	/**
	 * Saves all the grids.
	 */
	public static void saveAllGrids() {
		try {
			FileOutputStream file = new FileOutputStream(FILE_NAME);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeInt(SOLVED_GRIDS.size());
			for (SolvedGrid g : SOLVED_GRIDS) {
				out.writeObject(g);
			}
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the users of the PhotoLibrary application and returns them in an array list.
	 * @return An ArrayList of users.
	 */
	public static void parseGrids() {
        try {
        	FileInputStream file = new FileInputStream(FILE_NAME); 
			ObjectInputStream in = new ObjectInputStream(file);
			int length = in.readInt();
			for (int i = 0; i < length; i++) {
				SolvedGrid grid = (SolvedGrid) in.readObject();
				SOLVED_GRIDS.add(grid);
			}
			in.close();
			file.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
