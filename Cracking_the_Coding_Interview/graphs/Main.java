public class Main {

	public static void main(String[] args) {
   		BinaryTree tree = BinaryTree.BinaryTreeConstructor.createMockedTree();
    		tree.inOrderTraversal().forEach(node -> System.out.println(node.value));
	    	System.out.println();
    		tree.postOrderTraversal().forEach(node -> System.out.println(node.value));
	    	System.out.println();
    		tree.preOrderTraversal().forEach(node -> System.out.println(node.value));
	    	System.out.println();
    		tree.inverse().inOrderTraversal().forEach(node -> System.out.println(node.value));
 
		Graph graph = new Graph();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();
		graph.addNode();

		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 3);
		graph.addEdge(1, 4);
		graph.addEdge(1, 5);
		graph.addEdge(2, 4);
		graph.addEdge(2, 5);
		graph.addEdge(2, 6);
		graph.addEdge(3, 6);
		graph.addEdge(4, 7);
		graph.addEdge(5, 7);
		graph.addEdge(5, 8);
		graph.addEdge(6, 8);
		graph.addEdge(7, 9);
		graph.addEdge(8, 9);
		
		
		System.out.println("Graphs: ");	
		graph.printMathematicalNotation();
		graph.printAdjacencyMatrix();
		graph.printNeighbourList();
		
		System.out.println("DFS:");
		Graphs.dfs(graph, 0).stream().forEach(System.out::println);
		System.out.println("BFS:");
		Graphs.bfs(graph, 0).stream().forEach(System.out::println);
		System.out.println("Reachability:");
		System.out.println("There " + (Graphs.reachable(graph, 0, 9) ? "is":"is no") + " path between nodes 0 and 9.");
		System.out.println("There " + (Graphs.reachable(graph, 0, 10) ? "is":"is no") + " path between nodes 0 and 10.");
	}
}
