

public class Main {

	final static int case3 = 20;

	static {
	}
	
	public static void main(String[] args) {
	
		int x = 10;
		int y = x++ * x--;
		int z = ++y * --y;
		int w = --z * z++;
		int c = 10;
		int v = --c * c++;
		System.out.println(y + " " + z + " " + w + " " + v);

		Double d = 1.2;
		Integer i = null;
		System.out.println(d >= i);
	}
}

abstract class A { 
	public final static void meth() {}

	abstract void meth3();
}

class B extends A implements I1, I2{

	public void meth2(){
		super.meth();
		meth();
	}

	void meth3() {}

	public void meth4() {}
}

interface I1 {
	void meth4();
}

interface I2 {
	default void meth4() {}
}
