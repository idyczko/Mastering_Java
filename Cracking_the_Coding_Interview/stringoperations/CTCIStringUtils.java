
public class CTCIStringUtils {

  public static boolean isUniqueChars(String str) {
    int vector = 0;
    for (int i = 0; i < str.length(); i++) {
      int letterIndex = str.charAt(i) - 'A';
      System.out.println(str.charAt(i) + ": " + letterIndex);
      if ((vector & (1 << letterIndex)) > 0 )
        return false;
      vector |= (1 << letterIndex);
    }
    return true;
  }
}