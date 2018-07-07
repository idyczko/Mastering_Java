import static java.lang.Math.*;

public class Main {

  public static void main(String[] args){
    //linkedListTest();
    //arrayListTest();
    //removeDups();
    //removeMiddleNode();
    //partitionList();
    addNumbers();
  }

  private static void addNumbers() {
    BareBoneLinkedList<Integer> a = new BareBoneLinkedList<>();
    a.add(5);
    a.add(3);
    a.add(1);
    a.add(4);
    a.add(2);
    BareBoneLinkedList<Integer> b = new BareBoneLinkedList<>();
    b.add(9);
    b.add(7);
    b.add(6);
    b.add(8);
    Integer rfh = addFromHead(a, b);
    Integer rft = addFromTail(a, b);
    System.out.println(rfh + " " + rft);
  }

  private static Integer addFromTail(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {
    BareBoneLinkedList<Integer> longer = a.size() >= b.size() ? a : b;
    BareBoneLinkedList<Integer> shorter = a.size() >= b.size() ? b : a;
    BareBoneLinkedList.Node<Integer> longerPtr = longer.head.next;
    BareBoneLinkedList.Node<Integer> shorterPtr = shorter.head.next;

    int diff = longer.size() - shorter.size();
    int multiplier = (int) pow(10, longer.size() - 1);

    Integer sum = 0;
    while (longerPtr != null && shorterPtr != null) {
      if (diff <= 0) {
        sum += multiplier * shorterPtr.item;
        shorterPtr = shorterPtr.next;
      }

      sum += multiplier * longerPtr.item;
      longerPtr = longerPtr.next;

      diff--;
      multiplier /= 10;
    }
    return sum;
  }

  private static Integer addFromHead(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {
    BareBoneLinkedList.Node<Integer> headA = a.head.next;
    BareBoneLinkedList.Node<Integer> headB = b.head.next;

    int multiplier = 1;
    Integer sum = 0;
    while (headA != null || headB != null) {
      if (headA != null) {
        sum += multiplier * headA.item;
        headA = headA.next;
      }
      if (headB != null) {
        sum += multiplier * headB.item;
        headB = headB.next;
      }

      multiplier *= 10;
    }
    return sum;
  }

  private static void partitionList() {
    BareBoneLinkedList<Integer> list = new BareBoneLinkedList<>();
    list.add(5);
    list.add(3);
    list.add(1);
    list.add(4);
    list.add(2);
    list.partition(3);
    for(int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }

  private static void removeMiddleNode() {
    BareBoneLinkedList<Integer> list = new BareBoneLinkedList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.deleteMiddleNode(list.getNode(3));
    for(int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }

  private static void removeDups() {
    BareBoneLinkedList<Integer> list = new BareBoneLinkedList<>();
    list.add(5);
    list.add(1);
    list.add(2);
    list.add(1);
    list.add(3);
    list.add(2);
    list.add(1);
    list.add(1);
    list.add(3);
    list.add(1);
    list.add(4);
    list.removeDupsNoAdditionalSpace();
    for(int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }

  private static void linkedListTest() {
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
  }

  private static void arrayListTest() {
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
