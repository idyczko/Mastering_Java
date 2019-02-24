import java.util.*;

public class Main {

	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);

		int R = in.nextInt();
		int C = in.nextInt();
		int L = in.nextInt();
		int H = in.nextInt();
		char[][] pizza = new char[R][C];
		String line = in.nextLine();
		for(int i = 0; i < R; i++) {
			line = in.nextLine();
			char[] chars = line.toCharArray();
			for (int j = 0; j < chars.length; j++) {
				pizza[i][j] = chars[j];
			}
		}
		List<Slice> initialSolution = new ArrayList<>();
		slices.add(new)
		print(pizza);
	}

	public static void print(char[][] pizza) {
		for (char[] row : pizza) {
			for (char ing : row)
				System.out.print(ing + " ");
			System.out.println();
		}
	}

	public static boolean intersect(Slice s1, Slice s2) {
		return false;
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
		
		int size() {
			return (1 + lowerRight.x - upperLeft.x)*(1 + lowerRight.y - upperLeft.y);
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
