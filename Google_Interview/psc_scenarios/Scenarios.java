import java.util.*;
import java.util.stream.*;

public class Scenarios {


	private static List<String> levels = new ArrayList<>();
	private static Map<String, List<String>> options = new HashMap<>();
	private static int solutions = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			String[] split = line.split(" ");
			int numPos = Integer.valueOf(split[0]);
			String nameOfCategory = split[1].trim();
			options.put(nameOfCategory, new ArrayList<>());
			levels.add(nameOfCategory);
			for (int i = 0; i < numPos; i++) {
				if (!sc.hasNextLine())
					throw new IllegalArgumentException("Fewer options than promised!");

				line = sc.nextLine();
				options.get(nameOfCategory).add(line.trim());
			}
		}

		System.out.println(levels.stream().collect(Collectors.joining(";")));
		printAll(0, new ArrayList<>());
		System.out.println("Number of solutions: " + solutions);
	}

	private static void printAsCsv(List<String> solution) {
		System.out.println(solution.stream().collect(Collectors.joining(";")));
	}

	private static void printAll(int level, List<String> partialSolution) {
		if (level >= levels.size()) {
			//String solution = partialSolution.stream().collect(Collectors.joining(" -> "));
			//System.out.println(solution);
			printAsCsv(partialSolution);
			solutions++;
			return;
		}

		for (String option : options.get(levels.get(level))) {
			List<String> temp = new ArrayList<>(partialSolution);
			temp.add(option);
			printAll(level + 1, temp);
		}
	}

}
