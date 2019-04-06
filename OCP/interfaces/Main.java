
public class Main {

	public static void main(String args[]) {
	try {
		int o = 10;
	} catch (NullPointerException e) {
	} finally {
		throw new RuntimeException();
	}
	}
	public static void meth() throws Exception {
		throw new MuEx();
	}
}

class MuEx extends Exception {}

class Cl implements I1, I5 {
	public boolean meth() {
		return true;
	}
}

abstract class Ab4 implements I1, I5 {
	
	public abstract boolean meth();
}

abstract class Ab3 implements I3, I4 {}

abstract class Ab2 implements I1, I2 {
	
	public boolean meth() {
		return false;
	}
}

abstract class Ab implements I1, I2 {
	
	public abstract boolean meth();
}

interface I6 extends I3, I5 {}

interface I3 extends I1, I2 {
	
	@Override
	default boolean meth() {
		return true;
	}
}

interface I5 {
	
	boolean meth();
}

interface I1{
	
	default boolean meth() {
		return false;
	}
}

interface I2 {
	default boolean meth() {
		return true;
	}
}

interface I4 {
	int meth();
}
