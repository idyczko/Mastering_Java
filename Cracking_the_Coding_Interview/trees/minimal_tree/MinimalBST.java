import java.util.*;

public class MinimalBST {
	
	private static Integer atomicCounter = 0;

	public static void printMinimalBST(int[] sortedValues) {
		Node root = createNode(sortedValues, sortedValues.length/2, 0, sortedValues.length - 1);
	
		List<Integer> sequence = new ArrayList<>(sortedValues.length);
		inOrderTraversal(sequence, root, 0);
		sequence.stream().forEach(System.out::println);
		System.out.println("Nodes: " + Node.counter);
		System.out.println("Depht of the tree: " + atomicCounter);
	}

	private static void inOrderTraversal(List<Integer> sequence, Node node, int depth) {
		if (node == null)
			return;
		setAtomicCounter(depth);
		inOrderTraversal(sequence, node.l, depth + 1);
		sequence.add(node.v);
		inOrderTraversal(sequence, node.r, depth + 1);
	}

	private static Node createNode(int[] values, int index, int min, int max) {
		if (min > max)
			return null;

		return new Node(values[index], createNode(values, min + (index-min)/2, min, index-1), createNode(values, index + 1 + (max-index)/2, index+1, max));
	}

	private static void setAtomicCounter(int n) {
		if (n > atomicCounter )
			atomicCounter = n;
	}

	public static class Node {
		static int counter;
		int v;
		Node r;
		Node l;

		Node(int v, Node l, Node r) {
			counter++;
			this.v = v;
			this.r = r;
			this.l = l;
		}
	}
}
