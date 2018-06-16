import java.util.*;

public interface Node<T> {

  public T getValue();

  public Collection<Node<T>> getDescendants();
}
