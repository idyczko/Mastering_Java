
public class Main {

	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);

		int R = in.nextInt();
		int C = in.nextInt();
		int L = in.nextInt();
		int H = in.nextInt();
		boolean[][] pizza = new boolean[R][C];
		String line = "";
		for(int i = 0; i < R; i++) {
			line = in.nextLine();
			char[] chars = line.toCharArray();
			for (int j = 0; j < chars.length; j++) {
				pizza[i][j] = chars[j] == 'M';
			}
		}
	}

	public static void print(boolean[][] pizza) {
		for (boolean[] row : pizza) {
			for (boolean ing : row)
				System.out.print(ing ? "M " : "T ");
			System.out.println();
		}
	}

	public static boolean intersect(Slice s1, Slice s2) {
		
	} 

	public static class Slice {
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

	}

	public static class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
