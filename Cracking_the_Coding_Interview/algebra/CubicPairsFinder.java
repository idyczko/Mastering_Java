import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CubicPairsFinder {

  public static void stupid(int limit) {
    int counter = 0;
    for(int a = 1; a <= limit; a++)
      for (int b = 1; b <= limit; b++)
        for (int c = 1; c <= limit; c++)
          for (int d = 1; d <= limit; d++)
            if (cube(a) + cube(b) == cube(c) + cube(d))
              System.out.println(++counter  + ": " + a + " " + b + " " + c + " " + d);
  }

  public static void lessStupid(int limit) {
    int counter = 0;
    for(int a = 1; a <= limit; a++)
      for (int b = 1; b <= limit; b++)
        for (int c = 1; c <= limit; c++)
          for (int d = 1; d <= limit; d++)
            if (cube(a) + cube(b) == cube(c) + cube(d)) {
              System.out.println(++counter  + ": " + a + " " + b + " " + c + " " + d);
              break;
            }
  }

  public static void smart(int limit) {
    int counter = 0;
    for(int a = 1; a <= limit; a++)
      for (int b = 1; b <= limit; b++)
        for (int c = 1; c <= limit; c++) {
          int d = (int) Math.round(Math.cbrt(cube(a) + cube(b) - cube(c)));
          if (cube(a) + cube(b) == cube(c) + cube(d) && d > 0 && d <= limit)
          System.out.println(++counter  + ": " + a + " " + b + " " + c + " " + d);
        }
  }

  public static void smarter(int limit) {
    class Pair {
      public int c;
      public int d;

      public Pair(int c, int d) {this.c = c; this.d = d;}
    }
    int counter = 0;
    Map<Integer, ArrayList<Pair>> preprocessedSums = new HashMap<Integer, ArrayList<Pair>>();
    for (int c = 1; c <= limit; c++)
      for (int d = 1; d <= limit; d++){
        int sum = cube(c) + cube(d);
        if(!preprocessedSums.containsKey(sum)) {
          preprocessedSums.put(sum, new ArrayList<Pair>());
        }
        preprocessedSums.get(sum).add(new Pair(c, d));
      }
    for(int a = 1; a <= limit; a++)
      for (int b = 1; b <= limit; b++)
        for (Pair pair : preprocessedSums.get(cube(a) + cube(b)))
          System.out.println(++counter  + ": " + a + " " + b + " " + pair.c + " " + pair.d);
  }

  public static void brilliant(int limit) {
    class Pair {
      public int c;
      public int d;

      public Pair(int c, int d) {this.c = c; this.d = d;}
    }
    int counter = 0;
    Map<Integer, ArrayList<Pair>> preprocessedSums = new HashMap<Integer, ArrayList<Pair>>();
    for (int c = 1; c <= limit; c++)
      for (int d = 1; d <= limit; d++){
        int sum = cube(c) + cube(d);
        if(!preprocessedSums.containsKey(sum)) {
          preprocessedSums.put(sum, new ArrayList<Pair>());
        }
        preprocessedSums.get(sum).add(new Pair(c, d));
      }
    for (Map.Entry<Integer, ArrayList<Pair>> entry : preprocessedSums.entrySet())
      for (Pair pairOne : entry.getValue())
        for (Pair pairTwo : entry.getValue())
          System.out.println(++counter  + ": " + pairOne.c + " " + pairOne.d + " " + pairTwo.c + " " + pairTwo.d);
  }


  private static int cube(int n) {
    return (int) Math.pow((double) n, 3);
  }
}
