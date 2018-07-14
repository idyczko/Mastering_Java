import static java.lang.Math.*;

public class Main {

  public static void main(String[] args){
    //linkedListTest();
    //arrayListTest();
    //removeDups();
    //removeMiddleNode();
    //partitionList();
    //addNumbers();
    //findIntersection();
    findLoopStart();
  }

  private static void findLoopStart() {
    BareBoneLinkedList<Integer> a = new BareBoneLinkedList<>();
    a.add(52);
    a.add(123);
    a.add(12);
    a.add(432);
    a.add(232);
    a.add(325);
    a.add(233);
    a.add(11);
    a.add(42);
    a.add(32);
    a.addNode(a.getNode(4));
    BareBoneLinkedList.Node<Integer> node = BareBoneLinkedLists.findLoopStart(a);
    System.out.println(node == null? null : node.item);
  }



  private static void findIntersection() {
    BareBoneLinkedList<Integer> a = new BareBoneLinkedList<>();
    a.add(5);
    a.add(3);
    a.add(1);
    a.add(4);
    a.add(2);
    BareBoneLinkedList<Integer> b = new BareBoneLinkedList<>();
    b.add(7);
    b.add(6);
    b.add(8);
    BareBoneLinkedList.Node<Integer> node1 = new BareBoneLinkedList.Node<>(10);
    BareBoneLinkedList.Node<Integer> node2 = new BareBoneLinkedList.Node<>(100);
    BareBoneLinkedList.Node<Integer> node3 = new BareBoneLinkedList.Node<>(1000);
    BareBoneLinkedList.Node<Integer> node4 = new BareBoneLinkedList.Node<>(10000);
    a.addNode(node1);
    b.addNode(node1);
    a.addNode(node2);
    a.addNode(node3);
    a.addNode(node4);
    b.size += 3;
    BareBoneLinkedList.Node<Integer> ahead = a.head.next;
    BareBoneLinkedList.Node<Integer> bhead = b.head.next;
    while(ahead != null){
      System.out.println(ahead.item);
      ahead = ahead.next;
    }
    while(bhead != null){
      System.out.println(bhead.item);
      bhead = bhead.next;
    }
    BareBoneLinkedList.Node<Integer> intersectionNode = BareBoneLinkedLists.findIntersectionNode(a,b);
    System.out.println(intersectionNode == null ? "Lists are not intersecting." : intersectionNode.item);
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
    Integer rfh = BareBoneLinkedLists.addFromHead(a, b);
    Integer rft = BareBoneLinkedLists.addFromTail(a, b);
    System.out.println(rfh + " " + rft);
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
