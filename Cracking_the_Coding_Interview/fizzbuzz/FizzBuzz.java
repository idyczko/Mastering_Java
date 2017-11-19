package fizzbuzz;

public class FizzBuzz {
  public static final String EMPTY_STRING = "";

  public static String fizzBuzz(int number, int f, int b){
    String outcome = "";
    if(number % f == 0) {
      outcome += "Fizz";
    }
    if(number % b == 0) {
      outcome += "Buzz";
    }
    if(EMPTY_STRING.equals(outcome)){
      return String.valueOf(number);
    }
    return outcome;
  }

  public static String fizzBuzz(int[] numbers, int f, int b){
    StringBuilder sb = new StringBuilder();
    for(int i : numbers){
      sb.append(fizzBuzz(i, f, b) + " ");
    }
    return sb.toString();
  }
}
