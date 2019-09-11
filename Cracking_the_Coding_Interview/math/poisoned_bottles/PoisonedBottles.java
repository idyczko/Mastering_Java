import java.util.*;
import java.util.stream.*;

public class PoisonedBottles {


	public static void main(String[] args) {
		int bottles = Integer.parseInt(args[0]);
		List<Integer> poisoned = Arrays.stream(args[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
		PoisonResearchUnit pru = new PoisonResearchUnit(bottles, poisoned);

		Scanner sc = new Scanner(System.in);
		String line = "";
		while (!(line = sc.nextLine()).equals("EXIT")) {
			String command = line.split(" ")[0];
			switch (command) {
				case "R":
					printStripes(pru.stripesByDay);
					break;

				case "T":
					int experimentDay = Integer.parseInt(line.split(" ")[1]);
					List<Integer> bottlesPerStripe[] = resolveBottlesPerStripe(line);
					pru.submitTestPlan(bottlesPerStripe, experimentDay);

				default:
					break;
			}
			System.out.println();
		}
	}

	public static List<Integer>[] resolveBottlesPerStripe(String line) {
		String[] args = line.split(" ");
		if (args.length != 12)
			throw new IllegalArgumentException("You should define one list of bottles per stripe. \"_\" indicates an empty list!");

		List<Integer> bottles[] = new List[10];
		for (int i = 2; i < args.length; i++) {
			bottles[i - 2] = "_".equals(args[i]) ? new ArrayList<>() : new ArrayList<>(Arrays.stream(args[i].split(",")).map(Integer::parseInt).collect(Collectors.toList()));	
		}

		return bottles;
	}

	public static void printStripes(List<Boolean[]> stripesPerDay) {
		for (Boolean[] stripes : stripesPerDay) {
			System.out.println(Arrays.stream(stripes).map(s -> s ? "1" : "0").collect(Collectors.joining(" ")));
		}
	}

	public static interface ResearchUnit {
		
		public void submitTestPlan(List<Integer> stripesPerBottle[], int experimentDay);

		public Boolean[] retrieveTestResults(int experimentDay);
	}

	public static class PoisonResearchUnit implements ResearchUnit {
		private int bottles;
		private List<Integer> poisonedBottles;

		private List<Boolean[]> stripesByDay = new ArrayList<>();

		public PoisonResearchUnit(int bottles, List<Integer> poisonedBottles) {
			this.bottles = bottles;
			this.poisonedBottles = poisonedBottles;
		}

		public void submitTestPlan(List<Integer> bottlesPerStripe[], int experimentDay) {
			if (experimentDay < stripesByDay.size())
				throw new IllegalArgumentException("You cannot schedule an experiment in the past.");
			
			while (experimentDay > stripesByDay.size())
				stripesByDay.add(stripesByDay.isEmpty() ? createInitialStripes() : stripesByDay.get(stripesByDay.size() - 1));

			
			Boolean[] stripes = stripesByDay.isEmpty() ? createInitialStripes() : Arrays.copyOf(stripesByDay.get(stripesByDay.size() - 1), 10);
			for (int i = 0; i < bottlesPerStripe.length; i++) {
				List<Integer> bottles = bottlesPerStripe[i];
				for (Integer bottle : bottles)
					if (poisonedBottles.contains(bottle))
						stripes[i] = true;
			}
			stripesByDay.add(stripes);
		}

		public Boolean[] retrieveTestResults(int experimentDay) {
			if (experimentDay < 7)
				throw new IllegalArgumentException("First results will be ready on the 7th day");

			if (experimentDay - 7 > stripesByDay.size() - 1)
				throw new IllegalArgumentException("Results not ready yet.");

			return stripesByDay.get(experimentDay - 7);
		}

		private Boolean[] createInitialStripes() {
			return new Boolean[] {false, false, false, false, false, false, false, false, false, false};
		}

	}
}
