
public class BST {

	private Node root;

	public BST(int i) {
		this.root = new Node(i);
	}

	public Node insert(int i) {
		if (root == null)
			return root = new Node(i);

		return insert(root, new Node(i));
	}

	public Node insert(Node parent, Node newNode) {
		boolean goLeft = parent.v >= newNode.v;
		
		if (goLeft && parent.l == null)
			return parent.l = newNode;

		if (!goLeft && parent.r == null)
			return parent.r = newNode;

		return insert(goLeft ? parent.l : parent.r, newNode);
	}

	public Node findCommonAncestor(Node p, Node q) {
		if (p == null || q == null)
			return null;
		
		if (covers(p, q))
			return p;

		return commonAncestor(p, q);
	}

	private Node commonAncestor(Node p, Node q) {
		if (p.p == null)
			return null;

		if (covers(p.getSibling(), q))
			return p.p;

		return commonAncestor(p.p, q);
	}

	private boolean covers(Node covering, Node covered) {
		if (covering == null)
			return false;
		if (covering == covered)
			return true;

		return covers(covering.l, covered) || covers(covering.r, covered);
	}

	public static class Node {
		public int v;
		public Node p;
		public Node l;
		public Node r;

		public Node(int v) {
			this.v = v;
		}

		public Node(int v, Node l, Node r) {
			this.v = v;
			this.l = l;
			this.r = r;
		}

		public Node getSibling() {
			if (this.p == null)
				return null;

			return this == this.p.r ? this.p.l : this.p.r;
		}
	}
}
