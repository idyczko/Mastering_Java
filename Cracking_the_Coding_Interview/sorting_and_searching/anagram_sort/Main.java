import java.util.*;
import java.util.stream.*;

public class Main {

	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		strings.add("tata");
		strings.add("mama");
		strings.add("bobo");
		strings.add("amam");
		strings.add("atta");
		strings.add("maam");
		strings.add("bboo");

		sort(strings).forEach(item -> System.out.println(item));
	}

	private static Collection<String> sort(List<String> strings) {
		Map<String, List<String>> anagrams = new HashMap<>();
		strings.forEach(item -> {
			String key = sort(item);
			if (anagrams.containsKey(key))
				anagrams.get(key).add(item);
			else
				anagrams.put(key, new ArrayList<String>() {{add(item);}});
		});
		return anagrams.values().stream().flatMap(v -> v.stream()).collect(Collectors.toList());
	}

	private static String sort(String str) {
		char[] chars = str.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

	private static String quickSort(String str) {
		char[] chars = str.toCharArray();
		quicksort(chars, 0 ,chars.length);
		return new String(chars);
	}

	private static void quicksort(char[] chars, int begin, int end) {
		int pivot_index = begin + (end - begin)/2;
		int pivot = chars[pivot_index];
		p
		int i = begin, j = end;
		while (i < pivot_index && j > pivot_index) {
			while (i < pivot_index && chars[i] > pivot)
				i++;
			while (j > pivot_index && chars[j] < pivot)
				j--;
			if ()
		}
		
		quicksort(chars, begin, pivot - 1);
		quicksort(chars, pivot + 1, end);
	}

	private static void 
} 
