import package_a.*;
import package_b.*;
import package_c.*;

public class Main {

	public final int f;

	Main() {
		f = 10;
	}

	public static void main(String[] args) {
		A a = new A();
		BClass b = new BClass();
		CClass c = new CClass();
		DClass d = new DClass();

		meth(new Integer(0));
		byte bb = 0;
		meth(bb);
		
		int i = 10;
		meth(i);
	}

	public static void meth(Object o) {
		System.out.println("Object");
	}

	public static void meth(Number n) {
		System.out.println("Number");
	}

	public static void meth(short s) {
		System.out.println("short");
	}

	I i = (i) -> true;
}

abstract class B {
	abstract void meth();
}

abstract interface I {
	
	public abstract boolean dodeedo(Integer i);

	public static void meth() {
		System.out.println("meth");
	}
}
