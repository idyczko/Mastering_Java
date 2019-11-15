import java.util.*;

public class Multiply {

	public static final Map<String, Integer> CACHE = new HashMap<>();
	public static int OPERATIONS = 0;
	public static boolean cache_on = false;
	public static boolean handle_odd = false;

	public static void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);

		if (args.length > 2 && "cache".equals(args[2]))
			cache_on = true;

		if (args.length > 3 && "odd".equals(args[3]))
			handle_odd = true;

		System.out.println(multiply(x, y));
		System.out.println("Operations: " + OPERATIONS);
	}

	private static int multiply(int x, int y) {
		int lower = x < y ? x : y;
		int higher = x >= y ? x : y;
		
		if (handle_odd && lower%2 == 1) {
			OPERATIONS++;
			return higher + multiplyOrdered(lower - 1, higher);
		}

		return multiplyOrdered(lower, higher);
	}

	private static int multiplyOrdered(int lower, int higher) {
		if (lower == 1)
			return higher;

		if (cache_on && CACHE.containsKey(lower + ";" + higher))
			return CACHE.get(lower + ";" + higher);

		OPERATIONS += 2;
		int split = lower >> 1;
		int result = multiplyOrdered(split, higher) + multiplyOrdered(lower - split, higher);
		CACHE.put(lower + ";" + higher, result);
		return result;
	}
}
