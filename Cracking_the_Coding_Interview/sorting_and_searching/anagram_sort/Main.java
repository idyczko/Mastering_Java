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

		System.out.println(quicksort("djhfawhfadnbcakjvhfawliehfu"));
		sort(strings).forEach(item -> System.out.println(item));
	}

	private static Collection<String> sort(List<String> strings) {
		Map<String, List<String>> anagrams = new HashMap<>();
		strings.forEach(item -> {
			String key = quicksort(item);
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

	private static String quicksort(String str) {
		char[] chars = str.toCharArray();
		quicksort(chars, 0 ,chars.length - 1);
		return new String(chars);
	}

	private static void quicksort(char[] chars, int begin, int end) {
		if (begin >= end)
			return;

		int i = partition(chars, begin, end);
		quicksort(chars, begin, i);
		quicksort(chars, i + 1, end);
	}

	private static int partition(char[] chars, int begin, int end) {
		char pivot = chars[(begin + end)/2];
		int i = begin - 1;
		int j = end + 1;
		while(true) {
			while (chars[++i] < pivot);

			while (chars[--j] > pivot);

			if (i >= j)
				return j;
			
			swap(chars, i, j);
		}
	}

	private static void swap(char[] chars, int i, int j) {
		char c = chars[i];
		chars[i] = chars[j];
		chars[j] = c;
	} 
} 
