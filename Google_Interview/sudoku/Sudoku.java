import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Optional;

public class Sudoku {

	private Set<DecisionVariable> unassignedVariables = new HashSet<>(81);	
	private DecisionVariable[][] sudokuBoard = new DecisionVariable[9][9];

	public Sudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuBoard[i][j] = new DecisionVariable();
				sudokuBoard[i][j].x = i;
				sudokuBoard[i][j].y = j;
				unassignedVariables.add(sudokuBoard[i][j]);
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 9; k++) {
					bindVariables(sudokuBoard[i][j], sudokuBoard[i][k]);
					bindVariables(sudokuBoard[i][j], sudokuBoard[k][j]);
				}
			}
		}
		
		for (int X = 0; X < 9; X+=3) {
			for (int Y = 0; Y < 9; Y+=3) {
				for(int i = X; i < X + 3; i++) {
					for (int j = Y; j < Y + 3; j++) {
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i - X + 1) % 3) + X][((j - Y + 1) % 3) + Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i - X + 2) % 3) + X][((j - Y + 2) % 3) + Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i - X + 1) % 3) + X][((((j - Y - 1) % 3) + 3) % 3) + Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i - X + 2) % 3) + X][((((j - Y - 2) % 3) + 3) % 3) + Y]);
					}
				}
			}
		}

	}
	
	public Sudoku(char[][] initialAssignment) {
		this();
		
		if(initialAssignment.length != 9 || initialAssignment[0].length != 9)
			throw new IllegalArgumentException("Given array does not represent an initial Sudoku assignment.");
		
		//printDomains();
		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Character.isDigit(initialAssignment[i][j]))
					sudokuBoard[i][j].assign(Character.getNumericValue(initialAssignment[i][j]));
				else if (initialAssignment[i][j] != ' ') {
					throw new IllegalArgumentException("Ecountered unrecognized character in the context of sudoku board instantiation.");
				}
			}
		}
		//printDomains();
	}

	public boolean solve() {
		return solve(this);
	}

	public char[][] toCharArray() {
		char[][] board = new char[9][9];
		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudokuBoard[i][j].isAssigned)
					board[i][j] = Character.forDigit(sudokuBoard[i][j].assignment, 10);
				else
					board[i][j] = ' ';
			}
		}
		return board;
	}

	private boolean solve(Sudoku problem) {
		Optional<DecisionVariable> smallestDomainVariable = unassignedVariables.stream().sorted().findFirst();
		if (!smallestDomainVariable.isPresent()) {
			printDomains();
			return true;
		}

		if (smallestDomainVariable.get().domain.isEmpty())
			return false;

		smallestDomainVariable.get().domain.stream().sorted().forEach(value -> {
			char[][] board = problem.toCharArray();
			board[smallestDomainVariable.get().x][smallestDomainVariable.get().y] = Character.forDigit(value.intValue(), 10);
			new Sudoku(board).solve();
		});

		return false;
	}

	public void printDomains() {
		System.out.println();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudokuBoard[i][j].domainToString() + "\t");
			}
			System.out.println();
		}

	}

	public void printConstrainedVariables() {
		System.out.println();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudokuBoard[i][j].constrainedVariables.size() + "\t");
			}
			System.out.println();
		}
	}

	private void bindVariables(DecisionVariable x, DecisionVariable y) {
		if(x != y && !x.constrainedVariables.contains(y) && !y.constrainedVariables.contains(x)) {
			x.constrainedVariables.add(y);
			y.constrainedVariables.add(x);
		}
	}
	

	private class DecisionVariable implements Comparable<DecisionVariable> {
		public Set<DecisionVariable> constrainedVariables = new HashSet<>();
		public Set<Integer> domain = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		public boolean isAssigned = false;
		public Integer assignment;
		public int x;
		public int y;

		public void assign(int value){
			if (!isAssigned && domain.contains(value)) {
				assignment = value;
				isAssigned = true;
				constrainedVariables.stream().filter(variable -> !variable.isAssigned).forEach(variable -> variable.domain.remove(value));
				unassignedVariables.remove(this);
			}
		}
		
		public String domainToString() {
			if (isAssigned)
				return "{" + assignment + "}";
			StringBuilder domainString = new StringBuilder("{ ");
			domain.stream().sorted().forEach(item -> domainString.append(item + " "));
			domainString.append("}");
			return domainString.toString();
		}

		@Override
		public int compareTo(DecisionVariable var) {
			return Integer.valueOf(this.domain.size()).compareTo(Integer.valueOf(var.domain.size()));
		}
	}
}
