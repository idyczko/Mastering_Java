
public class Plates {

	private static final int CHARS = 5;
	
	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		printPlate(n);
	}

	public static void printPlate(int n) {
		int akku = 0;
		for (int l = 0; l <=CHARS; l++) {
			int rangeSize = (int) (Math.pow(10, (CHARS - l)) * Math.pow(26, l));
			if (n < akku + rangeSize) {
				printPlate(n - akku, l);
				return;
			}
			akku += rangeSize;
		}
	}

	public static void printPlate(int offset, int letters) {
		String plate = "";
		for (int i = 1; i <= letters; i++) {
			int offsetsOffset = (int) (Math.pow(10, (CHARS - letters)) * Math.pow(26, (letters - i)));
			int letterNumber = offset / offsetsOffset;
			plate += Character.toString((char) (letterNumber + 'A'));
			offset -= offsetsOffset*letterNumber;
		}
		System.out.println(letters + " " + offset + " " + plate);
		System.out.println(String.format("%0" + (CHARS-letters) + "d", offset) + plate);
	}
}
