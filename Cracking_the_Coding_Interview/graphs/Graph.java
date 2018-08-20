import java.util.Set;
import java.util.HashSet;

public class Graph {

	public Integer nextId = 0;
	public Integer nodesCount = 0;
	public Set<Node> nodes = new HashSet<Node>();
	public Set<Edge> edges = new HashSet<Edge>();
	
	public void printAdjacencyMatrix() {
	
	}

	public void removeNode(Node node) {
		removeEdges(edges.toArray());
		nodes.remove(node);
		nodesCount--;
	}

	public void removeEdges(Edge... edges) {
		for (Edge edge : edges) {
			edge.firstNode.incidentEdges.remove(edge);
			edge.secondNode.incidentEdges.remove(edge);
			edges.remove(edge);
		}
	}
	

	public void addEdge(int nodeOne, int nodeTwo) {
		Optional<Node> first = nodes.stream().filter(item -> item.id.equals(nodeOne)).findFirst();
		Optional<Node> second = nodes.stream().filter(item -> item.id.equals(nodeOne)).findFirst();
	
		if (first.empty() || second.empty())
			throw new IllegalArgumentException("One of the listed nodes does not exist.");

		edges.add(new Edge(first.get(), second.get()));	
	}

	public Integer addNode() {
		addNode(nextId);
		nodesCount++;
		return nextId++;
	}

	private void addNode(int id) {
		addNode(new Node(id));
	}

	private void addNode(Node node) {
		nodes.add(node);
	}

	public static class Edge {
		public Node firstNode;
		public Node secondNode;
	
		public Edge(Node firstNode, Node secondNode) {
			this.firstNode = firstNode.id < secondNode.id ? firstNode : secondNode;
			this.secondNode = firstNode.id < secondNode.id ? secondNode : firstNode;
			this.firstNode.adjacentNodes.add(secondNode);
			this.secondNode.adjacentNodes.add(firstNode);
			this.firstNode.incidentEdges.add(this);
			this.secondNode.incidentEdges.add(this);
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;

			if (!(o instanceof Edge))
				return false;

			Edge e = (Edge) o;
			return this.firstNode.id.equals(e.firstNode.id) && this.secondNode.id.equals(e.secondNode.id);
		}

		@Override
		public int hashCode() {
			return 2^firstNode.id * 3^secondNode.id;
		}

		@Override
		public String toString() {
			return "(" + firstNode.id + ", " + secondNode.id + ")";
		}
	}

	public static class Node {
		public Integer id;
		public Set<Node> adjacentNodes;
		public Set<Edges> incidentEdges;
		
		public Node(Integer id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			
			if (!(o instanceof Node))
				return false;

			Node n = (Node) o;
			return this.id.equals(n.id);
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
