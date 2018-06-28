import java.util.concurrent.atomic.AtomicInteger;

public class Permutations {

  //No capital letters allowed.
  public static boolean isPermutationOfPalindrome(String str) {
    int vector = 0;
    char[] chars = str.replaceAll(" ", "").toLowerCase().toCharArray();

    for(int i = 0; i < chars.length; i++) {
      vector ^= (1 << (chars[i] - 'a'));
    }
    return (vector & (vector - 1)) == 0;
  }

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
