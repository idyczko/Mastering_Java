public class Main {

  public static void main(String[] args) {
    /* Instantiation of nested static class does not require
    * any instance of outer class. We can create it like below.
    * It only references the static context of the outer class,
    * so there is no need for outer class being instantiated.
    */
    OuterClass.StaticNestedClass snc = new OuterClass.StaticNestedClass(12);
    System.out.println(snc.getInnerField());

    /*The following wouldn't have worked, as we need a specific instance
    * to ensure dynamic context for an instance of the inner class.
    */
    //OuterClass.SimpleInnerClass sic = new OuterClass.SimpleInnerClass(143);

    OuterClass oc = new OuterClass(12);
    OuterClass.SimpleInnerClass sic = oc.new SimpleInnerClass(32); //Twisted syntax, am I right?
    System.out.println(sic.getSomeInnerField());

    //Calling method with local inner class:
    System.out.println(oc.doSomeStuffWithInnerLocalClass(12));

    //Calling method with anonymous inner class:
    System.out.println(oc.doSomeOtherStuff(42));
  }
}
