import java.util.*;


public class Hanoi {

	
	public static void main(String[] args) {
		Stack<Integer> stack1 = new Stack<Integer>();
		stack1.push(5);
		stack1.push(4);
		stack1.push(3);
		stack1.push(2);
		stack1.push(1);
		Stack<Integer> stack2 = new Stack<Integer>();
		Stack<Integer> stack3 = new Stack<Integer>();
		Stack hanoi[] = {stack1, stack2, stack3};
		transfer(hanoi);

		while(!stack3.isEmpty())
			System.out.println(stack3.pop());
	}

	private static void transfer(Stack<Integer> hanoi[]) {
		transfer(hanoi[0].size(), hanoi[0], hanoi[1], hanoi[2]);
	}

	private static void transfer(int elements, Stack<Integer> from, Stack<Integer> buffer, Stack<Integer> to) {
		if (elements == 1) {
			to.push(from.pop());
			return;
		}
		
		transfer(elements - 1, from, to, buffer);
		Integer top = from.pop();
		to.push(top);
		transfer(elements - 1, buffer, from, to);
	}


}
