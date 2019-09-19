import java.util.*;

public class Huffmann {

	public static void main(String[] args) {
		
		HuffmannCompresser hc = new HuffmannCompresser();
		hc.buildEncoding(args[0]);
		System.out.println(hc.tree.root.text);
		hc.printDictionary();
		System.out.println(hc.encode(args[0]));
	}

	private static class HuffmannCompresser {
		
		public Tree tree;

		public void buildEncoding(String text) {
			Tree tree = new Tree();
			this.tree = tree;	
			
			Map<String, Record> map = initializeMap(text);
			Queue<Record> q = new PriorityQueue<Record>(map.keySet().size(), (p1, p2) -> p1.freq - p2.freq);
			map.values().forEach(r -> q.add(r));

			while (q.size() > 1) {
				Record r1 = q.poll();
				Record r2 = q.poll();
				Record r12 = new Record();
				r12.freq = r1.freq + r2.freq;
				Node node = new Node();
				node.text = r1.node.text + r2.node.text;
				node.left = r1.node;
				node.right = r2.node;
				r12.node = node;
				q.add(r12);
			}

			tree.root = q.poll().node;
		}

		public String encode(String text) {

			if (tree == null)
				throw new IllegalStateException("The encoding cannot be performed.");

			
			StringBuilder sb = new StringBuilder();
			for (char c : text.toCharArray()) {
				String literalEncoding = "";
				Node node = tree.root;
				while (!node.text.equals(Character.toString(c))) {
					Node nextNode = node.left.text.contains(Character.toString(c)) ? node.left : node.right;
					literalEncoding += node.left.text.contains(Character.toString(c)) ? "0" : "1";
					node = nextNode;
				}
				sb.append(literalEncoding);
			}
			return sb.toString();

		}

		public void printDictionary() {
			printLeaves(tree.root, "");
		}

		private void printLeaves(Node node, String encoding) {
			if (node.left == null) {
				System.out.println(node.text + ": " + encoding);
				return;
			}

			printLeaves(node.left, encoding + "0");
			printLeaves(node.right, encoding + "1");
		}

		private Map<String, Record> initializeMap(String text) {
			Map<String, Record> map = new HashMap<>();
			for (char c : text.toCharArray()) {
				Record record = map.get(Character.toString(c));
				
				if (record == null) {
					Node node = new Node();
					node.text = Character.toString(c);
					Record newRecord = new Record();
					newRecord.node = node;
					newRecord.freq = 1;
					map.put(Character.toString(c), newRecord);
				} else {
					record.freq++;
				}
				
			}

			return map;
		}
	}

	private static class Record {
		int freq;
		Node node;
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
