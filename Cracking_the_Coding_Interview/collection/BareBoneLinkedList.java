public class BareBoneLinkedList<T> {

  public Node<T> head = new Node<T>();
  private int size = 0;

  public int size() {
    return size;
  }

  public T get(int index) {
    return getNode(index).item;
  }

  public void add(T item) {
    Node<T> lastNode = head;
    while(lastNode.next != null) {
      lastNode = lastNode.next;
    }
    lastNode.next = new Node<T>(item);
    size++;
  }

  public Node<T> getNode(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }

    int localIndex = 0;
    Node<T> node = head.next;
    while(index > localIndex) {
      node = node.next;
      localIndex++;
    }
    return node;
  }

  public void removeDupsNoAdditionalSpace() {
    Node<T> ref = head.next;
    Node<T> comp = ref;

    while(ref.next != null) {
      if (comp.next == null) {
        ref = ref.next;
        comp = ref;
        continue;
      }
      if (comp.next.item.equals(ref.item)) {
        comp.next = comp.next.next;
        size--;
      } else {
        comp = comp.next;
      }

    }

  }

  public static class Node<T> {
    public T item;
    public Node<T> next;

    public Node() {}

    public Node(T item) {
      this.item = item;
    }
  }

}
