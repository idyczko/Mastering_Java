
public class BST {

	private Node root;

	public BST(int i) {
		this.root = new Node(i);
	}

	public Node insert(int i) {
		if (root == null)
			return root = new Node(i);

		Node parent = root;
		while (true)
	}

	public Node commonAncestor(Node p, Node q) {
		if (p == null)
			return null;
		
		if (covers(p, q))
			return p;

		return p.p == q ? p.p : commonAncestor(p.p, q);
	}

	private boolean covers(Node covering, Node covered) {
		if (covering == covered)
			return true;


	}

	private Node insert (Node newNode, Node parent) {
		boolean goLeft = newNode.v <= parent.v;
		Node nextParent = goLeft ? parent.l : parent.p;
		if (nextParent == null)
			if (goLeft)
				return parent.l = newNode;
			else
				return parent.r = newNode;

		return insert(newNode, nextParent);
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
	}
}
