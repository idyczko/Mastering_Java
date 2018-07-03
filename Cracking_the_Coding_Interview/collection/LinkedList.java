
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedList<T> implements List<T> {
  private Node<T> head = new Node<T>();
  private int size = 0;

  public T get(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }

    int localIndex = 0;
    Node<T> node = head.next;
    while(index > localIndex) {
      node = node.next;
      localIndex++;
    }
    return node.item;
  }

  public void add(T item) {
    Node<T> lastNode = head;
    while(lastNode.next != null) {
      lastNode = lastNode.next;
    }
    lastNode.next = new Node<T>(item);
    size++;
  }

  public void put(T item, int index) {
    if (index > size) {
      throw new IndexOutOfBoundsException();
    }

    int localIndex = 0;
    Node<T> oneBefore = head.next;
    while(index > localIndex + 1) {
      oneBefore = oneBefore.next;
      localIndex++;
    }
    Node<T> newNode = new Node<>(item);
    newNode.next = oneBefore.next;
    oneBefore.next = newNode;
    size++;
  }

  public void remove(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }

    int localIndex = 0;
    Node<T> oneBefore = head.next;
    while(index > localIndex + 1) {
      oneBefore = oneBefore.next;
      localIndex++;
    }
    oneBefore.next = oneBefore.next.next;
    size--;
  }

  public int size() {
    return size;
  }

  public T iterativeKthToLast(int k) {
    Node<T> ptr1 = head;
    Node<T> ptr2 = head;
    while(--k > 0) {
      if(ptr2 == null)
        return null;
      ptr2 = ptr2.next;
    }
    while(ptr2.next != null) {
      ptr1 = ptr1.next;
      ptr2 = ptr2.next;
    }

    return ptr1.item;
  }

  public T recursiveKthToLast(int k) {
    AtomicInteger atom = new AtomicInteger(k - 1);
    return recursiveKthToLast(atom, head);
  }

  /* Note this is a fairly simple recursion with a branching factor of 1. It is
  * therefore pretty easy to predict atom's behaviour at each point of execution.
  */
  private T recursiveKthToLast(AtomicInteger atom, Node<T> node) {
    if (node.next == null)
      return null;

    T n = recursiveKthToLast(atom, node.next);
    atom.decrementAndGet();

    if (atom.intValue() == 0)
      return node.item;

    return n;
  }

  private class Node<T> {
    public T item;
    public Node<T> next;

    public Node() {}

    public Node(T item) {
      this.item = item;
    }
  }
}
