public class Main {

	public static void main(String[] args) {

		String s = Integer.toBinaryString(Float.floatToIntBits(Float.valueOf(args[0])));
		System.out.println(s.length());
		for (int i = 0; i < s.length(); i++)
			System.out.print(s.charAt(i) + (i%8 == 7? " " : ""));
	}
}
