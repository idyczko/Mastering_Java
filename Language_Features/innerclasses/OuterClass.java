public class OuterClass {
  private static int someStaticField = 12;

  private int someField;

  public OuterClass(int someField) {
    this.someField = someField;
  }

  public int getSomeField() {
    return this.someField;
  }

  public static int performSomeCalculations() {
    return someStaticField * 2 - 10;
  }

  public int doSomeOtherStuff(int someInput) {
    //This is some fine usage of anonymous inner class.
    SomeInnerInterface sii = new SomeInnerInterface() {
      @Override
      public int doSomeStuff() {
        return 10;
      }
    };
    return someInput * sii.doSomeStuff();
  }

  public int doSomeStuffWithInnerLocalClass(int input) {
    //Any access delimiter will cause problems
    /*public would be inapropriate*/ class LocalClass {
      private int parameter;

      public LocalClass(int parameter) {
        this.parameter = parameter;
      }

      public int doSomeMath(int input) {
        return input * parameter;
      }
    }

    LocalClass lc = new LocalClass(10);

    return lc.doSomeMath(input);
  }

  public static class StaticNestedClass {
    private int innerField;

    public StaticNestedClass(int innerField) {
      this.innerField = innerField * performSomeCalculations();
    }

    public int getInnerField() {
      return innerField;
    }
  }

  public class SimpleInnerClass {
    private int someInnerField;

    public SimpleInnerClass(int someInnerField) {
      this.someInnerField = someInnerField * someField;
    }

    public int getSomeInnerField() {
      return this.someInnerField;
    }
  }

  public interface SomeInnerInterface {
    public default int doSomeStuff() { //Some Java 8 goodies!
      return 5;
    }
  }
}
