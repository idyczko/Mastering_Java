import static java.lang.Math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.regex.*;

public class Main {

	public static MarkovChain markovChain = new MarkovChain();

	public static void main(String[] args) {
		int lenght = Integer.valueOf(args[0]);
		double endProb = Double.valueOf(args[1]);
		
		Scanner sc = new Scanner(System.in).useDelimiter(Pattern.compile("\\s"));
		StringBuilder key = new StringBuilder();
		if (!sc.hasNext())
			throw new IllegalStateException();

		key.append(sc.next()).append(" ");
		lenght--;
		while (--lenght >= 0 && sc.hasNext()) {
			key.append(sc.next()).append(" ");
		}

		while (sc.hasNext()) {
			String next = sc.next();
			markovChain.add(key.toString().trim().toLowerCase(), next.trim().toLowerCase());
			key.delete(0, key.indexOf(" ") + 1).append(next).append(" ");
		}
		
		if (Arrays.asList(args).contains("-l"))
			markovChain.print();
		
		StringBuilder sb = new StringBuilder();
		int randomIndex = (int) (Math.random() * markovChain.chain.keySet().size());
		int i = 0;
		String randomKey = "";
		for (String s : markovChain.chain.keySet()) {
			if (++i > randomIndex) {
				randomKey = s;
				break;
			}
		}
		String key2 = randomKey + " ";
		sb.append(key2);
		while (endProb > Math.random()) {
			String next = markovChain.getNext(key2.trim());
			if (next == null)
				break;
			next += " ";
			sb.append(next);
			key2 = key2.substring(key2.indexOf(" ") + 1) + next;
		}
		System.out.println(sb.toString());
	}

	private static class MarkovChain {
		private static Map<String, MarkovNode> chain = new HashMap<>();

		private void add(String key, String value) {
			if (!chain.containsKey(key))
				chain.put(key, new MarkovNode(value));
			else
				chain.get(key).addPos(value);
		}

		private String getNext(String key) {
			MarkovNode node = chain.get(key);
			return node != null ? node.get() : null;
		}

		private void print() {
			for (Map.Entry<String, MarkovNode> entry : chain.entrySet()) {
				System.out.println(entry.getKey());
				entry.getValue().print();
			}
		}
	}

	private static class MarkovNode {
		private int nodes;
		private Map<String, AtomicInteger> graph;

		private MarkovNode(String possibility) {
			nodes = 1;
			graph = new HashMap<>();
			graph.put(possibility, new AtomicInteger(1));
		}
		
		private void addPos(String poss) {
			nodes++;
			graph.putIfAbsent(poss, new AtomicInteger());
			graph.get(poss).incrementAndGet();
		}

		private String get() {
			double rand = random();
			double accu = 0;
			for (Map.Entry<String, AtomicInteger> entry : graph.entrySet()) {
				accu += ((double) entry.getValue().get())/nodes;
				
				if (rand < accu)
					return entry.getKey();
			}
			
			throw new IllegalStateException();
		}

		private void print() {
			for (Map.Entry<String, AtomicInteger> entry : graph.entrySet()) {
				System.out.println("------" + (((double) entry.getValue().get())/nodes) + "-----> " + entry.getKey());
			}
		}
	}
}
