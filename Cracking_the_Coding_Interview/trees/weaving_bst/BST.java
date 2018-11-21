import java.util.*;

public class BST {
	
	public Node root;

	public BST(int i) {
		this.root = new Node(i);
	}

	public void weave() {
		weave(this.root, new ArrayList<>(), new HashSet<>());
	}

	private void weave(Node node, List<Integer> seq, Set<Node> moves) {
		if (node == null)
			return;

		seq.add(node.v);
		if (node.l != null)
			moves.add(node.l);
		if (node.r != null)
			moves.add(node.r);
		if (moves.isEmpty()) {
			System.out.print("[ ");
			seq.forEach(item -> System.out.print(item + " "));
			System.out.print("]\n");
			return;
		}

		moves.forEach(move -> {
			Set<Node> newMoves = new HashSet<>(moves);
			List<Integer> newSeq = new ArrayList<>(seq);
			newMoves.remove(move);
			weave(move, newSeq, newMoves);	
		});
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
