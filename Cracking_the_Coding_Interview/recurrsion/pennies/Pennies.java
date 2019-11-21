
public class Pennies {


	private static final int denoms[] = {25, 10, 5, 1};
	private static int cache[][] = new int[1][1];
	private static boolean CACHE_ENABLED = false;
	private static int COUNT = 0;


	public static void main(String[] args) {
		
		if (args.length > 1 && "CACHE".equals(args[1]))
			CACHE_ENABLED = true;

		makeChange(Integer.valueOf(args[0]));
		System.out.println("Count: " + COUNT);
	}

	private static void makeChange(int amount) {
		cache = new int[amount + 1][denoms.length];
		System.out.println(makeChange(amount, 0));
	}

	private static int makeChange(int amount, int index) {
		if (amount == 0 || index >= denoms.length - 1)
			return 1;

		if (CACHE_ENABLED && cache[amount][index] > 0)
			return cache[amount][index];

		COUNT++;
		int d = denoms[index];

		int initialAmount = amount;
		int numberOfWays = 0;
		while (amount >= 0) {
			numberOfWays += makeChange(amount, index + 1);
			amount -= d;
		}

		cache[initialAmount][index] = numberOfWays;
		return numberOfWays;
	}
}
