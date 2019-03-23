import java.util.*;


public class Main {

	static public void main(String[] ass) {
		List<Integer> is = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		print(is,  i -> i %2 ==0);
		print(is, (i) -> i%3 ==1);
		print(is, (Integer i) -> i%3==2);
	}

	private final static void print(List<Integer> is, I i) {
		is.stream().filter(ii -> i.test(ii)).forEach(System.out::println);
	}

	private static interface I {
	
		boolean test(Integer i);

	}
}
