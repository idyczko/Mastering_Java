import java.util.*;
import java.util.stream.*;

public class Cache {

	
	public static void main(String[] args) {
	
		Set<Character> cache = initialCache(args[0]);
		String evictionSchedule = planCacheEviction(cache, sequence(args[1]));
		System.out.println(evictionSchedule);
	}

	private static String planCacheEviction(Set<Character> cache, List<Character> sequence) {
		int step = 0;
		StringBuilder cacheEviction = new StringBuilder();

		for (int i = 0; i < sequence.size(); i++) {
			if (!cache.contains(sequence.get(i))) {
				Set<Character> cacheCopy = new HashSet<>(cache);
				for (int j = i + 1; j < sequence.size(); j++) {
					if (cacheCopy.size() == 1)
						break;
					
					cacheCopy.remove(sequence.get(j));
				}

				cache.remove(cacheCopy.stream().findAny().get());
				cache.add(sequence.get(i));
				cacheEviction.append(step + " " + cacheCopy.stream().findAny().get() + " => " + sequence.get(i));
			}
			step++;
		}
		return cacheEviction.toString();
	}

	private static Set<Character> initialCache(String initialCache) {
		return Arrays.stream(initialCache.substring(1, initialCache.length() - 1).split(",")).map(s -> Character.valueOf(s.toCharArray()[0])).collect(Collectors.toSet());
	}

	private static List<Character> sequence(String sequence) {
		return Arrays.stream(sequence.substring(1, sequence.length() - 1).split(",")).map(s -> Character.valueOf(s.toCharArray()[0])).collect(Collectors.toList());
	}

}
