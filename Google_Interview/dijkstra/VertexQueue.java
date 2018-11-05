
public interface VertexQueue {

	public VertexDistance popClosestToSource();
	public void reduceDistance(int v, int distance);
	public boolean isEmpty();
}
