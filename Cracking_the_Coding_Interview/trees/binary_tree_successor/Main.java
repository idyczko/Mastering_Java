

public class Main {

	public static void main(String[] args) {
	
		Tree t = new Tree();
		t.insert(10);
		t.insert(1);
		t.insert(2);
		t.insert(3);
		t.insert(5);
		t.insert(12);
		Tree.Node n = t.insert(14);
		t.insert(17);
		t.insert(21);
		t.insert(19);
		t.insert(10);
		t.insert(16);
		t.insert(15);
		t.inOrder();
		System.out.println(n.findSuccessor().v);
	}
}
