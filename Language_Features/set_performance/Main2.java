
public class Main2 {

	public static void main(String[] args) {
		String[] possibilities = {"one", "two", "three", "four"};
		long accu = 0l;

		for ( int i = 0; i < 10000; i++) {
			String option = possibilities[(int) Math.random()*4];
			long start = System.nanoTime();
			if ("one".equals(option) || "two".equals(option) || "three".equals(option)) { 
				int x = 10 + 10;
			}
			long end = System.nanoTime();
			accu += (end - start);
		}
		System.out.println(accu/10000);
	}
}
