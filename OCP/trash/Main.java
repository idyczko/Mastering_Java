import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
	
		List<Integer> list = new ArrayList<>();
		short s = 10;
		list.add((int) s);
		A a = new A();
		B b = new B();
		A ab = new B();
		Object o = true ? "DASDSA" : 10;
		try {
		a.meth();
		a.meth2();
		b.meth();
		b.meth2();
		ab.meth();
		ab.meth2();
		} catch (IOException e) {
		} catch (RuntimeException e) {
		} catch (Exception e) {}
	}
}

class A {
	void meth() throws IOException{}
	void meth(int i) {}
	void meth(String s) {}
	void meth2() {}
}

class B extends A {
	@Override
	protected void meth() {}
}
