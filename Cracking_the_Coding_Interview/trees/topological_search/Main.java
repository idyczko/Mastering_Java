
public class Main {


	public static void main(String[] args) {
		int[][] dag = { {0, 1, 1, 1, 1},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0}};

		TopologicalSort.findBuildOrder(dag).stream().forEach(System.out::println);
	}
}
