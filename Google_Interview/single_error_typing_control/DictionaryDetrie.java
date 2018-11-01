import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

public class DictionaryDetrie implements Detrie {
	
	private Node root = new MyNode();
	private Node masterLeaf = new MyNode();

	public Node getRoot() {return this.root;}
	public Node getMasterLeaf() {return this.masterLeaf;};

	public void insertWord(String word) {
	
		Node parent = root;
		Node newNode = null;
		for (char c : word.toCharArray()) {
			Set<Node> successors = parent.getSuccessors();
			if (successors.contains(this.masterLeaf))
				return;

			Optional<Node> next = successors.stream().filter(node -> node.getChar().equals(c)).findAny();
			if (next.isPresent()) {
				parent = next.get();
				continue;
			}

			newNode = new MyNode(c);
			newNode.getPredecessors().add(parent);
			parent.getSuccessors().add(newNode);
			parent = newNode;
		}

		if(newNode != null) {
			mergeBack(newNode);
			newNode.getSuccessors().add(masterLeaf);
			masterLeaf.getPredecessors().add(newNode);
		}
	}

	private void mergeBack(Node node) {
		Node child = masterLeaf;
		Optional<Node> equivalent;
		
		while ((equivalent = child.getPredecessors().stream().filter(item -> item.getChar().equals(node.getChar())).findAny()).isPresent()) {
			child = equivalent.get();
			node = node.getPredecessors().toArray(new MyNode[]{})[0];
		}
		child.getPredecessors().add(node);
		node.getSuccessors().clear();
		node.getSuccessors().add(child);
	} 

	public static class MyNode implements Node {
		private Character c;
		private Set<Node> predecessors = new HashSet<Node>();
		private Set<Node> successors = new HashSet<Node>();
		
		public MyNode() {}
		public MyNode(Character c) {this.c = c;}
		public Character getChar() {return this.c;}
		public Set<Node> getPredecessors() {return this.predecessors;}
		public Set<Node> getSuccessors() {return this.successors;}
	}
}
