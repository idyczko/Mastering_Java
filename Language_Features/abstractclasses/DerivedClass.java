public class DerivedClass extends AbstractClass {
  public String name;

  public DerivedClass(String name) {
    super("Abstract" + name);
    this.name = name;
  }

}