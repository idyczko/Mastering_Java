package collection;

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

  private class Node<T> {
    public T item;
    public Node<T> next;

    public Node() {}

    public Node(T item) {
      this.item = item;
    }
  }
}
