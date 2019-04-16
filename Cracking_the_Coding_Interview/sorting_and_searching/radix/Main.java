
public class Main {

	public static void main (String[] args){
		int[] ints = new int[args.length];
		for (int i = 0; i < args.length; i++)
			ints[i] = Integer.parseInt(args[i]);
		radixSort(ints);
		for (int i : ints)
			System.out.println(i);
	}

	private static void radixSort(int[] int) {
		
	}
}
