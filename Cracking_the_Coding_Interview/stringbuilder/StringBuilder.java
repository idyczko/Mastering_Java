package stringbuilder;

import java.util.Arrays;

public class StringBuilder {
  private static final int DEFAULT_INITIAL_SIZE = 10;
  private static final int GROWTH_FACTOR = 2;

  private char[] characterBuffer;
  private int currentIndex;

  public StringBuilder() {
    this(DEFAULT_INITIAL_SIZE);
  }

  public StringBuilder(int initialSize) {
    characterBuffer = new char[initialSize];
    currentIndex = 0;
  }

  public void append(String str) {
    if(str != null){
      if(exceedsSize(str)){
        adjustBufferSize();
      }
      for(char c : str.toCharArray()) {
        characterBuffer[currentIndex++] = c;
      }
    }
  }

  public String toString() {
    return new String(characterBuffer, 0, currentIndex);
  }

  private boolean exceedsSize(String str) {
    return str.length() + currentIndex > characterBuffer.length;
  }

  private void adjustBufferSize() {
    characterBuffer = Arrays.copyOf(characterBuffer, characterBuffer.length * GROWTH_FACTOR);
  }

}
