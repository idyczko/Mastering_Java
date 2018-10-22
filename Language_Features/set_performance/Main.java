import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		String[] possibilities = {"one", "two", "three", "four"};
		long accu = 0l;
		Set<String> uris = new HashSet<>(Arrays.asList("one", "two", "three"));

		for ( int i = 0; i < 10000; i++) {
			String option = possibilities[(int) Math.random()*4];
			long start = System.nanoTime();
			if (uris.contains(option)) { 
				int x = 10 + 10;
			}
			long end = System.nanoTime();
			accu += (end - start);
		}
		System.out.println(accu/10000);
	}
}
