import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
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
		LinkedList<BFSNode> path = treePreservingBFS(g, start, end);
		return resolvePathFromTree(path);
	}
	
	public static List<Integer> bidirectionalSearchPath(Graph g, int start, int end) {
		Optional<Graph.Node> startNode = g.nodes().stream().filter(item -> item.id == start).findFirst();
		Optional<Graph.Node> endNode = g.nodes().stream().filter(item -> item.id == end).findFirst();
		LinkedList<BFSNode> pathHead = new LinkedList<>();
		LinkedList<BFSNode> pathTail = new LinkedList<>();
					       
		if (!startNode.isPresent() || !endNode.isPresent())
			throw new IllegalArgumentException("Either start or end node could not be found in the graph.");

		Set<Graph.Node> visitedFromStart = new HashSet<>();	
		Set<Graph.Node> visitedFromEnd = new HashSet<>();
		LinkedList<BFSNode> queueStart = new LinkedList<>(Arrays.asList(new BFSNode(startNode.get(), null)));
		LinkedList<BFSNode> queueEnd = new LinkedList<>(Arrays.asList(new BFSNode(endNode.get(), null)));
		while(!queueStart.isEmpty() && !queueEnd.isEmpty()) {
			BFSNode head = queueStart.poll();
			BFSNode tail = queueEnd.poll();
					visitedFromStart.add(head.node);
			visitedFromEnd.add(tail.node);
			pathHead.add(head);
			pathTail.add(tail);
			if (visitedFromStart.contains(tail.node) || visitedFromEnd.contains(head.node))
				return mergePaths(pathHead, pathTail);



		}

		throw new IllegalArgumentException("There is no path between given nodes...");
	}

	private static List<Integer> resolvePathFromTree(LinkedList<BFSNode> tree) {
		Collections.reverse(tree);
		Queue<BFSNode> queueTree = tree;
		List<Integer> path = new ArrayList<>();
		BFSNode last = queueTree.poll();
		path.add(last.node.id);
		while(!queueTree.isEmpty()) {
			while(!queueTree.peek().node.equals(last.ancestor)) {
				queueTree.poll();
			}
			last = queueTree.poll();
			path.add(last.node.id);
		}
		Collections.reverse(path);
		return path;
	}

	private static boolean reachable(Graph g, int start, int end, boolean dfs) {
		List<Integer> path = fs(g, start, dfs, true, end);
		return path.get(path.size() - 1).equals(end);
	}

	private static LinkedList<BFSNode> treePreservingBFS(Graph g, int start, int end) {
		Optional<Graph.Node> rootNode = g.nodes.stream().filter(item -> item.id == start).findFirst();
		LinkedList<BFSNode> bfsOrder = new LinkedList<>();

		if (! rootNode.isPresent())
			throw new IllegalArgumentException("Specified root node does not exist.");

		Set<Graph.Node> visitedNodes = new HashSet<>();
		LinkedList<BFSNode> queue = new LinkedList<>();
		queue.add(new BFSNode(rootNode.get(), null));
		while (!queue.isEmpty()) {
			BFSNode polledNode = queue.poll();
			bfsOrder.add(polledNode);
			visitedNodes.add(polledNode.node);
			List<Graph.Node> toBeQueued = polledNode.node.adjacentNodes.stream().filter(node -> !visitedNodes.contains(node) && !queue.stream().anyMatch(inner -> inner.node.equals(node))).collect(Collectors.toList());
			if (toBeQueued.stream().anyMatch(node -> node.id == end)){
				bfsOrder.add(new BFSNode(toBeQueued.stream().filter(node->node.id==end).findFirst().get(), polledNode.node));
				return bfsOrder;
			}
			toBeQueued.stream().forEach(node -> queue.add(new BFSNode(node, polledNode.node)));
		}

		throw new IllegalArgumentException("The end is not reachable from the start.");		
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
			if (search && toBeQueued.stream().anyMatch(node -> node.id == end)){
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

	private static class BFSNode {
		private Graph.Node node;
		private Graph.Node ancestor;

		private BFSNode (Graph.Node node, Graph.Node ancestor) {
			this.node = node;
			this.ancestor = ancestor;
		}
	}
} 
