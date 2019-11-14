import java.util.*;

public class Powerset {
	
	public static void main(String[] args) {
		
		List<Integer> set = new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7}));//Let's assume it is inherently a set and the elements are distinct
		List<List<Integer>> powerset = computePowerset(set);
		for (List<Integer> subset : powerset) {
			System.out.println();
			subset.forEach(e -> System.out.print(e + " "));
		}
	}

	private static <T extends Comparable> List<List<T>> computePowerset(List<T> elements) {
		if (elements.isEmpty()) {
			List<List<T>> initialPowerset = new ArrayList<>();
			initialPowerset.add(new ArrayList<>());
			return initialPowerset;
		}

		T firstElement = elements.remove(0);
		return mergePowerset(firstElement, computePowerset(elements));
	}

	private static <T extends Comparable> List<List<T>> mergePowerset(T element, List<List<T>> powerset) {
		//If there is a lot of elements a recurrence may not be the best choice in a language which is designed for imperative use. It is beautiful though.

		
		if (powerset.isEmpty())
			powerset.add(new ArrayList<>());

		List<List<T>> newPowerset = new ArrayList<>(powerset);

		for (List<T> set : powerset) { //I do not like using sets, as it will internally enforce cardinality O(log(n)). But for the sake of argument let's assume it simply adds an element to a collection.
			List<T> mergedSet = new ArrayList<>(set);
			mergedSet.add(element);
			newPowerset.add(mergedSet);
		}

		return newPowerset;
	}
}
