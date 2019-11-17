
public class PaintFill {

	public static void main(String[] args) {
		
		int [][] painting = {{1,2,3,4},{1,2,3,4},{1,1,1,2},{2,3,1,1}};
		fillPaint(painting, 0, 0, 10);
	}

	private static void fillPaint(int[][] painting, int x, int y, int newColor) {
		int startingColor = painting[x][y];
		painting[x][y] = newColor;
		print(painting);

		if (x - 1 >= 0 && painting[x-1][y] == startingColor)
			fillPaint(painting, x-1, y, newColor);
		if (x + 1 < painting.length && painting[x+1][y] == startingColor)
			fillPaint(painting, x+1, y, newColor);
		if (y - 1 >= 0 && painting[x][y-1] == startingColor)
			fillPaint(painting, x, y-1, newColor);
		if (y + 1 < painting[0].length && painting[x][y+1] == startingColor)
			fillPaint(painting, x, y+1, newColor);
	}

	private static void print(int[][] painting) {
		for (int[] row : painting) {
			for (int cell : row)
				System.out.print(cell + " ");
			System.out.println();
		}
	}
}
