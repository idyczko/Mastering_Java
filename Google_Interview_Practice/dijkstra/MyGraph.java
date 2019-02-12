import java.util.Set;
import java.util.HashSet;


public class MyGraph implements Graph {
	
	private int[][] graph;
	private Set<Edge> edges;

	public MyGraph(int[][] graph) {
		if (graph == null || graph.length != graph[0].length)
			throw new IllegalArgumentException("You cannot instantiate a graph like that.");
		
		this.graph = graph;
		this.edges = new HashSet<>();
		for (int i = 0; i < this.graph.length; i++)
			for (int j = i + 1; j < this.graph[0].length; j++)
				if (this.graph[i][j] > 0)
					this.edges.add(new Edge(i, j, this.graph[i][j]));

		this.edges.stream().forEach(edge -> System.out.println("Edge: " +  edge.u + " <---" + edge.weight + "---> " + edge.v));
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

	@Override
	public Set<Edge> getEdges() {
		return this.edges;	
	}
}
