
public class Main {

	public static void main(String[] args) {
		int input = Integer.valueOf(args[0]);
		print(input);
		print(bruteBigger(input));
		print(bruteSmaller(input));
	}

	private static int bruteBigger(int i) {
		int ones = countOnes(i);
		int j = i;
		while(countOnes(++j) != ones);
		return j;
	}

	private static int bruteSmaller(int i) {
		int ones = countOnes(i);
		int k = i;
		while (countOnes(--k) != ones);
		return k;
	}

	private static void print(int i) {
		String n = String.valueOf(i & 1);
		while ((i >>= 1) != 0)
			n = (i & 1) + n;
		System.out.println(n);
	}

	private static int countOnes(int i) {
		int count = 0;
		while (i != 0) {
			count += i & 1;
			i >>= 1;
		}
		return count;
	}
}
