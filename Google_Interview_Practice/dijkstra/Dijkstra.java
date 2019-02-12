import java.util.List;
import java.util.LinkedList;

public final class Dijkstra {

	private Dijkstra() {
		throw new AssertionError("This class is not meant to be instantiated.");
	}

	public static int calculateDistance(Graph g, int source, int sink) {
		return calculateDistances(g, source)[sink].distance;
	}

	public static List<Integer> findPath(Graph g, int source, int sink) {
		LinkedList<Integer> path = new LinkedList<>();
		VertexDistance[] distances = calculateDistances(g, source);
		path.addFirst(sink);
		while (sink != source) {
			sink = distances[sink].predecessor;
			path.addFirst(sink);
		}
		return path;
	}

	public static VertexDistance[] calculateDistances(Graph g, int source) {
		VertexDistance[] distancesToSource = new VertexDistance[g.getNumberOfNodes()];
		VertexQueue queue = new MyVertexQueue(source, g.getNumberOfNodes());

		while (!queue.isEmpty()) {
			VertexDistance closest = queue.popClosestToSource();
			distancesToSource[closest.vertex] = closest; 
			for (int neighbour : g.getNeighbours(closest.vertex))
				queue.reduceDistance(neighbour, closest.distance + g.getEdgeWeight(neighbour, closest.vertex), closest.vertex);
		}

		return distancesToSource;
	}
}
