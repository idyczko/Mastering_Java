

public class Main {
	
	public static void main(String[] args) {
	
		int[][] graph = {{ 0, 10, 10,  0,  0},
				 {10,  0,  7,  2,  0 },
				 {10,  7,  0,  1,  0 },
				 { 0,  2,  1,  0, 16},
				 { 0,  0,  0, 16,  0}};

		System.out.println(Dijkstra.calculateDistance(new MyGraph(graph), Integer.valueOf(args[0]), Integer.valueOf(args[1])));
		Dijkstra.findPath(new MyGraph(graph), Integer.valueOf(args[0]), Integer.valueOf(args[1])).stream().forEach(item -> System.out.print(item + (item == Integer.valueOf(args[1]) ? "" : " -> ")));
	}
}
