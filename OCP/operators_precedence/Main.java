

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

class A {
	public static void meth() {}
}

class B extends A {
	public static void meth(){}

	public void meth2(){
		super.meth();
	}
}
