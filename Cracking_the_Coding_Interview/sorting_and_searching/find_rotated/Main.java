
public class Main {

	public static void main(String[] args) {
		int size = Integer.valueOf(args[0]);
		int search = Integer.valueOf(args[1]);
		int[] array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = Integer.valueOf(args[2 + i]);
		}

		System.out.println(find(search, array));
	}

	private static int find(int search, int[] array) {
		return find(search, array, 0, array.length - 1);
	}

	private static int find(int search, int[] array, int lo, int hi) {
		int pivotIndex = (lo + hi)/2;
		int pivot = array[pivotIndex];

		if (pivot == search)
			return pivotIndex;

		if (lo >= hi)
			return -1;

		if (array[lo] <= pivot && pivot <= array[hi])
			return search > pivot ? find(search, array, pivotIndex + 1, hi) : find(search, array, lo, pivotIndex - 1);

		else if (array[hi] <= array[lo] && array[lo] <= pivot)
			if (search > pivot)
				return find(search, array, pivotIndex + 1, hi);
			else
				if (search > array[hi])
					return find(search, array, lo, pivotIndex - 1);
				else
					return find(search, array, pivotIndex + 1, hi);

		else if (array[hi] <= array[lo] && array[hi] >= pivot)
			if (search < pivot)
				return find(search, array, lo, pivotIndex - 1);
			else
				if (search < array[lo])
					return find(search, array, pivotIndex + 1, hi);
				else
					return find(search, array, lo, pivotIndex - 1);
		else
			throw new IllegalStateException("The data is incorrect.");
	}
}
