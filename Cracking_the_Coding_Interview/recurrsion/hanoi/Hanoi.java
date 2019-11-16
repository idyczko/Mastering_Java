import java.util.*;


public class Hanoi {

	private static LinkedList hanoi[] = new LinkedList[3];

	public static void main(String[] args) {
		LinkedList<Integer> stack1 = new LinkedList<Integer>();
		stack1.addFirst(5);
		stack1.addFirst(4);
		stack1.addFirst(3);
		stack1.addFirst(2);
		stack1.addFirst(1);
		LinkedList<Integer> stack2 = new LinkedList<Integer>();
		LinkedList<Integer> stack3 = new LinkedList<Integer>();
		hanoi[0] = stack1;
		hanoi[1] = stack2;
		hanoi[2] = stack3;
		print();
		transfer(hanoi);

	}

	private static void transfer(LinkedList<Integer> hanoi[]) {
		transfer(hanoi[0].size(), hanoi[0], hanoi[1], hanoi[2]);
	}

	private static void transfer(int elements, LinkedList<Integer> from, LinkedList<Integer> buffer, LinkedList<Integer> to) {
		if (elements == 1) {
			to.addFirst(from.removeFirst());
			print();
			return;
		}
		
		transfer(elements - 1, from, to, buffer);
		to.addFirst(from.removeFirst());
		print();
		transfer(elements - 1, buffer, from, to);
	}

	private static void print() {
		int elements = hanoi[0].size() + hanoi[1].size() + hanoi[2].size();
		System.out.println("=========================================================");
		for (int i = elements - 1; i >= 0; i--) {
			for (LinkedList list : hanoi)
				System.out.print(list.size() - 1 >= i ? list.get(list.size() - i - 1) +"\t" : " \t");
			System.out.println();
		}
	}
}
