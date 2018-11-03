import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.Collection;

public class DictionaryDetrie implements Detrie {
	
	private int length;
	private MyNode root = new MyNode();
	private MyNode masterLeaf = new MyNode();

	public DictionaryDetrie(int length) {this.length = length; this.masterLeaf.level = length + 1;}
	public Node getRoot() {return this.root;}
	public Node getMasterLeaf() {return this.masterLeaf;}

	public void insertWord(String word) {
	
		Optional<MyNode> maybeParent = parentOfFirstTypo(word);
		char[] wordChars = word.toCharArray();
		
		MyNode newNode = null;
		if (maybeParent.isPresent()) {
			MyNode parent = maybeParent.get();
			for (int i = parent.level; i < this.length; i++) {
				newNode = new MyNode(wordChars[i], i + 1);
				newNode.getPredecessors().add(parent);
				parent.getSuccessors().add(newNode);
				parent = newNode;
			}
		}

		if(newNode != null) {
			mergeBack(newNode);
		}
	}

	public boolean isZeroOrOneTypoAway(String word) {
		Optional<MyNode> parent = parentOfFirstTypo(word);
		if (!parent.isPresent())
			return true;

		int firstTypoLevel = parent.get().level;
		Optional<MyNode> child= childOfFirstTypoFromTail(word);
		if (!child.isPresent())
			throw new IllegalStateException("Inconsistent dictionary state.");

		if ((child.get().level - firstTypoLevel) > 2)
			return false;
		return true;
	}

	private Optional<MyNode> childOfFirstTypoFromTail(String word) {
		Node child = this.masterLeaf;

		for (int i = word.length() - 1; i >= 0; i--) {
			final Character c = word.charAt(i);
			Set<Node> predecessors = child.getPredecessors();
			if (predecessors.contains(this.root))
				break;

			Optional<Node> next = predecessors.stream().filter(node -> node.getChar().equals(c)).findAny();
			if (next.isPresent()) {
				child = next.get();
				continue;
			}

			return Optional.of((MyNode) child);
		}

		return Optional.empty();
	}

	private Optional<MyNode> parentOfFirstTypo(String word) {
		Node parent = this.root;
		
		for (char c : word.toCharArray()) {
			Set<Node> successors = parent.getSuccessors();
			if (successors.contains(this.masterLeaf))
				break;

			Optional<Node> next = successors.stream().filter(node -> node.getChar().equals(c)).findAny();
			if (next.isPresent()) {
				parent = next.get();
				continue;
			}

			return Optional.of((MyNode) parent);
		}

		return Optional.empty();
	}

	private void mergeBack(Node node) {
		Node child = masterLeaf;
		Optional<Node> equivalent;
		while ((equivalent = findNodeWithChar(child.getPredecessors(), node.getChar())).isPresent()) {
			child = equivalent.get();
			node = node.getPredecessors().toArray(new MyNode[]{})[0];
		}
		child.getPredecessors().add(node);
		node.getSuccessors().clear();
		node.getSuccessors().add(child);
	} 

	private Optional<Node> findNodeWithChar(Collection<Node> nodes, final Character c) {
		return nodes.stream().filter(node -> node.getChar().equals(c)).findAny();
	}

	public static class MyNode implements Node {
		private Character c;
		private Set<Node> predecessors = new HashSet<Node>();
		private Set<Node> successors = new HashSet<Node>();
		public int level;

		public MyNode() {}
		public MyNode(Character c) {this.c = c;}
		public MyNode(Character c, int level) {this.c = c; this.level = level;}
		public Character getChar() {return this.c;}
		public Set<Node> getPredecessors() {return this.predecessors;}
		public Set<Node> getSuccessors() {return this.successors;}
	}
}
