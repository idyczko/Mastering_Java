
public interface List<T> {

  public T get(int index);

  public void add(T item);

  public void put(T item, int index);

  public void remove(int index);

  public int size();

}
