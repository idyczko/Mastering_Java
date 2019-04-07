package package_a;

import package_c.*;

public class BClass extends A{

	public void meth2(A a) {
		System.out.println("Meth 2 from BClass.");
		//a.meth(); Not allowed, because A is in another package and the method has protected access! Any access not through inheritance and not securing A reference's full type is illegal!
	}
}
