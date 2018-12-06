import java.util.*;
import java.util.stream.*;
import java.util.concurrent.atomic.*;

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

	public void sumPaths(int sum) {
		sumPaths(root, sum, new LinkedList<>());
		System.out.println("Created instances: " + SumPath.instances.get() + " Max instances: " + SumPath.maxInstances);
	}

	private void sumPaths(Node node, int sum, LinkedList<SumPath> sequences) {
		if (node == null)
			return;

		sequences.forEach(seq -> seq.append(node));
		sequences.add(new SumPath(node));
		sequences.stream().filter(seq -> seq.sum == sum).forEach(SumPath::print);

		sumPaths(node.l, sum, new LinkedList<>(sequences.stream().filter(seq -> !(node.v <= 0 && seq.sum < sum))
					.map(seq -> new SumPath(seq)).collect(Collectors.toList())));
		sumPaths(node.r, sum, new LinkedList<>(sequences.stream().filter(seq -> !(node.v > 0 && seq.sum > sum))
					.collect(Collectors.toList())));
	}

	public static class SumPath extends LinkedList<Node> {
	
		static Integer maxInstances = 0;
		static AtomicInteger instances = new AtomicInteger(0);
		int sum;

		SumPath(SumPath s) {
			super(s);
			synchronized (maxInstances) {
				if (maxInstances < instances.incrementAndGet())
					maxInstances = instances.get();
			}
			this.sum = s.sum;
		}

		SumPath(Node n) {
			super(Collections.singletonList(n));
			synchronized (maxInstances) {
				if (maxInstances < instances.incrementAndGet())
					maxInstances = instances.get();
			}
			this.sum = n.v;
		}
		
		void append(Node n) {
			sum += n.v;
			addLast(n);	
		}

		void print() {
			 System.out.println(this.stream().map(n -> String.valueOf(n.v)).collect(Collectors.joining(" ")));
		}

		@Override
		protected void finalize() {
			instances.decrementAndGet();
		}
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
