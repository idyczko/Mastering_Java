import java.util.*;
import java.util.stream.*;


public class TopologicalSort {

	public static List<Integer> findBuildOrder(int[][] matrix) {
		DAG dag = new DAG(matrix);
		List<Integer> buildOrder = new ArrayList<>(matrix.length);
		Set<Integer> startingVertices;
		while (!(startingVertices = dag.findNodesWithoutIncomingEdges()).isEmpty() && buildOrder.size() < matrix.length) {
			startingVertices.stream().filter(v -> !buildOrder.contains(v)).forEach(v -> {
				buildOrder.add(v);
				dag.removeOutgoingEdges(v);
			});
		}

		if (buildOrder.size() < matrix.length)
			throw new IllegalArgumentException();

		return buildOrder;
	}

	public static class DAG {
		
		private int[][] dag;

		public DAG(int[][] dag) {
			this.dag = dag;
		}

		private void removeOutgoingEdges(int vertex) {
		
			if (vertex >= dag.length)
				throw new IllegalArgumentException();

			for (int i = 0; i < dag.length; i++)
				dag[vertex][i] = 0;
		}

		private Set<Integer> findNodesWithoutIncomingEdges() {
			Set<Integer> nodes = IntStream.range(0, dag.length).boxed().collect(Collectors.toSet());
			for (int i = 0; i < dag.length; i++)
				for (int j = 0; j < dag[0].length; j++)
					if (dag[i][j] != 0) { 
						nodes.remove(j);
						continue;
					}
			return nodes;
		}
	}

}
