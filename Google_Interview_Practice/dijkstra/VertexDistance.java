
public class VertexDistance implements Comparable<VertexDistance>{
	public int predecessor;
	public int vertex;
	public int distance;

	public VertexDistance(int vertex, int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}

	public VertexDistance(int vertex, int distance, int predecessor) {
		this(vertex, distance);
		this.predecessor = predecessor;
	}

	@Override
	public int compareTo(VertexDistance vd) {
		return this.distance - vd.distance;
	}
}

