
public class BST {
	
	Node root;

	public BST insert(int v) {
		if (root == null)
			root = new Node(v);
		else
			insert(root, new Node(v));
		
		return this;	
	}

	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(Node node) {
		if (node == null)
			return;

		inOrder(node.l);
		System.out.println(node.v);
		inOrder(node.r);
	}

	public void insert(Node parent, Node node) {
		parent.size++;
		Node nextParent = parent.v < node.v ? parent.r : parent.l;

		if (nextParent != null)
			insert(nextParent, node);
		else
			if (parent.v < node.v)
				parent.r = node;
			else
				parent.l = node;
	}

	public Node random() {
		int index = (int) (Math.random() * root.size) + 1;
		return get(index);
	}

	public Node get(int index) {
		return get(root, index);
	}

	public Node get(Node node, int index) {
		int rightIndex = index - (node.l != null ? node.l.size : 0) - 1;
		System.out.println("Node: " + node.v + " index: " + index + " right index: " + rightIndex);
		return rightIndex == 0 ? node : (rightIndex > 0 ? get(node.r, rightIndex) : get(node.l, index));
	}
	
}

class Node {
	
	int v;
	Node l;
	Node r;
	int size;

	Node(int v) {
		this.v = v;
		this.size = 1;
	}
}
