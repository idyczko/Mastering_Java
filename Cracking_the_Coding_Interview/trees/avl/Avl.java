import java.util.*;


public class Avl {

	private static AvlTree tree = new AvlTree();

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		while (true) {
			String line = sc.nextLine();
			if (line.startsWith("INSERT")) {
				String[] command = line.split(" ");
				tree.insert(Integer.valueOf(command[1]));
			} else if (line.startsWith("SEARCH")) {
				String[] command = line.split(" ");
				tree.search(Integer.valueOf(command[1]));
			}
		}
	}

	private static class AvlTree {
		
		Node root;
	
		void insert(Integer i) {
			System.out.println("Inserting " + i);
			Node node = new Node(i);
			this.insert(node);
		}
		
		void insert(Node node) {
			
			if (node.left != null || node.right != null)
				throw new IllegalArgumentException();

			if (this.root == null) {
				this.root = node;
				return;
			}

			root.insert(this, node);
		}

		void search(Integer i) {
			search(root, i, 0);
		}

		void search(Node node, Integer i, int depth) {

			if (node == null) {
				System.out.println("The tree does not contain given node");
				return;
			}

			if (i.equals(node.value)) {
				System.out.println("The node " + i + " has been found on the level " + depth);
				return;
			}

			if (i <= node.value)
				search(node.left, i, depth + 1);
			else
				search(node.right, i, depth + 1);
		}
	}

	private static class Node {
		
		int depth;
		int value;
		Node parent;
		Node left;
		Node right;

		Node(int value) {
		 	this.depth = 1;
			this.value = value;
		}

		Node(int value, Node left, Node right) {
			this(value);
			this.left = left;
			this.right = right;
		}

		void insert(AvlTree tree, Node node) {
			
			if (node.value <= this.value) {
				if (this.left != null) {
					this.left.insert(tree, node);
					
				} else {
					node.parent = this;
					this.left = node;
					
				}
			} else {
			
				if (this.right != null) {
					this.right.insert(tree, node);

				} else {
					node.parent = this;
					this.right = node;

				}
			}


			if (Math.abs((this.left != null ? this.left.depth : 0) - (this.right != null ? this.right.depth : 0)) > 1) {
				System.out.println("Inbalance detected at node: " + this.value + " left depth: " + (this.left != null ? this.left.depth : 0) + " right depth: " + (this.right != null ? this.right.depth : 0));
				this.rotate(tree);
			}

			this.updateDepth();
		}

		void rotate(AvlTree tree) {
			if ((this.left != null ? this.left.depth : 0) > (this.right != null ? this.right.depth : 0)) {
				Node rotate = null;
				if (this.left.left == null) {
					rotate = this.left.right;
					rotate.rotateLeft(tree);
				} else {
					rotate = this.left;
				}
				rotate.rotateRight(tree);
			} else {
				Node rotate = null;
				if (this.right.right == null) {
					rotate = this.right.left;
					rotate.rotateRight(tree);
				} else {
					rotate = this.right;
				}
				rotate.rotateLeft(tree);
			}

		}

		void rotateLeft(AvlTree tree) {

			if (this.parent == null || this != this.parent.right)
				throw new IllegalArgumentException();

			
			if (this.parent.parent == null)
				tree.root = this;
			else
				if (this.parent == this.parent.parent.left)
					this.parent.parent.left = this;
				else
					this.parent.parent.right = this;

			this.parent.right = this.left;
			
			if (this.left != null)
				this.left.parent = this.parent;
			
			this.left = this.parent;
			this.parent = this.left.parent;
			this.left.parent = this;
			this.left.updateDepth();
		}

		void rotateRight(AvlTree tree) {
			if (this.parent == null || this != this.parent.left)
				throw new IllegalArgumentException();

			
			if (this.parent.parent == null)
				tree.root = this;
			else
				if (this.parent == this.parent.parent.left)
					this.parent.parent.left = this;
				else
					this.parent.parent.right = this;

			this.parent.left = this.right;

			if (this.right != null)
				this.right.parent = this.parent;
			
			this.right = this.parent;
			this.parent = this.right.parent;
			this.right.parent = this;
			this.right.updateDepth();
	
		}

		void updateDepth() {
			this.depth = Math.max(this.left != null ? this.left.depth : 0, this.right != null ? this.right.depth : 0) + 1;

			if (this.parent != null)
				this.parent.updateDepth();
		}
		
	}
}
