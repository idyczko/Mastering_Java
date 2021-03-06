
public class Permutations {

	public static void main(String[] args) {
	
		String word = args[0]; //we assume it is there;
		permutations(word);
	}

	private static void permutations(String word) {

		permutations("", word);
	}

	private static void permutations(String permutationSoFar, String remainingLetters) {
		//System.out.println(permutationSoFar + " " + remainingLetters);
		if (remainingLetters.isEmpty()) {
			System.out.println(permutationSoFar);
			return;
		}
		
		String checked = "";
		for (int i = 0; i < remainingLetters.length(); i++) {
			if (checked.contains(String.valueOf(remainingLetters.charAt(i))))
				continue;

			checked += remainingLetters.charAt(i);
			permutations(permutationSoFar + remainingLetters.charAt(i), remainingLetters.substring(0, i) + remainingLetters.substring(i+1));
		}
	}
}
