package package_b;

import package_a.*;
import package_c.*;
import package_b.package_d.*;

public class CClass extends A{
	
	public void meth2() {
		CClass c = new CClass();
		c.meth();
	}

	public void meth6() {
		EClass e = new EClass();
		e.meth();
	}

	public void meth3() {
		BClass b = new BClass();
		//b.meth();
	}

	public void meth4() {
		A a = new A();
		//a.meth();
	}

	public void meth5() {
		A a = new CClass();
		//a.meth();
	}
}
