import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Sudoku {
	
	private DecisionVariable[][] sudokuBoard = new DecisionVariable[9][9];

	public Sudoku(char[][] initialAssignment) {
		if(initialAssignment.length != 9 || initialAssignment[0].length != 9)
			throw new IllegalArgumentException("Given array does not represent an initial Sudoku assignment.");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuBoard[i][j] = new DecisionVariable();
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 9; k++) {
					bindVariables(sudokuBoard[i][j], sudokuBoard[i][k]);
					bindVariables(sudokuBoard[j][i], sudokuBoard[j][k]);
				}
			}
		}
		for (int X = 0; X < 9; X+=3) {
			for (int Y = 0; Y < 9; Y+=3) {
				for(int i = X; i < X + 3; i++) {
					for (int j = Y; j < Y + 3; j++) {
						System.out.println("X: "+X +" Y: " + Y + " i: " + i + " j: " + j);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i+1)%(X+3))+X][((j+1)%(Y+3))+Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((i+2)%(X+3))+X][((j+2)%(Y+3))+Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((((i + 1) % (X + 3)) + (X + 3)) % (X + 3)) + X][((((j - 1) % (Y + 3)) + (Y + 3)) % (Y + 3)) + Y]);
						bindVariables(sudokuBoard[i][j], sudokuBoard[((((i + 2) % (X + 3)) + (X + 3)) % (X + 3)) + X][((((j - 2) % (Y + 3)) + (Y + 3)) % (Y + 3)) + Y]);
					}
				}
			}
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println(sudokuBoard[i][j].constrainedVariables.size());
			}
		}
	}

	private void bindVariables(DecisionVariable x, DecisionVariable y) {
		if(x != y && !x.constrainedVariables.contains(y)) {
			x.constrainedVariables.add(y);
			y.constrainedVariables.add(x);
		}
	}
	

	private class DecisionVariable implements Comparable<DecisionVariable> {
		public Set<DecisionVariable> constrainedVariables = new HashSet<>();
		public List<Integer> domain = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		public boolean isAssigned = false;
		public Integer assignment;

		public void assign(int value){
			if (!isAssigned && domain.contains(value)) {
				assignment = value;
				isAssigned = true;
				constrainedVariables.stream().filter(variable -> !variable.isAssigned).forEach(variable -> variable.domain.remove(value));
			}
		}
		

		@Override
		public int compareTo(DecisionVariable var) {
			return new Integer(this.domain.size()).compareTo(new Integer(var.domain.size()));
		}
	}
}
