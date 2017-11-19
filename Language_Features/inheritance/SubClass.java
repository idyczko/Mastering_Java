public class SubClass extends BaseClass {

  public SubClass() {
    /* If superclass' non-parameterized constructor is private compiler won't
    * allow to build solution.
    */
    //super() // Won't fly, BaseClass() is private.
    super(10); //Voila, BaseClass(int) is public.
  }

  public static class StaticNestedClassSubClass extends BaseClass.InnerSubClass {

    public StaticNestedClassSubClass() {
      super();
    }
  }

  public class InnerClassSubClass extends BaseClass.InnerSubClass {

    public InnerClassSubClass() {
      super();
    }
  }
}
