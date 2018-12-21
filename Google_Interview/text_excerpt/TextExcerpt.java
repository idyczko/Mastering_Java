import java.util.*;
import java.util.stream.*;

public class TextExcerpt {

	
	public static void main(String[] args) {
		File file = new File(args[0]);
		String[] text = readText(file);
		String excerpt = calculateExcerpt(Arrays.stream(args).skip(1).collect(Collectors.toList), text);
	}

	private static void calculateExcerpt(List<String> terms, String[] text) {
		HashMap<String, LinkedList<Integer>> positions = findPositions(terms, text);
		PriorityQueue<HeapNode> heap = new PriorityQueue<>(terms.size(), new Comparator<HeapNode>() {
			@Override
			public int compare(HeapNode n1, HeapNode n2) {
				return n1.position - n2.position;
			}
		});

	}

	public static class HeapNode {
		String term;
		int position;
		int index;

		HeapNode(String term, int position, int index) {
			this.term = term;
			this.position = position;
			this.index = index;
		}
	}
}
