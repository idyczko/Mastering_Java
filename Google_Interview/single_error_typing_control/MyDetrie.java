import java.util.Set;
import java.util.HashSet;

public class DictionaryDetrie implements Detrie {
	
	private MyNode root = new MyNode();
	private MyNode masterLeaf = new MyNode();

	public MyNode getRoot() {return this.root;}
	public MyNode getMasterLeaf() {return this.masterLeaf;};

	public void insertWord(String word) {
	
		MyNode parent = root;
		MyNode newNode;
		for (char c : word.toCharArray()) {
			Set<MyNode> successors = parent.getSuccessors();
			if (successors.contains(this.masterLeaf))
				return;

			Optional<MyNode> next = successors.stream().filter(node -> node.getChar().equals(c)).findAny();
			if (next.isPresent()) {
				parent = next.get();
				continue;
			}

			newNode = new MyNode(){{this.c = c}};
			newNode.getPredecessors().add(parent);
			parent.getSuccessors().add(newNode);
			parent = newNode;
		}

		if(newNode != null) {
			newNode.getSuccessors().add();
		}
	}

	public static class MyNode implements Node {
		private Character c;
		private Set<MyNode> predecessors = new HashSet<>();
		private Set<MyNode> successors = new HashSet<>();

		public Character getChar() {return this.c;}
		public Set<MyNode> getPredecessors() {return this.predecessors;}
		public Set<MyNode> getSuccessors() {return this.successors;}
	}
}
