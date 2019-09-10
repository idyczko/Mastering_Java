import java.util.*;

public class PoisonedBottles {


	public static void main(String[] args) {
		
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

		public void submitTestPlan(List<Integer> stripesPerBottle[], int experimentDay) {
			if (experimentDay < stripesByDay.size())
				throw new IllegalArgumentException("You cannot schedule an experiment in the past.");
			
			while (experimentDay > stripesByDay.size())
				stripesByDay.add(stripesByDay.isEmpty() ? createInitialStripes() : stripesByDay.get(stripesByDay.size() - 1));

			

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
