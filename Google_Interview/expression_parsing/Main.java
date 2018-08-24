import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

	
	public static void main(String[] args) {
		System.out.println(eval("3"));
		System.out.println(eval("( + 7 ( * 8 12 ) ( * 2 ( + 9 4 ) 7 ) 3 )"));
	}

	public static int eval(String expr) {
		return eval(expr.toCharArray(), 0, expr.length());
	}

	public static int eval(char[] expression, int begin, int end) {
		if (expression[begin] != '(')
			return Integer.valueOf(new String(expression).substring(begin, end));
		
		if (expression[begin + 2] == '+') {
			return sum(extractOperands(expression, begin + 4, end - 1));
		}  else {
			return product(extractOperands(expression, begin + 4, end - 1));
		}
	}

	public static int[] extractOperands(char[] expression, int begin, int end) {
		List<Integer> operands = new ArrayList<>();
		System.out.println(new String(expression).substring(begin, end));
		while(begin < end) {
			int endIndex = begin;
			if (expression[begin] != '(') {
				while(expression[++endIndex] != ' ');
				endIndex--;
			} else {
				int brackets = 1;
				while (brackets > 0) {
					endIndex++;
					if (expression[endIndex] == '(')
						brackets++;
					else  if (expression[endIndex] == ')')
						brackets--;
				}
			}
			operands.add(eval(expression, begin, endIndex + 1));
			begin = endIndex + 2;
		}
		return operands.stream().mapToInt(Integer::valueOf).toArray();
	}

	public static int sum(int... operands) {
		return Arrays.stream(operands).sum();
	}

	public static int product(int... operands) {
		int accu = 1;
		for (int operand : operands) {
			accu *= operand;
		}
		return accu;
	}

}
