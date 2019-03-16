import java.util.*;

public class Main {

	public static void main(String[] args) {
		Number n = new Integer(12);
		Integer i = new Integer(12);
		if (n == i)
			return;
		String s = "java";
		StringBuilder sb = new StringBuilder("java");
		Object os = s;
		Object osb = sb;
		if (os == osb)
			return;
		if (s == sb)
			System.out.println("==");
		else if (s.equals(sb))
			System.out.println("equals");

		
	}
}
