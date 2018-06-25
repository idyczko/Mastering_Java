import static java.lang.Math.*;

public class LevenshteinDistanceCalculator {

  public static boolean oneAway(String expected, String actual) {
    char[] expectedChars = expected.toCharArray();
    char[] actualChars = actual.toCharArray();
    int lengthDiff = expectedChars.length - actualChars.length;
    if(abs(lengthDiff) >= 2)
      return false;

    int i = 0;
    while (i < expectedChars.length) {
      if(expectedChars[i] != actualChars[i]) {
        return compareRemainder(expectedChars, i + (lengthDiff >= 0 ? 1 : 0), actualChars, i + (lengthDiff <= 0 ? 1 : 0));
      }
      i++;
    }
    return true;
  }

  private static boolean compareRemainder(char[] chars1, int i, char[] chars2, int j) {
    if(chars1.length - i != chars2.length - j)
      throw new IllegalArgumentException("Remainders should be of the same length!");

    while (i < chars1.length)
      if(chars1[i++] != chars2[j++])
        return false;

    return true;
  }
}
