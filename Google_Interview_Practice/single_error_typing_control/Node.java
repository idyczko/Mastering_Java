import java.util.Set;

public interface Node {
	
	public Character getChar();
	public Set<Node> getPredecessors();
	public Set<Node> getSuccessors();
}
