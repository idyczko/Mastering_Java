import java.util.*;

public class Main {

	public static void main(String[] args) {
	
		
	}

	private static class TicTacToe {
		int[][] board = new int[3][3];
		Set<Point> possibleMoves = new HashSet<>();
		
		TicTacToe() {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					possibleMoves.add(new Point(i, j));
		}

		void move(Point move, int player) {
			if (!possibleMoves.contains(move) || player < 1 || player > 2)
				throw new IllegalArgumentException();
			
			this.board[move.i][move.j] = player;
			this.possibleMoves.remove(move);
		}

		TicTacToe copy(TicTacToe t) {
			TicTacToe c = new TicTacToe();
			c.possibleMoves = new HashSet<>(t.possibleMoves);
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					c.board[i][j] = t.board[i][j];
			return c;
		}
	}

	private static class Point {
		int x;
		int y;

		Point(int x, int y) {
			if (x > 2 || y > 2 || x < 0 || y < 0)
				throw new IllegalArgumentException();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return 13 * x  + 31 * y;
		}

		@Override
		public boolean equals(Object o) {
			Point p = (Point) o;
			return this.x == p.x && this.y == p.y;
		}
	}
}
