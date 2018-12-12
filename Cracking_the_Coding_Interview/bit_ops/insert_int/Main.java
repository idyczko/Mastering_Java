

public class Main {

	public static void main(String[] args) {
		
		int n = Integer.valueOf(args[0]);
		int m = Integer.valueOf(args[1]);
		int j = Integer.valueOf(args[2]);

		System.out.println("Bit lengths:\nn: " + bitLength(n) + "\nm: " + bitLength(m));
		System.out.println(insert(n, m, j));
	}

	private static int insert(int n, int m, int j) {
		return 0;
	}

	private static int bitLength(int n) {
		int length = 0;
		while ((n /= 2) != 0)
			length++;

		return length;
	}
}
