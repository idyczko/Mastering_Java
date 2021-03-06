
public class Main {

	public static void main(String[] args) {
	
		BST tree = new BST(10);
		tree.insert(5);
		BST.Node t = tree.insert(1);
		tree.insert(3);
		tree.insert(11);
		tree.insert(10);
		tree.insert(211);
		tree.insert(11);
		BST.Node p = tree.insert(11);
		tree.insert(21);
		BST.Node q = tree.insert(13);
		tree.insert(16);
		tree.insert(15);
		
		tree.inOrder();
		System.out.println("Common ancestor of p and q: " + tree.findAncestorSmart(tree.root, p).v);
		System.out.println("Common ancestor of p and q: " + tree.findAncestorSmart(q, tree.root).v);
		System.out.println("Common ancestor of p and q: " + tree.findAncestorSmart(q, p).v);
	}
}
