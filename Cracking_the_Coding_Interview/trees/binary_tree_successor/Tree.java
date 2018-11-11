import java.util.concurrent.atomic.*;

public class Tree {

	private Node root;

	public Tree() {}

	public Tree(Node root) {
		this.root = root;
	}

	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(Node n) {
		if (n == null)
			return;

		inOrder(n.l);
		System.out.println(n.v);
		inOrder(n.r);
	}

	public boolean isBST() {
		return isBST(root, new AtomicInteger(root.v), new AtomicInteger(root.v));
	}

	private boolean isBST(Node n, AtomicInteger min, AtomicInteger max) {
		AtomicInteger lMax = new AtomicInteger(n.v);
		AtomicInteger lMin = new AtomicInteger(n.v);
		AtomicInteger rMax = new AtomicInteger(n.v);
		AtomicInteger rMin = new AtomicInteger(n.v);

		boolean l = (n.l == null ? true : isBST(n.l, lMin, lMax));
		boolean r = (n.r == null ? true : isBST(n.r, rMin, rMax));

		if (!r || !l || (n.l != null && n.v < lMax.get()) || (n.r != null && n.v >= rMin.get())) {
			System.out.println("Returning false for node " + n.v + ". min: " + min.get() + " max: " + max.get() + " lMax: " 
					+ lMax.get() + " rMin: " + rMin.get());
			return false;
		}

		min.set(Math.min(lMin.get(), rMin.get()));
		max.set(Math.max(lMax.get(), rMax.get()));

		return true;
	}

	public Node insert(int v) {
		if (this.root == null) {
			this.root = new Node(v, null, null);
			return root;
		}
		Node newNode = new Node(v, null, null);
		Node parent = root;
		while (parent != null) {
			if (parent.v < v) {
				if (parent.r == null) {
					parent.r = newNode;
					newNode.p = parent;
					break;
				}
				parent = parent.r;
			}
			else {
				if (parent.l == null) {
					parent.l = newNode;
					newNode.p = parent;
					break;
				}
				parent = parent.l;
			}
		}

		return newNode;
	}


	public static class Node {
	
		public int v;
		public Node p;
		public Node l;
		public Node r;

		public Node(int v, Node l, Node r) {
			this.v = v;
			this.l = l;
			this.r = r;
		}

		public Node findSuccessor() {
			Node node = this;
			while (node.p != null) {
				if (node.p.l == this)
					return node.p;
				node = node.p;
			}
			Node subTreeSuccessor = this.r;
			while (subTreeSuccessor.l != null)
				subTreeSuccessor = subTreeSuccessor.l;

			return subTreeSuccessor;
		}

	}
}
