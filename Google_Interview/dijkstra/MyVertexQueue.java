import java.util.PriorityQueue;
import java.util.Optional;

public class MyVertexQueue implements VertexQueue {

	private PriorityQueue<VertexDistance> queue;

	public MyVertexQueue(int source, int vertices) {
		this.queue = new PriorityQueue<>(vertices);
		this.queue.add(new VertexDistance(source, 0));

		for (int i = 0; i < vertices; i++)
			if (i != source)
				this.queue.add(new VertexDistance(i, Integer.MAX_VALUE));
	}

	@Override
	public VertexDistance popClosestToSource() {
		return this.queue.poll();
	}

	@Override
	public void reduceDistance(int vertex, int distance) {
		Optional<VertexDistance> maybeDistance = this.queue.stream().filter(vd -> vd.vertex == vertex).findAny();
	       	
		if (!maybeDistance.isPresent())
			throw new IllegalArgumentException("You tried to update information about a vertex which is not in the queue.");
		
		if (maybeDistance.get().distance > distance)
			maybeDistance.get().distance = distance;	
	}

	@Override
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
}
