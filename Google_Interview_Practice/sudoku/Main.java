
public class Main {

	public static void main(String[] args) {
		
		char[][] init = {{' ', ' ', ' ', ' ', ' ', '6', '2', ' ', ' '},
  				 {' ', '2', '4', '1', ' ', '3', '6', ' ', ' '},
  				 {' ', '3', ' ', '7', ' ', ' ', ' ', ' ', ' '},
  				 {' ', '7', '1', ' ', ' ', ' ', ' ', ' ', ' '},
  				 {'6', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '1'},
  				 {' ', ' ', ' ', '5', '6', '1', ' ', ' ', ' '},
  				 {' ', ' ', '8', ' ', ' ', ' ', '5', ' ', ' '},
  				 {'9', ' ', ' ', ' ', ' ', ' ', ' ', '1', '6'},
  				 {' ', ' ', '3', ' ', '9', '4', ' ', ' ', ' '}};
		new Sudoku(init).solve();
		System.out.println("Evaluated " + Sudoku.NODES + " out of possible " + Math.pow(81, 10) +" nodes... Do what you please with those " + (int) (Sudoku.NODES*100)/Math.pow(81,10) + " percents of time saved thanks to Constraint Programming!");
	}
}
