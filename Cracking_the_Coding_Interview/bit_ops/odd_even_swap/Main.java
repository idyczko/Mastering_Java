
public class Main {

	public static void main(String args[]) {
	
		int i = Integer.valueOf(args[0]);
		int oddMask = i & 0b10101010101010101010101010101010;
		int evenMask = i & 0b01010101010101010101010101010101;
		System.out.println((oddMask >> 1) | (evenMask << 1));
	}
}
