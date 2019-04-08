import java.util.*;

public class Main {

	public static void main(String[] args) {
	
		List<Integer> list = new ArrayList<>();
		short s = 10;
		list.add((int) s);
	}
}

class A {
	void meth() {}
}

class B extends A {
	@Override
	protected void meth() {}
}
