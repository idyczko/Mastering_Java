public class MatrixCalculator {

  public static int[][] rotateRight(int[][] matrix) {
    int size = matrix.length;
    for(int i = 0; i <= (size-1)/2; i++) {
      for(int j = 0; j < size/2; j++) {
        Point p = new Point(i, j);
        Point q = rotateLeft(size, p);
        int temp = matrix[p.x][p.y];
        matrix[p.x][p.y] = matrix[q.x][q.y];
        p = q;
        q = rotateLeft(size, q);
        matrix[p.x][p.y] = matrix[q.x][q.y];
        p = q;
        q = rotateLeft(size, q);
        matrix[p.x][p.y] = matrix[q.x][q.y];
        matrix[q.x][q.y] = temp;
      }
    }
    return matrix;
  }

  public static Point rotateLeft(int size, Point p) {
    return new Point(p.y, size - p.x -1);
  }

  public static Point rotateRight(int size, Point p) {
    return new Point(size - p.y - 1, p.x);
  }

  public static class Point {
    public int x;
    public int y;

    public Point(int x, int y) {this.x = x; this.y = y;}
  }
}
