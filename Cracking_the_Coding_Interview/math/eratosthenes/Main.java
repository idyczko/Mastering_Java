import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		StringBuilder stats = new StringBuilder();
		long N = Long.valueOf(args[0]);
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
		start = System.currentTimeMillis();
		p = isPrimeSixK(N);
		end = System.currentTimeMillis();
		stats.append("6k (+/-) 1 check finished. Took " + (end - start) + " millis. Result: " + p + "\n");

		System.out.println(stats);
	}

	private static boolean isPrimeSixK(long n) {
		if (n > 1 && n < 4)
			return true;

		for (long k = 1; (6*k - 1) < Math.sqrt(n); k++){
			if (n % (6*k - 1) == 0 || n % (6*k + 1) == 0)
				return false;
		}

		return true;
	}

	private static boolean isPrimeStupid(long n) {
		for (long i = 2; i < n; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	private static boolean isPrimeSlightlyBetter(long n) {
		// After the n*n the product elements start repeating in reversed order and we only care about unordered pairs.
		for (long i = 2; i <= Math.sqrt(n); i++)
			if (n % i == 0)
				return false;
		return true;
	}

	private static boolean isPrimeEratosthenesSieve(long n) {
		List<Long> sieve = sieve(n);
		return sieve.get(sieve.size() - 1).equals(n);
	}

	private static void sieveG(long n) {
		List<Long> sieve = sieve(n);
		for (Long i : sieve)
			System.out.print(i + " ");
		System.out.println();
	}

	private static List<Long> sieve(long n) {
		boolean[] flags = new boolean[(int) n];
		
		for (long i = 0; i < flags.length; i++)
			flags[(int) i] = true;

		for (long i = 1; i < n;) {
			for (long j = ((i + 1)*(i+1)) - 1; j < n; j += (i + 1))
				flags[(int) j] = false;
			i = findNext(flags, i);
		}
		
		List<Long> list  = new ArrayList<>();
		for (long i = 0; i < flags.length; i++)
			if (flags[(int) i])
				list.add(i + 1);
		return list;
	}

	private static long findNext(boolean[] flags, long i) {
		long j;
		for (j = i + 1; j < flags.length; j++)
			if (flags[(int) j])
				break;
		return j;
	}
}
