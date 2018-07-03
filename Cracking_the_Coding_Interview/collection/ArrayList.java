
public class ArrayList<T> implements List<T> {
  private static final int GROWTH_FACTOR = 2;

  private Object[] items = new Object[10];
  private int size;

  public T get(int index) {
    if(index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return (T) items[index];
  }

  public void add(T item) {
    if(size == items.length) {
      expandArray();
    }
    items[++size - 1] = item;
  }

  public void put(T item, int index) {
    if(index > size) {
      throw new IndexOutOfBoundsException();
    }
    if(size == items.length) {
      expandArray();
    }
    for(int i = size; i > index; i--) {
      items[i] = items[i-1];
    }
    items[index] = item;
    size++;
  }

  public void remove(int index) {
    if(index >= size) {
      throw new IndexOutOfBoundsException();
    }
    for(int i = index; i < size - 1; i++) {
      items[i] = items[i+1];
    }
    size--;
  }

  public int size() {
    return size;
  }

  private void expandArray() {
    Object[] newItems = new Object[items.length * GROWTH_FACTOR];
    for(int i = 0; i < items.length; i++) {
      newItems[i] = items[i];
    }
    items = newItems;
  }
}
