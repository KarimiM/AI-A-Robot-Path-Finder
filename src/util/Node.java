package util;

import java.util.ArrayList;
import java.util.List;

public class Node {

	public Tile data;
	
	public Node parent;
	
	public List<Node> children = new ArrayList<>();
	
	public Node(Tile data) {
		this.data = data;
	}
}
