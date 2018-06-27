public class Main {

  public static void main(String args[]) {
    //int matrix[][] = new int[][] {{1,2,3}, {1,2,3}, {1,2,3}};
    //int matrix[][] = new int[][] {{1,0,0,0}, {0,1,0,0}, {0,0,1,0}, {0,0,0,1}};
    int matrix[][] = new int[][] {{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5}};
    printMatrix(matrix);
    int matrix2[][] = MatrixCalculator.rotateRight(matrix);
    printMatrix(matrix2);
  }

  private static void printMatrix(int[][] matrix) {
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < matrix.length; j++) {
        System.out.print(matrix[j][i] + "\t");
      }
      System.out.println();
    }
  }
}
