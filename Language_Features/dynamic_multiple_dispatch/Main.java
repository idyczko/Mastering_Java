

public class Main {
	
	public static void main(String args[] ) {
		A a = new B();
		meth(a);
	}

	public static void meth(B b) {
		System.out.println("I will serve B");
	}
	
	public static void meth(C c) {
		System.out.println("I will serve C");
	}

	public static class A {}

	public static class B extends A {}

	public static class C extends A {}
}
