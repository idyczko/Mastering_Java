
public class CollectionMain {

  public static void main(String[] args){
    //LinkedList
    List<Integer> list = new LinkedList<>();
    list.add(10);
    list.add(11);
    list.add(12);
    list.add(13);
    list.add(14);
    list.add(15);
    list.put(1000, 4);
    list.remove(5);
    list.put(10400, list.size());
    try {
      list.get(100);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("You got carried away, pal!");
    }

    for(int i = 0; i<list.size(); i++) {
      System.out.println(list.get(i));
    }
    System.out.println("3th to last element found recursively: " + ((LinkedList<Integer>) list).recursiveKthToLast(3));
    System.out.println("3th to last element found iteratively: " + ((LinkedList<Integer>) list).iterativeKthToLast(3));

    //ArrayList
    List<Integer> list2 = new ArrayList<>();
    list2.add(10);
    list2.add(11);
    list2.add(12);
    list2.add(13);
    list2.add(14);
    list2.add(15);
    list2.put(1000, 4);
    list2.remove(5);
    list2.add(10);
    list2.add(11);
    list2.add(12);
    list2.add(13);
    list2.add(14);
    list2.add(15);
    list2.put(1000, 10);
    list2.remove(5);
    list2.put(10400, list2.size());
    try {
      list2.get(100);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("You got carried away, pal!");
    }

    for(int i = 0; i<list2.size(); i++) {
      System.out.println(list2.get(i));
    }

  }
}
