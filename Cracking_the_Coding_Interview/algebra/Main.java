public class Main {

  public static void main(String args[]) {
    long start = System.currentTimeMillis();
    CubicPairsFinder.stupid(Integer.parseInt(args[0]));
    long end = System.currentTimeMillis();
    System.out.println("The calculation took: " + (end - start) + " milliseconds");
    start = System.currentTimeMillis();
    CubicPairsFinder.lessStupid(Integer.parseInt(args[0]));
    end = System.currentTimeMillis();
    System.out.println("The calculation took: " + (end - start) + " milliseconds");
    start = System.currentTimeMillis();
    CubicPairsFinder.smart(Integer.parseInt(args[0]));
    end = System.currentTimeMillis();
    System.out.println("The calculation took: " + (end - start) + " milliseconds");
    start = System.currentTimeMillis();
    CubicPairsFinder.smarter(Integer.parseInt(args[0]));
    end = System.currentTimeMillis();
    System.out.println("The calculation took: " + (end - start) + " milliseconds");
    start = System.currentTimeMillis();
    CubicPairsFinder.brilliant(Integer.parseInt(args[0]));
    end = System.currentTimeMillis();
    System.out.println("The calculation took: " + (end - start) + " milliseconds");
  }

}
