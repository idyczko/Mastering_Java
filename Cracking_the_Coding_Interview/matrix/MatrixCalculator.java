public class MatrixCalculator {


  public static Point rotatePoint(int size, Point p) {
    return new Point(){{x = size - p.y - 1; y = p.x;}};
  }

  public static class Point {
    public int x;
    public int y;
  }
}
