public class Main {

  public static void main(String[] args) {
    BaseClass baseClass = new BaseClass(12);
    BaseClass baseClass_2 = new SubClass();
    BaseClass baseClass_3 = new BaseClass.InnerSubClass();
    BaseClass baseClass_4 = new SubClass.StaticNestedClassSubClass();
    //You have to down-cast explicitly! Funny syntax again!
    BaseClass baseClass_5  = ((SubClass) baseClass_2).new InnerClassSubClass();
  }
}
