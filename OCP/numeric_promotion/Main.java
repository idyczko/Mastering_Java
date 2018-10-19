public class Main {

	public static void main(String[] args) {
		short s = 10;
		byte b = 2;
		byte c = 4;
		s = (short) (s + b); //Conversion of s and b to int -> conversion of the result to short has to be carried out by explicit casting due to possibly lossy conversion!
		byte d = (byte) (b + c); //The rule mentioned above holds even if both oprands have the same type... Kind of lame...
		long l = 1_000_000_000_000_000_000l; //You have to remember about the l, becaouse java will thing it is to big of a int.
		float f = 0.1f; //You have to remember about the f, because java cannot figure out you are not thinking of a double - lossy conversion!
		double e = 0.1;
		f = l + f; //f will hold complete garbage, but compiler won't complain.
		e = e + l; //e will hold 10E17, 0.1 will be naturally dropped due to binary representation boundries.
		System.out.println(s + " " + d + " " + l + " " + f + " " + e);
	}
}
