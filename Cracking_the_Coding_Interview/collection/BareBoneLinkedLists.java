import static java.lang.Math.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BareBoneLinkedLists {

  private BareBoneLinkedLists(){};

  public static <T extends Comparable> boolean isPalindrome(BareBoneLinkedList<T> list) {
    AtomicBoolean palindromeSoFar = new AtomicBoolean(true);
    isPalindrome(list.head.next, list.size, palindromeSoFar);
    return palindromeSoFar.get();
  }

  private static <T extends Comparable> BareBoneLinkedList.Node<T> isPalindrome(
    BareBoneLinkedList.Node<T> node, int length, AtomicBoolean palindromeSoFar) {
      if (length == 1)
        return node.next;
      else if (length == 0)
        return node;

      BareBoneLinkedList.Node<T> backNode = isPalindrome(node.next, length - 2, palindromeSoFar);
      if (!palindromeSoFar.get())
        return null;

      if (!node.item.equals(backNode.item)) {
        palindromeSoFar.set(false);
        return null;
      }

      return backNode.next;
    }

  public static <T extends Comparable> BareBoneLinkedList.Node<T> findLoopStart(BareBoneLinkedList<T> list) {
    BareBoneLinkedList.Node<T> slow = list.head.next;
    BareBoneLinkedList.Node<T> fast = list.head.next;

    while(slow != null && fast !=null) {
      if (fast.next == null)
        return null;
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast)
        break;
    }
    BareBoneLinkedList.Node<T> slow2 = list.head.next;
    while (slow != slow2) {
      slow = slow.next;
      slow2 = slow2.next;
    }
    return slow;
  }

  public static <T extends Comparable> BareBoneLinkedList.Node<T> findIntersectionNode(BareBoneLinkedList<T> a, BareBoneLinkedList<T> b) {
    BareBoneLinkedList<T> longer = a.size() >= b.size() ? a : b;
    BareBoneLinkedList<T> shorter = a.size() >= b.size() ? b : a;
    int diff = longer.size() - shorter.size();

    BareBoneLinkedList.Node<T> longerPtr = longer.getNode(diff);
    BareBoneLinkedList.Node<T> shorterPtr = shorter.head.next;

    while(longerPtr != null && shorterPtr != null) {
      if(longerPtr == shorterPtr)
        return longerPtr;
      longerPtr = longerPtr.next;
      shorterPtr = shorterPtr.next;
    }

    return null;
  }

  public static Integer addFromTail(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {
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

  public static Integer addFromHead(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {
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

  public static BareBoneLinkedList<Integer> addFromHeadToList(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {
    BareBoneLinkedList<Integer> result = new BareBoneLinkedList<>();

    result.head.next = addFromHeadToList(a.head.next, b.head.next, 0);

    return result;
  }

  private static BareBoneLinkedList.Node<Integer> addFromHeadToList(BareBoneLinkedList.Node<Integer> a,
    BareBoneLinkedList.Node<Integer> b, int carry) {

    if(a == null && b == null && carry == 0)
      return null;

    BareBoneLinkedList.Node<Integer> result = new BareBoneLinkedList.Node<>();
    int value = carry + (a == null ? 0 : a.item) + (b == null ? 0 : b.item);
    result.item = value % 10;

    if (a != null || b != null) {
      result.next = addFromHeadToList(a == null ? null : a.next, b == null ? null : b.next, value/10);
    }

    return result;
  }

  public static BareBoneLinkedList<Integer> addFromTailToList(BareBoneLinkedList<Integer> a, BareBoneLinkedList<Integer> b) {


    BareBoneLinkedList<Integer> result = new BareBoneLinkedList<>();

    int carry = addFromTailToList(result.head, a.head.next, b.head.next);
    if (carry > 0) {
      BareBoneLinkedList.Node<Integer> carryNode = new BareBoneLinkedList.Node<>(carry);
      carryNode.next = result.head.next;
      result.head.next = carryNode;
    }
    return result;
  }

  private static int addFromTailToList(BareBoneLinkedList.Node<Integer> tail,
    BareBoneLinkedList.Node<Integer> a,
    BareBoneLinkedList.Node<Integer> b) {
    if(a == null || b == null)
      return 0;
    tail.next = new BareBoneLinkedList.Node<>();
    int carry = addFromTailToList(tail.next, a.next, b.next);
    tail.next.item = (a.item + b.item + carry) % 10;

    return (a.item + b.item + carry)/10;
  }

}
