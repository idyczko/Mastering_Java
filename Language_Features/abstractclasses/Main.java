public class Main {

  public static void main(String[] args) {
    DerivedClass dc = new DerivedClass("Tobey");
    AbstractClass ac = dc;
    /*First opration has a astonishing result - it will produce "AbstractTobey".
    It seems, that the method call is being delegated to base class, where "this"
    means what "super" would mean in the child class. Therefore the field from
    the base class is being read.
    */
    Sysyem.out.println(dc.getName());
    System.out.println(dc.name);
    System.out.println(ac.getName());
    System.out.println(ac.name);
  }
}