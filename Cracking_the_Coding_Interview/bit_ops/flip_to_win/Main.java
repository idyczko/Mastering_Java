
public class Main {

	public static void main(String[] args) {
		System.out.println(args[0]);
		System.out.println(maxReward(args[0]));
	}

	private static int maxReward(String bits) {
		
		int current = 0;
		int previous = 0;
		int max = 0;
		int index = 0;
		char last = 'a';
		for (char c : bits.toCharArray()) {
			if (c == '1')
				current++;
			else if (last == '0') {
				previous = 0;
			} else {
				max = max > (current + 1 + previous) ? max : (current + 1 + previous);
				previous = current;
				current = 0;
			}
		}
		int result =  max > (current + 1 + previous) ? max : (current + 1 + previous);
		return result > bits.length()? bits.length() : result;

	}
}
