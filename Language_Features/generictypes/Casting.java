import java.util.*;

public class Casting {

  public static void main(String[] args){
    /* The following will not be allowed, as generic types parameters are checked
    * statically at compiling time. Type erasure happens only at runtime!
    */
    //List<Integer> list = new ArrayList<String>(); //Incompatible types at compile time!

    List<Integer> list = new ArrayList<>();
    System.out.println(list.getClass());//It is an ArrayList.
    list.add(Integer.valueOf(10));

    /*I can also do something like that, because downcasting is permited in this scenario,
    * but it would cause runtime ClassCastException, because ArrayList cannot be converted
    * to LinkedList. Compiler allowed it, because we were passing around List reference,
    * so static check figured out, it would be possible for list object to be "castable"
    * to LinkedList<Integer>.
    */
    //LinkedList<Integer> list_2 = (LinkedList<Integer>) list;

    List<Integer> list_3 = list; //Allowed, as supertype can be reference to subtype - explicit upcasting.
    someFunction(list);

    Object obj = list;
    someOtherFunction((List<Integer>) obj); //Also will cause unchecked warning! It demands explicit downcasting!

    /* The following will cause runtime exception, as straight A cannot be converted to B.
    * The compiler however allowed it to be compiled, as A reference could be bound to an
    * actual B object.
    */
    //A a = new A(); //BAD
    A a = new B(); //Now we're talking! GOOD with implicit upcasting!
    B b = (B) a;

    //Now, is LinkedList<String> instanceof List<String>:
    System.out.println(new LinkedList<String>() instanceof List); //Yep, it is!
  }

  public static void someFunction(Object obj) {
    /*Will cause "unchecked cast" warning because of explicit cast at runtime. This
    * is a dynamic cast, performed at runtime. For safety reasons compiler will emmit
    * "Unchecked" warning for every dynamic cast whose target type is parametrized type.
    */
    List<String> list = (List<String>) obj;

    /*The following will return false, as we have a list of Integers, but we pass
    *around a reference to list of Strings!!! (sic!)
    */
    System.out.println(list.get(0) instanceof String);

    /* KABOOM! ClassCastException! The operation looks fine, as we are taking a String from
    * a String list, but surprise, surprise, it is a list of Integers, which cannot be cast
    * to String implicitly! Badass! Runtime apocalipse!
    */
    //String someString = list.get(0); //BOOM!!!
  }

  //Just to prove a point with passing casted object!
  public static void someOtherFunction(List<Integer> list){
  }

  //Two stupid classes for test purposes. Those have to be static nested classes, as we want them
  //not to be bound to dynamic context of particular instance.
  public static class A {

  }

  public static class B extends A {

  }
}
