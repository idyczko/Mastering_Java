import java.util.concurrent.atomic.AtomicInteger;

public class Permutations {

  public static void printAllPermutations(String str) {
    printAllPermutations(str, "", new AtomicInteger());
  }

  private static void printAllPermutations(String str, String prefix, AtomicInteger count) {
    if(str.length() == 0)
      System.out.println(count.incrementAndGet() + ": " + prefix);
    else
      for(int i = 0; i < str.length(); i++) {
        String rem = str.substring(0, i) + str.substring(i + 1);
        printAllPermutations(rem, prefix + str.charAt(i), count);
      }
  }
}
