

public class Main {

	public static void main(String[] args) {
		
		int n = Integer.valueOf(args[0]);
		int m = Integer.valueOf(args[1]);
		int j = Integer.valueOf(args[2]);

		System.out.println("Bits:\nn: " + bitLength(n) + ": " + bits(n) + "\nm: " + bitLength(m) + ": " + bits(m));
		System.out.println(bits(insert(n, m, j)));
		System.out.println("Ones: " + ones(m) + " " + ones(n));
		System.out.println("Distance: " + distance(n, m));
	}

	private static int insert(int n, int m, int j) {
		if (bitLength(n) - j < bitLength(m))
			throw new IllegalArgumentException("Paste not allowed.");
		int left = 1;
		while (bitLength(left) < j - 1)
			left = (left << 1) | 1;
		left <<= (bitLength(n) - bitLength(left));

		System.out.println(bits(left));
		int right = (int) Math.pow(2, (bitLength(n) - j - bitLength(m) + 1)) - 1;
		System.out.println(bits(right));
		int mask = left | right;
		int masked = n & mask;
		System.out.println(bits(masked));
		int shifted = m << (bitLength(n) - j - bitLength(m));
		System.out.println(m + " " + bits(shifted));
		return masked | shifted;
	}

	private static int bitLength(int n) {
		int length = 1;
		while ((n /= 2) != 0)
			length++;

		return length;
	}

	private static String bits(int n) {
		StringBuilder sb = new StringBuilder(String.valueOf(n & 1));
		while ((n /= 2) != 0)
			sb.append(String.valueOf(n & 1));
		return sb.reverse().toString();
	}

	private static int distance(int i, int j) {
		return ones(i ^ j);
	}

	private static int ones(int n) {
		int ones = 0;
		while (n > 0) {
			if ((n & 1) == 1)
				ones++;
			n /= 2;
		}
		return ones;
	}
}
