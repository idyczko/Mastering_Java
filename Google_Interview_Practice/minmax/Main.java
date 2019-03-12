import java.util.*;
import java.util.concurrent.atomic.*;

public class Main {
	
	private static boolean log = false;

	public static void main(String[] args) {
		log |= Arrays.asList(args).contains("-l");
		Game g = new Game(new Human(1), new MinMax(2, 1));
		g.play();
	}

	private static class Game {
		TicTacToe t = new TicTacToe();
		Player p1;
		Player p2;
		
		Game(Player p1, Player p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		void play() {
			boolean p1Move = true;
			while(!t.possibleMoves.isEmpty() && !t.boardEvaluation().finished) {

				printBoard();
				if (p1Move)
					p1.makeMove(this);
				else
					p2.makeMove(this);
				p1Move = !p1Move;
			}
			printBoard();
			System.out.println(t.boardEvaluation().winner == 0 ? "It's a draw! Congratulations to all non-artificial contestants!" : ("Player " + t.boardEvaluation().winner + " is the victor."));
		}

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

	private static class MinMax implements Player {
		int id;
		int opponentsId;
		MinMax(int id, int opponentsId) {this.id = id; this.opponentsId = opponentsId;}

		@Override
		public void makeMove(Game g) {
			TicTacToe t = g.t;
			AtomicInteger leaves = new AtomicInteger();
			AtomicInteger possibleWins = new AtomicInteger();
			g.t.move(minMax(g.t, new AtomicInteger(), true, 0, leaves, possibleWins), id);
			log("Leaves: " + leaves.get() + " wins: " + possibleWins.get());
		}

		Point minMax(TicTacToe t, AtomicInteger result, boolean max, int depth, AtomicInteger leaves, AtomicInteger possibleWins) {
			if (t.gameFinished()) {
				leaves.incrementAndGet();
				Result r = t.boardEvaluation();
				if (r.winner == 0) {
					result.set(0);
				} else if (r.winner == this.id) {
					result.set((100 - depth)*10);
					possibleWins.incrementAndGet();
				} else {
					result.set((depth - 100)*10);
				}

				return null;
			}

			Map<Point, AtomicInteger> points = new HashMap<>();

			for (Point move : t.possibleMoves) {
				AtomicInteger r = new AtomicInteger();
				points.put(move, r);
				minMax(t.copy().move(move, max ? this.id : this.opponentsId), r, !max, depth + 1, leaves, possibleWins);
			}

			Optional<Map.Entry<Point, AtomicInteger>> best = max ? points.entrySet().stream().max((e1, e2) -> e1.getValue().get() - e2.getValue().get()) :
				points.entrySet().stream().min((e1, e2) -> e1.getValue().get() - e2.getValue().get());
			result.set(best.get().getValue().get());
			return best.get().getKey();
		}
	}

	private static void log(String s) {
		if (log)
			System.out.println(s);
	}

	private static class Human implements Player {
		int id;
		Human(int id) {this.id = id;}

		@Override
		public void makeMove(Game g) {
			Scanner sc = new Scanner(System.in);
			int x = sc.nextInt();
			int y = sc.nextInt();
			g.t.move(new Point(x, y), id);
		}

	}

	private interface Player {
		
		void makeMove(Game g);
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
			return this.possibleMoves.isEmpty() || this.boardEvaluation().finished;
		}

		Result boardEvaluation() {
			for (int i = 0; i < 3; i++)
				if ((this.board[i][i] != 0 && ((this.board[i][i] == this.board[i][(i + 1) % 3] && this.board[i][i] == this.board[i][(i + 2)% 3]) ||
								(this.board[i][i] == this.board[(i + 1) % 3][i] && this.board[i][i] == this.board[(i + 2)% 3][i]))))
						return new Result(true, this.board[i][i]);
			
			if (this.board[0][0] != 0 && this.board[0][0] == this.board[1][1] && this.board[0][0] == this.board[2][2])
				return new Result(true, this.board[1][1]);

			if (this.board[0][2] != 0 && this.board[0][2] == this.board[1][1] && this.board[0][2] == this.board[2][0])
				return new Result(true, this.board[1][1]);

			return new Result(false, 0);
		}

		TicTacToe move(Point move, int player) {
			if (!possibleMoves.contains(move) || player < 1 || player > 2)
				throw new IllegalArgumentException();
			
			this.board[move.x][move.y] = player;
			this.possibleMoves.remove(move);
			return this;
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

	private static class Result {
		boolean finished;
		int winner;

		Result(boolean finished, int winner) {
			this.finished = finished;
			this.winner = winner;
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
