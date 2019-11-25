import java.util.*;

public class Peeks {
	
	public static void main(String[] args) {
		int numbers[] = new int[args.length];

		for (int i = 0; i < args.length; i++) {
			numbers[i] = Integer.parseInt(args[i]);
		}

		System.out.println();
		for (int p : peeksAndValleys(numbers)) {
			System.out.print(p + " ");
		}
	}

	private static int[] peeksAndValleys(int[] numbers) {
		
		int[] peeks = new int[numbers.length];
		

		for (int i = 0; i < numbers.length - 2; i+=2) {
		
			List<Integer> window = Arrays.asList(numbers[i], numbers[i + 1], numbers[i + 2]);
			Collections.sort(window);

			int min = window.get(0);
			int mid = window.get(1);
			int max = window.get(2);

			peeks[i] = max;
			peeks[i + 1] = min;
			peeks[i + 2] = mid;
		}

		return peeks;
	}

}
