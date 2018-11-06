
public final class BellmanFord {

	public static int calculateDistance(Graph g, int source, int sink) {
		int[] distances = new int[g.getNumberOfNodes()];
		int[] predecessors = new int[g.getNumberOfNodes()];
		for (int i = 0; i < g.getNumberOfNodes(); i++) {
			distances[i] = Integer.MAX_VALUE;
			predecessors[i] = -1;
		}
		distances[source] = 0;

		for (int i = 1; i < g.getNumberOfNodes(); i++) {
			g.getEdges().stream().forEach(edge -> {
				if (distances[edge.u] > (((long) distances[edge.v]) + edge.weight)) {
					distances[edge.u] = distances[edge.v] + edge.weight;
					predecessors[edge.u] = edge.v;
				}
				if (distances[edge.v] > (((long) distances[edge.u]) + edge.weight)) {
					distances[edge.v] = distances[edge.u] + edge.weight;
					predecessors[edge.v] = edge.u;
				}
			});
		}
		for (int i = 0; i < distances.length; i++)
			System.out.println("Distance to " + i + ": " + distances[i]);
		return distances[sink];
	}
}
