

public class Main {

	public static void main(String[] args){
		byte a = (byte) Integer.valueOf(args[0]).intValue();
		byte b = (byte) Integer.valueOf(args[1]).intValue();
		System.out.println(swaps(a, b));
	}

	private static int swaps(byte a, byte b) {
		byte c = (byte) (a ^ b);
		int counter = 0;
		while (c > 0) {
			if ((c & 1) == 1)
				counter++;
			c = (byte) (c >> 1);
		}
		return counter;
	}
}
