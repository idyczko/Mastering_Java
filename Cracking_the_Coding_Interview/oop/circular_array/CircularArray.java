import java.util.*;

public class CircularArray<T> implements Iterable<T> {

	public static void main(String args[]) {
		List<Integer> items = new ArrayList<>();
		for (String arg : args) {
			items.add(Integer.valueOf(arg));
		}
		CircularArray<Integer> ca = new CircularArray<>(items);

		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while (!line.equals("q")) {
			switch (line) {
				case "d":
					for (Integer i : ca)
						System.out.println(i);
					break;
				case "r":
					int indices = sc.nextInt();
					ca.rotate(indices);
					break;
				default:
					break;
			}
			line = sc.nextLine();
		}
	}

	private Object[] items;

	private int index;

	public CircularArray(Collection<T> items) {
		this.index = 0;
		this.items = new Object[items.size()];
	 	int i = 0;
		for (T item : items) {
			this.items[i++] = item;
		}
	}

	public void rotate(int indices) {
		this.index = ((this.index + indices) % this.items.length + this.items.length) % this.items.length;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			int startIndex = index;
			int runningIndex = index;
			boolean started = false;

			@Override
			public T next() {
				this.started = true;
				runningIndex %= items.length;
				return (T) items[runningIndex++];
			}

			@Override
			public boolean hasNext() {
				return items.length > 0 && (!started || runningIndex % items.length != startIndex);
			}
		};
	}
}
