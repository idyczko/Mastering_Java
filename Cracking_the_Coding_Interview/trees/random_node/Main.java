import java.util.stream.*;

public class Main {

	public static void main(String[] args) {
		BST tree = new BST();
		tree.insert(4).insert(3).insert(2).insert(1).insert(7).insert(6).insert(5);
		int[] results = new int[tree.root.size];
		tree.inOrder();
		for (int i = 0; i < 5000; i++)
			results[tree.random().v - 1]++;
		for (int i : results)
			System.out.println(i);
	}
}
