public class BaseClass {
  private BaseClass() {}

  public BaseClass(int i) {}


  /*That one is allowed, as inner class has access to super class' private methods.
  * That is how you construct a builder BTW! With private constructor and static
  * nested class, but without inheritance!
  */
  public static class InnerSubClass extends BaseClass{

    /* No-problemo, constructor is not part of instance's dynamic context,
    * so it may be used in static nested class!
    */
    public InnerSubClass() {
      super();
    }
  }
}
