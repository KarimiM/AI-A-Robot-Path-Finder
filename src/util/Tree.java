package util;

public class Tree {
	
	public Node root;
	
	public Node current;
	
	
	
	public Tree(Tile root) {
		this.root = new Node(root);
		this.current = this.root;
	}
	
	public Node getRoot() {
		return root;
	}

	public void printTree(Node head) {
		if (head == null) {
			return;
		}
		System.out.println("Tile: " + head.data.x + ",  " + head.data.y);
		for (Node n : head.children) {
			System.out.println("Tile: " + n.data.x + ",  "  + n.data.y);
			printTree(n);
		}
	}
	
	public Node getNode(Tile t, Node root) {
		if (root == null) {
			return null;
		}
		if (root.data == t) {
			return root;
		}
		if (root.children == null || root.children.size() == 0) {
			return null;
		}
		for (Node n : root.children) {
			Node check = getNode(t, n);
			if (check != null) {
				return check;
			}
		}
		return null;
	}
	
	public Node findLastChild(Node start) {
		Node ptr = start;
		if (ptr.children.size() > 0) {
			Node longestChild = ptr.children.get(0);
			int length = ptr.children.get(0).children.size();
			for (Node n : ptr.children) {
				if (n.children.size() > length) {
					length = n.children.size();
					longestChild = n;
				}
			}
			return findLastChild(longestChild);
		} else {
			return ptr;
		}
	}
}
