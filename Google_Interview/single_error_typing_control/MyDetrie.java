import java.util.Set;
import java.util.HashSet;

public class DictionaryDetrie implements Detrie {
	
	private MyNode root = new ;
	private MyNode masterLeaf;

	public MyNode getRoot() {return this.root;}
	public MyNode getMasterLeaf() {return this.masterLeaf;};

	public void insertWord(String word) {
		
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
