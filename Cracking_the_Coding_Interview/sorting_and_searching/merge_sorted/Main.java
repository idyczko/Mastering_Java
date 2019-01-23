

public class Main {

	public static void main(String args[]) {
	
		int sizeA = Integer.valueOf(args[0]);
		int sizeB = Integer.valueOf(args[1]);
		int[] a = new int[sizeA + sizeB];
		int[] b = new int[sizeB];
		for (int i = 0; i < sizeA; i++)
			a[i] = Integer.valueOf(args[i + 2]);
		for (int i = 0; i < sizeB; i++)
			b[i] = Integer.valueOf(args[i + 2 + sizeA]);
		merge(a, sizeA ,b);
		System.out.println();
		for (int i : a)
			System.out.print(i + " ");
	}

	private static void merge(int[] a, int a_end, int[] b) {
		int b_index = b.length;
		int a_index = a_end;
		int sorted = a.length;
		while (b_index > 0) {
			if (a_index == 0 || b[b_index - 1] > a[a_index - 1]) {
				a[--sorted] = b[--b_index];
			} else {
				a[--sorted] = a[--a_index];
			}
		}
	}
}
