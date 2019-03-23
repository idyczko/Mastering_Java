
public class Main {

	public static void main(String[] args) {
		meth("asda");
		meth(123);
		Number n = 12;
		meth(n);
	}

	private static final void meth(String s) {
		System.out.println("String");
	}

	static private final void meth(int i) {
		System.out.println("int");
	}

	private final static void meth(Integer i) {
		System.out.println("Integer");
	}

	final private static void meth(Number n) {
		System.out.println("Number");
	}
}
