
public class Huffmann {

	public static void main(String[] args) {
		
		HuffmannCompresser hc = new HuffmannCompresser();

	}

	private static class HuffmannCompresser {
		
		Tree tree;

		public void buildEncoding(String text) {
			Tree tree = new Tree();
			this.tree = tree;	


		}

		public String encode(String text) {

			if (tree == null)
				throw new IllegalStateException("The encoding cannot be performed.");

		}
	}

	private static class Tree {
		Node root;
	}

	private static class Node {
		Node left;
		Node right;

		String text;
	}
}
