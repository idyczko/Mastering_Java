
public class Steps {

	public static void main(String[] args) {
		System.out.println("There are " + count(Integer.valueOf(args[0])) + " ways to go down " + args[0] + " steps.");
	}

	private static int count(int steps) {
		return count(steps, 1) + count(steps, 2) + count(steps, 3);
	}

	private static int count(int stepsLeft, int size) {
		if (stepsLeft < size)
			return 0;
		
		if (stepsLeft == size)
			return 1;
		
		return count(stepsLeft - size);
	}
}
