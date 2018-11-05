import java.util.Set;

public interface Graph {

	public Set<Integer> getNeighbours(int node);

	public int getEdgeWeight(int u, int v);

	public int getNumberOfNodes();
}
