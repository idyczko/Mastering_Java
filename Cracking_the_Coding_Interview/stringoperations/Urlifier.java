public class Urlifier {

  public static String urlify(String str, int trimmedLength) {
    char[] chars = str.toCharArray();
    int spaces = (str.length() - trimmedLength)/2;
    for(int i = trimmedLength - 1; i > 0 && spaces > 0; i--) {
      if(chars[i] == ' ') {
        chars[i + 2*spaces] = '0';
        chars[i + 2*spaces - 1] = '2';
        chars[i + 2*spaces - 2] = '%';
        spaces--;
        continue;
      }
      chars[i + 2*spaces] = chars[i];
    }
    return new String(chars);
  }
}
