public class Sudoku {
		

	public Sudoku(int[][] init) {
		
	}

	private class DecisionVariable implements Comparable<DecisionVariable> {
		public List<Integer> domain = new ArrayList<>();
		
		@Override
		public int compareTo(DecisionVariable var) {
			return this.domain.size().compareTo(var.domain.size());
		}
	}
}
