
public class CTCIStringUtils {

  public static boolean isUniqueChars(String str) {
    int firstHalf = 0;
    int secondHalf = 0;
    for (int i = 0; i < str.length(); i++) {
      int letterIndex = str.charAt(i) - 'A';
      if (letterIndex < 32 ? (firstHalf & (1 << letterIndex)) > 0 : (secondHalf & (1 << letterIndex)) > 0 )
        return false;
      if (letterIndex < 32)
        firstHalf |= (1 << letterIndex);
      else
        secondHalf |= (1 << letterIndex);
    }
    return true;
  }
}