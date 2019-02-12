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
	public void reduceDistance(int vertex, int distance, int predecessor) {
		Optional<VertexDistance> maybeDistance = this.queue.stream().filter(vd -> vd.vertex == vertex).findAny();
		if (!maybeDistance.isPresent())
			return;

		if (maybeDistance.get().distance > distance) {
			this.queue.remove(maybeDistance.get());
			this.queue.add(new VertexDistance(vertex, distance, predecessor));
		}
	}

	@Override
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
}
