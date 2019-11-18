
public class Parens {

	
	public static void main(String[] args) {
		Integer n = Integer.valueOf(args[0]);
		printCorrectParens(n);
	}

	private static void printCorrectParens(int pairs) {
		printCorrectParens(pairs, 0, 0, "");
	}

	private static void printCorrectParens(int pairs, int pairsClosed, int pairsOpen, String expressionSoFar) {
		if (pairsClosed == pairs) {
			System.out.println(expressionSoFar);
			return;
		}

		if (pairsOpen > 0)
			printCorrectParens(pairs, pairsClosed + 1, pairsOpen - 1, expressionSoFar + ")");
		
		if (pairs - pairsClosed > pairsOpen)
			printCorrectParens(pairs, pairsClosed, pairsOpen + 1, expressionSoFar + "(");
	}
}
