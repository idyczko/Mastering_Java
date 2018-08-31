import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

public class Graphs {
	private Graphs() {}

	
	public static List<Integer> dfs(Graph g, int root) {
		return fs(g, root, true, false, -1);
	}

	public static List<Integer> bfs(Graph g, int root) {
		return fs(g, root, false, false, -1);
	}

	public static boolean reachable(Graph g, int start, int end) {
		return reachable(g, start, end, false);
	}

	public static List<Integer> shortestPath(Graph g, int start, int end) {
		List<Integer> path = fs(g, start, false, true, end);
		if (!path.get(path.size() - 1).equals(end))
			throw new IllegalArgumentException("End is not reachable from the start.");

		return path;
	}

	private static boolean reachable(Graph g, int start, int end, boolean dfs) {
		List<Integer> path = fs(g, start, dfs, true, end);
		return path.get(path.size() - 1).equals(end);
	}


	private static List<Integer> fs(Graph g, int start, boolean dfs, boolean search, int end) {
		Optional<Graph.Node> rootNode = g.nodes.stream().filter(item -> item.id == start).findFirst();
		List<Integer> fsOrder = new ArrayList<>(g.nodes.size());

		if (! rootNode.isPresent())
			throw new IllegalArgumentException("Specified root node does not exist.");

		Set<Graph.Node> visitedNodes = new HashSet<>();
		LinkedList<Graph.Node> queue = new LinkedList<>();
		queue.add(rootNode.get());
		while (!queue.isEmpty()) {
			Graph.Node polledNode = queue.poll();
			fsOrder.add(polledNode.id);
			visitedNodes.add(polledNode);
			List<Graph.Node> toBeQueued = polledNode.adjacentNodes.stream().filter(node -> !visitedNodes.contains(node) && !queue.contains(node)).collect(Collectors.toList());
			if (search && toBeQueued.stream().filter(node -> node.id == end).findFirst().isPresent()){
				fsOrder.add(end);
				return fsOrder;
			}
			if (dfs)
				Collections.sort(toBeQueued, Collections.reverseOrder());
			toBeQueued.stream().forEach(node -> {
				if (dfs)
					queue.addFirst(node);
				else
					queue.add(node);
			});
		}

		return fsOrder;		

	}
} 
