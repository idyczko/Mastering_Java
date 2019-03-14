import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		sieveG(Integer.valueOf(args[0]));
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
