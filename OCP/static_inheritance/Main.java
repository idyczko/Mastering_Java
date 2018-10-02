
public class Main {
	public static void main(String[] args) {
		for (String arg : args)
			System.out.println(arg);
		Writer w = new Programmer();
		w.writeHidden();
		w.writeInherited();
	}
}
