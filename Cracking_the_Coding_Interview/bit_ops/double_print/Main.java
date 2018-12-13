

public class Main {

	public static void main(String[] args) {
		if (args.length < 1)
			throw new IllegalArgumentException("Gimme some number.");

		double d = Double.valueOf(args[0]);
		
		if (d < 0 || d > 1)
			throw new IllegalArgumentException("That's not what you promised!");

		log("D: " + d);	
		log("Bits: " + printBits(d));
	}

	private static String printBits(double d) {
		StringBuilder sb = new StringBuilder("0.");
		while (d > 0) {
			if (sb.length() >= 34)
				throw new IllegalStateException("Given number is not representable on 32 bits.");
			d *= 2;
			sb.append(d >= 1 ? "1" : "0");
			if (d >= 1)
				d -= 1;
		}
		return sb.toString();
	}

	private static void log(String s) {
		System.out.println(s);
	}
}
