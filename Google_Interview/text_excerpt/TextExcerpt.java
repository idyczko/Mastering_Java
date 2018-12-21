import java.util.*;
import java.util.stream.*;

public class TextExcerpt {

	
	public static void main(String[] args) {
		String text = "I was not happy but I have seen the others are happy so I started to feel happy";
		String[] terms = {"I", "happy"};
		String excerpt = calculateExcerpt(terms, text.split(" "));
		System.out.println(excerpt);
	}

	private static String calculateExcerpt(List<String> terms, String[] text) {
		HashMap<String, LinkedList<Integer>> positions = findPositions(terms, text);
		LinkedList<HeapNode> tuple = new LinkedList<>();
		positions.entrySet().forEach(e -> tuple.add(new HeapNode(e.getKey(), e.getValue().get(0), 0)));
		List<HeapNode> best = new ArrayList<>(tuple);
		int bestScore = calculateScore(best);
		while (nextSolutionFeasible(tuple)) {
			tuple = nextSolution(tuple);
			int score = calculateScore(tuple);
			if (score > bestScore) {
				bestScore = score;
				best = new ArrayList<>(tuple);
			}
		}
		StringBuilder result = new StringBuilder();
		for (int i = minPosition(bestSolution); i <= maxPosition(bestSolution); i++) {
			result.append(text[i] + " ");
		}
		return result.toString();
	}

	private static nextSolutionFeasible() {
		
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
