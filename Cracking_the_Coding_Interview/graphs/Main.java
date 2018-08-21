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

		graph.addEdge(0, 3);
		graph.addEdge(0, 4);
		graph.addEdge(0, 5);
		graph.addEdge(1, 3);
		graph.addEdge(1, 4);
		graph.addEdge(1, 5);
		graph.addEdge(2, 3);
		graph.addEdge(2, 4);
		graph.addEdge(2, 5);
		
		
		System.out.println("Graphs: ");	
		graph.printMathematicalNotation();
		graph.printAdjacencyMatrix();
		graph.printNeighbourList();
	}
}
