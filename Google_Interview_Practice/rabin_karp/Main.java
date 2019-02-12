
public class Main {

	public static void main(String[] args) {
		if (args.length < 2)
			throw new IllegalArgumentException("This is not how I wanna be used!");
		System.out.println("Index of " + args[0] + " in " + args[1] + ": " +  PatternFinder.find(args[0], args[1]));
	}
}
