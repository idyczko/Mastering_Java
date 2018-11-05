

public final class Dijkstra {

	private Dijkstra() {
		throw new AssertionError("This class is not meant to be instantiated.");
	}

	public static int calculateDistance(Graph g, int source, int sink) {
		int[] distancesToSource = new int[g.getNumberOfNodes()];
		VertexQueue queue = new MyVertexQueue(source, g.getNumberOfNodes());

		while (!queue.isEmpty()) {
			VertexDistance closest = queue.popClosestToSource();
			distancesToSource[closest.vertex] = closest.distance; 
			for (int neighbour : g.getNeighbours(closest.vertex)) {
				if (neighbour != source && distancesToSource[neighbour] == 0)
					queue.reduceDistance(neighbour, closest.distance + g.getEdgeWeight(neighbour, closest.vertex));
			}
		}

		return distancesToSource[sink];
	}
}
