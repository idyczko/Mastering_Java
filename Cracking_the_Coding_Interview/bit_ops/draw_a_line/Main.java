public class Main {

	public static void main(String[] args) {
		byte[] screen = new byte[Integer.valueOf(args[0])];
		int width = Integer.valueOf(args[1]);
		display(screen, width);
		drawLine(screen, width, Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]));
		display(screen, width);
	}

	private static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
		if ((screen.length * 8) % width != 0 || x1 >= width || x2 >= width || x2 < x1 || (y > (screen.length * 8) / width))
			throw new IllegalArgumentException("Fix the data!");
		
		int wholeBytes = x2/8 - x1/8 - 1;
		int firstByte = (x1/8 + y * width/8);
		if (x1/8 != x2/8)
			screen[firstByte] |= (1 << (8 - (x1 % 8))) - 1;
		else
			screen[firstByte] |= (((1 << (8 - (x1 % 8))) - 1) ^ ((1 << (7 - (x2 % 8))) - 1) );
		for (int i = 0; i < wholeBytes; i++) {
			screen[firstByte + 1 + i] |= ~0;
		}
		if (x1/8 != x2/8)
			screen[firstByte + wholeBytes + 1] |= (~0 ^ ((1 << (7 - x2 % 8)) - 1));

	}

	private static void display(byte[] screen, int width) {
		int y = 0;
		int x = 0;
		System.out.println();
		System.out.println();
		for (byte b : screen) {
			x += 8;
			if (x > width) {
				System.out.print("\n" + read(b));
				x = 8;
			} else {
				System.out.print(read(b));
			}
		}
	}

	private static String read(byte b) {
		String bite = "";
		for (int i = 0; i < 8; i++) {
			bite = (b & 1) + bite;
			b = (byte) (b >> 1);
		}
		return bite;
	}
}
