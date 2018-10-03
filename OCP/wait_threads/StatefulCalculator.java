
public class StatefulCalculator {
	int intermediateResult = 0;

	public synchronized int calculate(int input) throws InterruptedException{
		firstStep();
		int cache = intermediateResult;
		cleanUp();
		return cache*input;
	}

	private void firstStep() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			intermediateResult += i;
			Thread.sleep(500);
		}
	}

	private void cleanUp() {
		intermediateResult = 0;
	}
}
