
public class VertexDistance implements Comparable<VertexDistance>{
	public int vertex;
	public int distance;

	public VertexDistance(int vertex, int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}

	@Override
	public int compareTo(VertexDistance vd) {
		return this.distance - vd.distance;
	}
}

