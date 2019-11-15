
public class Multiply {


	public static void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		System.out.println(multiply(x, y));
	}

	private static int multiply(int x, int y) {
		int lower = x < y ? x : y;
		int higher = x >= y ? x : y;

		return multiplyOrdered(lower, higher);
	}

	private static int multiplyOrdered(int lower, int higher) {
		if (lower == 1)
			return higher;

		int split = lower>>1;
		return multiplyOrdered(split, higher) + multiplyOrdered(lower - split, higher);
	}
}
