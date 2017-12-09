import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {

  public static void main(String[] args) {

    Integer[] integers = {1,2,3,4,5,6}; //DUH, autoboxing :)

    /*The code below will cause runtime casting exception, as ArrayList returned
    *by Arrays package is an internal type java.util.Arrays$ArraysList
    */
    //ArrayList<Integer> ints = (ArrayList<Integer>) Arrays.asList(integers);

    List<Integer> ints = Arrays.asList(integers);
    /* UnsupportedOperationException! The list is not expandable!
    */
    //ints.add(10);
    ints.stream().forEach(item -> System.out.println(item));
    ints.set(2,10);

    /*Changes on list will propagate to underlying array!
    */
    Stream.of(integers).mapToInt(item -> item.intValue()).forEach(item -> System.out.println(item));
  }
}
