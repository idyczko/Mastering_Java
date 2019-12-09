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

			this.depth = Math.max(this.left != null ? this.left.depth : 0, this.right != null ? this.right.depth : 0) + 1;

			if (Math.abs((this.left != null ? this.left.depth : 0) - (this.right != null ? this.right.depth : 0)) > 1) {
				System.out.println("Inbalance detected at node: " + this.value + " left depth: " + (this.left != null ? this.left.depth : 0) + " right depth: " + (this.right != null ? this.right.depth : 0));
				this.rotate(tree);
			}

			this.depth = Math.max(this.left != null ? this.left.depth : 0, this.right != null ? this.right.depth : 0) + 1;

		}

		void rotate(AvlTree tree) {
			this.rotateRight(tree);
		}

		void rotateRight(AvlTree tree) {
			if (this.parent != null){
				if(this == this.parent.left) {
					this.parent.left = this.left;
				} else {
					this.parent.right = this.left;
				}
			} else {
				tree.root = this.left;
				this.left.parent = null;
			}


			this.parent = this.left;
			this.left = this.left.right;
			this.parent.right = this;

			if (this.left != null)
				this.left.parent = this;

			this.depth = Math.max(this.right != null ? this.right.depth : 0, this.left != null ? this.left.depth : 0) + 1;
			this.parent.depth = (this.parent.left.depth > this.depth ? this.parent.left.depth : this.depth) + 1;

		}
		
	}
}
