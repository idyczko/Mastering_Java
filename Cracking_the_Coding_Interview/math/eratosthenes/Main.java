import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		StringBuilder stats = new StringBuilder();
		int N = Integer.valueOf(args[0]);
		long start = System.currentTimeMillis();
		sieve(N);
		long end = System.currentTimeMillis();
		stats.append("Sieve finished. Took " + (end - start) + " millis.\n");
		start = System.currentTimeMillis();
		boolean p = isPrimeStupid(N);
		end = System.currentTimeMillis();
		stats.append("Stupid finished. Took " + (end - start) + " millis. Result: " + p + "\n");
		start = System.currentTimeMillis();
		p = isPrimeSlightlyBetter(N);
		end = System.currentTimeMillis();
		stats.append("Better finished. Took " + (end - start) + " millis. Result: " + p + "\n");
		start = System.currentTimeMillis();
		p = isPrimeEratosthenesSieve(N);
		end = System.currentTimeMillis();
		stats.append("Eratosthenes Prime Check finished. Took " + (end - start) + " millis. Result: " + p + "\n");
		System.out.println(stats);
	}

	private static boolean isPrimeStupid(int n) {
		for (int i = 2; i < n; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	private static boolean isPrimeSlightlyBetter(int n) {
		// After the n*n the product elements start repeating in reversed order and we only care about unordered pairs.
		for (int i = 2; i <= Math.sqrt(n); i++)
			if (n % i == 0)
				return false;
		return true;
	}

	private static boolean isPrimeEratosthenesSieve(int n) {
		List<Integer> sieve = sieve(n);
		return sieve.get(sieve.size() - 1).equals(n);
	}

	private static void sieveG(int n) {
		List<Integer> sieve = sieve(n);
		for (Integer i : sieve)
			System.out.print(i + " ");
		System.out.println();
	}

	private static List<Integer> sieve(int n) {
		boolean[] flags = new boolean[n];
		
		for (int i = 0; i < flags.length; i++)
			flags[i] = true;

		for (int i = 1; i < n;) {
			for (int j = ((i + 1)*(i+1)) - 1; j < n; j += (i + 1))
				flags[j] = false;
			i = findNext(flags, i);
		}
		
		List<Integer> list  = new ArrayList<>();
		for (int i = 0; i < flags.length; i++)
			if (flags[i])
				list.add(i + 1);
		return list;
	}

	private static int findNext(boolean[] flags, int i) {
		int j;
		for (j = i + 1; j < flags.length; j++)
			if (flags[j])
				break;
		return j;
	}
}
