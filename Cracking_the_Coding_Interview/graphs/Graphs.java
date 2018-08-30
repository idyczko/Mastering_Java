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
		return fs(g, root, true);
	}

	public static List<Integer> bfs(Graph g, int root) {
		return fs(g, root, false);
	}


	public static List<Integer> fs(Graph g, int root, boolean dfs) {
		Optional<Graph.Node> rootNode = g.nodes.stream().filter(item -> item.id == root).findFirst();
		List<Integer> dfsOrder = new ArrayList<>(g.nodes.size());

		if (! rootNode.isPresent())
			throw new IllegalArgumentException("Specified root node does not exist.");

		Set<Graph.Node> visitedNodes = new HashSet<>();
		LinkedList<Graph.Node> queue = new LinkedList<>();
		queue.add(rootNode.get());
		while (!queue.isEmpty()) {
			Graph.Node polledNode = queue.poll();
			dfsOrder.add(polledNode.id);
			visitedNodes.add(polledNode);
			List<Graph.Node> toBeQueued = polledNode.adjacentNodes.stream().filter(node -> !visitedNodes.contains(node) && !queue.contains(node)).collect(Collectors.toList());
			if (dfs)
				Collections.sort(toBeQueued, Collections.reverseOrder());
			toBeQueued.stream().forEach(node -> {
				if (dfs)
					queue.addFirst(node);
				else
					queue.add(node);
			});
		}

		return dfsOrder;		

	}
} 
