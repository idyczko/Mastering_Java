import stringbuilder.StringBuilder;
import fizzbuzz.FizzBuzz;

public class Main {
  private static final int REPEAT_TIMES = 10000;
  public static void main(String[] args) {
    //String Builder
    long startTime = System.currentTimeMillis();
    StringBuilder sb = new StringBuilder(1000000);
    for(int i = 1; i < REPEAT_TIMES; i++) {
      sb.append("I won't do it again for the " + i + suffix(i) + " time!\n");
    }
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("String builder performance: " + elapsedTime + " ms");

    startTime = System.currentTimeMillis();
    String buffer = "";
    for(int i = 1; i < REPEAT_TIMES; i++) {
      buffer += "I won't do it again for the " + i + suffix(i) + " time!\n";
    }

    stopTime = System.currentTimeMillis();
    elapsedTime = stopTime - startTime;
    System.out.println("Simple string addition performance: " + elapsedTime + " ms");

    System.out.println("Operation finished: " + (buffer.equals(sb.toString())? "success" : "failure"));

    //FizzBuzz
    int[] numbers = new int[100];
    for(int i = 0; i<100; i++){
      numbers[i] = i;
    }
    System.out.println(FizzBuzz.fizzBuzz(numbers, 3, 5));

  }

  public static String suffix(int number) {
    switch(number%10) {
      case 1:
        return "st";
      case 2:
        return "nd";
      case 3:
        return "rd";
      default:
        return "th";
    }
  }
}
