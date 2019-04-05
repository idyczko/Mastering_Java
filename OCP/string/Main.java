import java.util.*;
class Main {

	public static void main(String[] args) {
	
		String h = "Hello";
		String s = "Hello World";
		String d = "Hello World".trim();
		System.out.println((s == d) + " " + (s.trim() == d) + " " + (h == s.substring(0, 5)));
		System.out.println(h + " " + s.substring(0, 5) + " " + h.equals(s.substring(0, 5)));

		int nums[] = new int[0];
		String[] strings = new String[10];
		Object[] objs = strings;
		objs[0] = new Integer(10);
		Object obj = new int[] {1,2,3};
		
		List<Number> l = new ArrayList<>();
		short ss = 10;
		l.add(ss);
		Integer i = null;
		meth(new Integer(0), i);
	}

	static public final void meth(Integer i, Integer... is) {
		System.out.println("meth integer, varargs");
	}

	final public static void meth(Integer i, Integer j) {
		System.out.println("meth for integer, integer");
	}
}
