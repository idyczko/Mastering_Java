import java.util.*;
import java.util.stream.*;

public class TextExcerpt {

	
	public static void main(String[] args) {
		String text = "I was not happy but I have seen the others are happy so I started to feel happy";
		List terms = Arrays.asList("I", "happy");
		String excerpt = calculateExcerpt(terms, text.split(" "));
		System.out.println(excerpt);
	}

	private static String calculateExcerpt(List<String> terms, String[] text) {
		Map<String, LinkedList<Integer>> positions = findPositions(terms, text);
		LinkedList<HeapNode> tuple = new LinkedList<>();
		for (Map.Entry<String, LinkedList<Integer>> entry : positions.entrySet()) {
			tuple.add(new HeapNode(entry.getKey(), entry.getValue().get(0), 0));
		}
		List<HeapNode> best = new ArrayList<>(tuple);
		int bestScore = calculateScore(best);
		while (nextSolutionFeasible(positions, tuple)) {
			tuple = nextSolution(positions, tuple);
			int score = calculateScore(tuple);
			if (score > bestScore) {
				bestScore = score;
				best = new ArrayList<>(tuple);
			}
		}
		StringBuilder result = new StringBuilder();
		for (int i = getMinPosNode(best).position; i <= getMaxPosNode(best).position; i++) {
			result.append(text[i] + " ");
		}
		return result.toString();
	}

	private static Map<String, LinkedList<Integer>> findPositions(List<String> terms, String[] text) {
		Map<String, LinkedList<Integer>> positions = new HashMap<>();
		for (int i = 0; i < text.length; i++) {
			if (terms.contains(text[i])) {
				if (positions.containsKey(text[i]))
					positions.get(text[i]).add(i);
				else {
					positions.put(text[i], new LinkedList<>());
					positions.get(text[i]).add(i);
				}
			}
		}
		return positions;
	}

	private static HeapNode getMaxPosNode(List<HeapNode> nodes) {
		int max = 0;
		HeapNode maxNode = null;
		for (HeapNode node : nodes) {
			if (node.position > max) {
				max = node.position;
				maxNode = node;
			}
		}
		return maxNode;
	}

	private static LinkedList<HeapNode> nextSolution(Map<String, LinkedList<Integer>> positions, LinkedList<HeapNode> tuple) {
		HeapNode minNode = getMinPosNode(tuple);
		tuple.remove(minNode);
		tuple.add(new HeapNode(minNode.term, positions.get(minNode.term).get(minNode.index + 1), minNode.index + 1));
		return tuple;
	}

	private static boolean nextSolutionFeasible(Map<String, LinkedList<Integer>> positions, LinkedList<HeapNode> tuple) {
		HeapNode minNode = getMinPosNode(tuple);
		return (minNode.index + 1) < positions.get(minNode.term).size();
	}

	private static HeapNode getMinPosNode(List<HeapNode> nodes) {
		int min = Integer.MAX_VALUE;
		HeapNode minNode = null;
		for (HeapNode node : nodes) {
			if (node.position < min) {
				min = node.position;
				minNode = node;
			}
		}
		return minNode;
	}

	private static int calculateScore(List<HeapNode> solution) {
		int min = Integer.MAX_VALUE;
		int max = 0;
		for (HeapNode node : solution) {
			if (node.position < min)
				min = node.position;

			if (node.position > max)
				max = node.position;
		}
		return max - min;
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
