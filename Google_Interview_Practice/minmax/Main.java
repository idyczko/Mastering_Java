import java.util.*;

public class Main {

	public static void main(String[] args) {
		Game g = new Game();
		g.play();
	}

	private static class Game {
		TicTacToe t = new TicTacToe();
		
		void play() {
			Scanner sc = new Scanner(System.in);
			while(!t.gameFinished()) {
				printBoard();
				int x = sc.nextInt();
				int y = sc.nextInt();
				t.move(new Point(x, y), 1);
				computerMove();
			}
			printBoard();
		}

		void computerMove(){}

		void printBoard() {
			System.out.println("-------");
			for (int[] row : t.board) {
				System.out.print("|");
				for (int cell : row) {
					System.out.print((cell == 0 ? " " : (cell == 1 ? "X" : "O")) + "|");
				}
				System.out.println();
			}
			System.out.println("-------");
		}
	}

	private static class TicTacToe {
		int[][] board = new int[3][3];
		Set<Point> possibleMoves = new HashSet<>();
		
		TicTacToe() {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					possibleMoves.add(new Point(i, j));
		}

		boolean gameFinished() {
			return this.possibleMoves.isEmpty() || this.someoneWon();
		}

		boolean someoneWon() {
			for (int i = 0; i < 3; i++)
				if ((this.board[i][i] != 0 && ((this.board[i][i] == this.board[i][(i + 1) % 3] && this.board[i][i] == this.board[i][(i + 2)% 3]) ||
								(this.board[i][i] == this.board[(i + 1) % 3][i] && this.board[i][i] == this.board[(i + 2)% 3][i]))))
						return true;
			
			if (this.board[0][0] != 0 && this.board[0][0] == this.board[1][1] && this.board[0][0] == this.board[2][2])
				return true;

			if (this.board[0][2] != 0 && this.board[0][2] == this.board[1][1] && this.board[0][2] == this.board[2][0])
				return true;

			return false;
		}

		void move(Point move, int player) {
			if (!possibleMoves.contains(move) || player < 1 || player > 2)
				throw new IllegalArgumentException();
			
			this.board[move.x][move.y] = player;
			this.possibleMoves.remove(move);
		}

		TicTacToe copy() {
			TicTacToe c = new TicTacToe();
			c.possibleMoves = new HashSet<>(this.possibleMoves);
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					c.board[i][j] = this.board[i][j];
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
