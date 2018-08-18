import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class StackSolution {

	public static void main(String[] args) {
		System.out.println(eval("( + 7 ( * 8 12 ) ( * 2 ( + 9 4 ) 7 ) 3 )"));
	}

	public static int eval(String expr) {
		String[] expression = expr.split(" ");
		Stack<String> evaluationStack = new Stack<>();
		for(String expressionElement : expression) {
			Arrays.stream(evaluationStack.toArray()).forEach(item -> System.out.print(item + " "));
			System.out.println();
			if (!")".equals(expressionElement))
				evaluationStack.push(expressionElement);
			else {
				List<Integer> operands = new ArrayList<>();
				while (!"+".equals(evaluationStack.peek()) && !"*".equals(evaluationStack.peek()))
					operands.add(Integer.valueOf(evaluationStack.pop()));
				String operator = evaluationStack.pop();
				Integer result = operands.stream().reduce((x, y) -> "+".equals(operator) ? (x + y) : (x * y)).get();
				evaluationStack.pop();
				evaluationStack.push(result.toString());
			}
		}
		
		return Integer.valueOf(evaluationStack.pop());
	}

}		


