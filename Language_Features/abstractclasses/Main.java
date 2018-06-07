public class Main {

  public static void main(String[] args) {
    DerivedClass dc = new DerivedClass("Tobey");
    AbstractClass ac = dc;
    System.out.println(dc.getName());
    System.out.println(dc.name);
    System.out.println(ac.getName());
    System.out.println(ac.name);
  }
}