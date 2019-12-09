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


		}

		void rotate(AvlTree tree) {
			this.rotateRight(tree);
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

			this.left = this.parent;
			this.left.right = null;
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

			this.right = this.parent;
			this.right.left = null;
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
