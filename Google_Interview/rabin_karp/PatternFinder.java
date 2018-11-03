
public class PatternFinder {
	
	private static final int PRIME = 101;
	private static final int BIG_PRIME = 7919;

	public static int find(String pattern, String sequence) {
		log("Starting looking for pattern: " + pattern + " in sequence: " + sequence +".");
		char[] patternChars = pattern.toCharArray();
		char[] sequenceChars = sequence.toCharArray();
		int length = patternChars.length;
		if (length > sequenceChars.length)
			return -1;
		long patternHash = hash(patternChars);
		log("Pattern hash: " + patternHash);
		long sequenceHash = hash(sequence.substring(0, length).toCharArray());
		log("Subsequence hash: " + sequenceHash);
		int index = 0;
		while (index < (sequenceChars.length - length)) {
			if (patternHash == sequenceHash && compareSequences(patternChars, sequenceChars, index))
				return index;
			sequenceHash = rollHash(sequenceChars[index], sequenceChars[index + patternChars.length], sequenceHash, patternChars.length);
			log("New subsequence hash: " + sequenceHash);
			index++;
		}
		return -1;
	}

	private static boolean compareSequences(char[] pattern, char[] sequence, int index) {
		log("Found matching hash at index " + index + ". Comparing...");
		
		for (int i = 0; i < pattern.length; i++)
			if (pattern[i] != sequence[index + i])
				return false;

		return true;
	}

	private static long rollHash(char oldLetter, char newLetter, long oldHash, int length) {
		return (BIG_PRIME + ((((oldHash - (oldLetter * ((long) Math.pow(PRIME, length - 1)))) * PRIME) + newLetter) % BIG_PRIME)) % BIG_PRIME;
	}

	private static long hash(char[] word) {
		long hash = 0;

		for (int i = 0; i < word.length; i++) {
			hash += word[i]*((int) Math.pow(PRIME, word.length - 1 - i));
			hash %= BIG_PRIME;
		}

		return hash;
	}

	private static void log(String log) {
		System.out.println(log);
	}
}
