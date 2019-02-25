import java.util.*;

public class Main {

	private static int R;
	private static int C;
	private static int L;
	private static int H;
	private static char[][] pizza;
	private static boolean log = false;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		R = in.nextInt();
		C = in.nextInt();
		L = in.nextInt();
		H = in.nextInt();
		pizza = new char[R][C];
		String line = in.nextLine();
		for(int i = 0; i < R; i++) {
			line = in.nextLine();
			char[] chars = line.toCharArray();
			for (int j = 0; j < chars.length; j++) {
				pizza[i][j] = chars[j];
			}
		}
		if (Arrays.asList(args).contains("-l"))
			log = true;

		Set<Slice> initSlices = computeInitSlices(pizza, L, H);

		if (Arrays.asList(args).contains("-p"))
			print(pizza);
		if (Arrays.asList(args).contains("-s"))
			initSlices.forEach(System.out::println);
	}

	private static Set<Slice> computeInitSlices(char[][] pizza, int L, int H) {
		return computeMinimalSizeSlices(pizza, L, H);
	}

	private static Set<Slice> computeMinimalSizeSlices(char[][] pizza, int L, int H) {
		Set<Slice> slices = new HashSet<>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (log)
					System.out.println("Cell: " + (i*R + j) +"/" + (R*C));

				slices.addAll(computeMinimalSlicesForCenter(i, j, pizza));
			}
		}

		return slices;
	}

	private static Set<Slice> computeMinimalSlicesForCenter(int i, int j, char[][] pizza) {
		Set<Slice> slices = new HashSet<>();
		Slice slice = new Slice(new Point(j, i), new Point(j, i));
		computeRecursively(slice, slices);
		return slices;
	}

	private static void computeRecursively(Slice slice, Set<Slice> slices) {
		if (slice.size() >= 2*L && slice.isAllowed()) {
			slices.add(slice);
			return;
		}
		if (slice.size() >= H)
			return;

		try {
			Slice left = slice.expandLeft();
			computeRecursively(left, slices);
		} catch(IllegalStateException e) {
		}
		try {
			Slice right = slice.expandRight();
			computeRecursively(right, slices);
		} catch(IllegalStateException e) {
		}
		try {
			Slice up = slice.expandUp();
			computeRecursively(up, slices);
		} catch(IllegalStateException e) {
		}
		try {
			Slice down = slice.expandDown();
			computeRecursively(down, slices);
		} catch(IllegalStateException e) {
		}


	}

	private static void print(char[][] pizza) {
		for (char[] row : pizza) {
			for (char ing : row)
				System.out.print(ing + " ");
			System.out.println();
		}
	}

	private static boolean intersect(Slice s1, Slice s2) {
		return false;
	} 

	private static class Slice {
		Point upperLeft;
		Point lowerRight;

		Slice (Point p1, Point p2) {
			int minX = p1.x > p2.x ? p2.x : p1.x;
			int maxX = p1.x > p2.x ? p1.x : p2.x;
			int minY = p1.y > p2.y ? p2.y : p1.y;
			int maxY = p1.y > p2.y ? p1.y : p2.y;
			this.upperLeft = new Point(minX, minY);
			this.lowerRight = new Point(maxX, maxY);

		}
		
		int size() {
			return (1 + lowerRight.x - upperLeft.x)*(1 + lowerRight.y - upperLeft.y);
		}

		boolean isAllowed() {
			int ms = 0, ts = 0;
			for (int j = this.upperLeft.x; j <= this.lowerRight.x; j++)
				for (int i = this.upperLeft.y; i <= this.lowerRight.y; i++) {
					if (pizza[i][j] == 'M')
						ms++;
					else
						ts++;
				}

			return (ms >= L) && (ts >= L) && (size() <= H);
		}

		@Override
		public String toString() {
			return "upperLeft: " + upperLeft.toString() + " lowerRight: " + lowerRight.toString();
		}

		Slice expandLeft() {
			return new Slice(upperLeft.move(-1, 0), lowerRight);
		}

		Slice expandRight() {
			return new Slice(upperLeft, lowerRight.move(1, 0));
		}

		Slice expandUp() {
			return new Slice(upperLeft.move(0, -1), lowerRight);
		}

		Slice expandDown() {
			return new Slice(upperLeft, lowerRight.move(0, 1));
		}

		@Override
		public int hashCode() {
			return 31 * upperLeft.hashCode() + 37 * lowerRight.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			Slice s = (Slice) o;
			return this.upperLeft.equals(s.upperLeft) && this.lowerRight.equals(s.lowerRight);
		}
	}

	private static class Point {
		final int x;
		final int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Point move(int hor, int ver) {
			if (this.x + hor < 0 || this.x + hor >= C || this.y + ver < 0 || this.y + ver >= R)
				throw new IllegalStateException();
			return new Point(x + hor, y + ver);
		}

		Point cp() {
			return new Point(x, y);
		}

		@Override
		public String toString() {
			return "x: " + x + " y: " + y;
		}

		@Override
		public int hashCode() {
			return 31 * x + 13 * y;
		}

		@Override
		public boolean equals(Object o) {
			Point p = (Point) o;
			return x == p.x && y == p.y;
		}
	}
}
