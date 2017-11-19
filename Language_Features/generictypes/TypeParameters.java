import java.util.*;

public class TypeParameters {

  public static void main(String[] args) {
    A a = new A();
    List<A> list = new ArrayList<>();
    list.add(a);
    doSomething(list);
    doSomethingElse(list);

    I i = new E();
    /*"Upcast" had to be performed explicitly this time, as it wasn't even actual
    * upcast. We were passing around reference to class implementing some interface,
    * it only happend by accident it was actually an instance of descendant of A!
    * Interface only marks behaviour, not relation, so compiler won't allow it without
    * proper casting.
    */
    list.add((A) i);

    //Take a look at this:
    I ii = new Z();
    /*BOOM!!! ClassCastException! Compiler allowed this statement, as at compile-time
    * it was possible, that I type reference was referencing descendant of A, but
    * dynamic cast check has proven it to be faulty!!! Bamboozled again!
    */
    //list.add((A) ii); //KABOOM!

    List<I> list_2 = new ArrayList<I>();
    A aa = new A();
    //list_2.add((E) aa); //KABOOM! ClassCastException!
    aa = new E();
    E e = (E) aa; //Explicit downcasting!
    list_2.add((E) aa); //No problemo! Explicito downcasting!
    list.add(aa); //No problemo!
    list.add(e); // No problemo! Implicito upcasting!
  }

  public static <T extends A> void doSomething(List<T> t){}

  /* The following method cannot be compiled, as type parameters only comply with
  * "upper bound", meaning that they are compatible with extends.
  public static <T super D> void doSomething(T t){

  }
  * But we can do that (method's name had to be different due to non-exact signatures
  * after type erasure!):
  */
  public static void doSomethingElse(List<? super D> a) {}

  public static <T extends I> void doSthg(List<T> list) {}

  public static void doSthg2(List<? extends I> list) {}

  public static class A {}

  public static class B extends A {}

  public static class C extends A {}

  public static class D extends B {}

  public interface I {
    /*If default implementation is provided we do not have to implement the method
    * in implementing classes.
    */
    public default int calc(int i) {
      return i * 2;
    }
  }

  public interface J extends I {
    /*Derived interface apparently can override default behaviour - as expected :)
    */
    public default int calc(int i) {
      return i * 2;
    }
  }

  public static class E extends D implements J {}

  public static class Z implements J{}
}
