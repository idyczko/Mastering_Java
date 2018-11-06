
public interface VertexQueue {

	public VertexDistance popClosestToSource();
	public void reduceDistance(int v, int distance, int predecessor);
	public boolean isEmpty();
}
