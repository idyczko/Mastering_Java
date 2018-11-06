import java.util.Set;
import java.util.HashSet;


public class MyGraph implements Graph {
	
	int[][] graph;

	public MyGraph(int[][] graph) {
		if (graph == null || graph.length != graph[0].length)
			throw new IllegalArgumentException("You cannot instantiate a graph like that.");
		
		this.graph = graph;
	}

	@Override
	public Set<Integer> getNeighbours(int node) {
		Set<Integer> neighbours = new HashSet<>();
		for(int i = 0; i < this.graph.length; i++) {
			if (i != node && this.graph[i][node] != 0)
				neighbours.add(i);
		}

		return neighbours;
	}

	@Override
	public int getEdgeWeight(int u, int v) {
		return this.graph[u][v];
	}

	@Override
	public int getNumberOfNodes() {
		return this.graph.length;
	}
}
