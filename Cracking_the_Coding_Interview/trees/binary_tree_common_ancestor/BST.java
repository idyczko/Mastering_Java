
public class BST {

	public Node root;

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
		
		if (goLeft && parent.l == null) {
			newNode.p = parent;
			return parent.l = newNode;
		}

		if (!goLeft && parent.r == null) {
			newNode.p = parent;
			return parent.r = newNode;
		}

		return insert(goLeft ? parent.l : parent.r, newNode);
	}

	public Node findCommonAncestor(Node p, Node q) {
		if (p == null || q == null)
			return null;
		
		if (covers(p, q))
			return p;

		return commonAncestor(p, q);
	}

	public Node findCommonAncestorWithoutParentLink(Node p, Node q) {
		if (!covers(root, p) || !covers(root, q))
			return null;
		
		if (root == p || root == q)
			return root;

		return findCAWPL(root, p, q);
	}

	public Node findAncestorSmart(Node p, Node q) {

		Result r = findAncestorSmart(root, p, q);
		return r.isAncestor ? r.ancestor : null;
	}

	private Result findAncestorSmart(Node root, Node p, Node q) {
		if (root == null)
			return new Result(false, null);

		if (root == p && root == q)
			return new Result(true, root);

		Result x = findAncestorSmart(root.l, p, q);
		if (x.isAncestor)
			return x;

		Result y = findAncestorSmart(root.r, p, q);
		if (y.isAncestor)
			return y;

		if (x.ancestor != null && y.ancestor != null)
			return new Result(true, root);
		else if (root == p || root == q)
			return new Result(x.ancestor != null || y.ancestor != null, root);
		else
			return x.ancestor == null ? y : x;
	}


	private Node findCAWPL(Node parent, Node p, Node q) {
		boolean pIsLeft = covers(parent.l, p);
		boolean qIsLeft = covers(parent.l, q);

		if (pIsLeft != qIsLeft)
			return parent;

		return findCAWPL(pIsLeft ? parent.l : parent.r, p, q);
	}

	public void inOrder() {
		inOrder(this.root);
	}

	private void inOrder(Node node) {
		if (node == null)
			return;

		inOrder(node.l);
		System.out.println(node.v);
		inOrder(node.r);
	}

	private Node commonAncestor(Node p, Node q) {
		if (p == q)
			return p;

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

	private class Result {
		boolean isAncestor;
		Node ancestor;

		Result(boolean isAncestor, Node ancestor) {
		this.isAncestor = isAncestor;
		this.ancestor = ancestor;
		}
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
