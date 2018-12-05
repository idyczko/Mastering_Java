import java.util.stream.*;

public class Main {

	public static void main(String[] args) {
		BST tree = new BST();
		tree.insert(4).insert(3).insert(2).insert(1).insert(6).insert(7).insert(5).insert(4).insert(1).insert(1).insert(1).insert(-3);

		tree.sumPaths(10);
	}
}
